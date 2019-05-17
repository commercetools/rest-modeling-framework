package io.vrap.rmf.raml.persistence.constructor;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.modules.Extension;
import io.vrap.rmf.raml.model.modules.Library;
import io.vrap.rmf.raml.model.modules.LibraryUse;
import io.vrap.rmf.raml.model.resources.ResourceType;
import io.vrap.rmf.raml.model.resources.Trait;
import io.vrap.rmf.raml.model.security.SecurityScheme;
import io.vrap.rmf.raml.model.types.*;
import io.vrap.rmf.raml.model.types.util.TypesSwitch;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static io.vrap.rmf.raml.model.elements.ElementsPackage.Literals.NAMED_ELEMENT__NAME;
import static io.vrap.rmf.raml.model.modules.ModulesPackage.Literals.*;
import static io.vrap.rmf.raml.model.resources.ResourcesPackage.Literals.RESOURCE_TYPE;
import static io.vrap.rmf.raml.model.resources.ResourcesPackage.Literals.TRAIT;
import static io.vrap.rmf.raml.model.security.SecurityPackage.Literals.SECURITY_SCHEME;
import static io.vrap.rmf.raml.model.security.SecurityPackage.Literals.SECURITY_SCHEME_CONTAINER__SECURITY_SCHEMES;
import static io.vrap.rmf.raml.model.types.TypesPackage.Literals.ANY_TYPE__TYPE;

/**
 * Resolves all declarations so that we can reference them during the next parse phases.
 */
public class DeclarationResolver {
    private final TypeExpressionResolver typeExpressionResolver = new TypeExpressionResolver();

    /**
     * The ordered map of unresolved type declarations.
     */
    private final Multimap<RAMLParser.TypeDeclarationFacetContext, EObject> unresolvedTypeDeclarations
            = ArrayListMultimap.create();

    public void resolve(final ParserRuleContext ruleContext, final Scope scope) {
        final DeclarationConstructingVisitor declarationConstructingVisitor = new DeclarationConstructingVisitor(scope);
        final EObject rootObject = (EObject) declarationConstructingVisitor.visit(ruleContext);

        int unresolvedTypes = unresolvedTypeDeclarations.size();
        int newUnresolvedTypes = 0;
        while (newUnresolvedTypes < unresolvedTypes) {
            unresolvedTypes = unresolvedTypeDeclarations.size();

            final Multimap<RAMLParser.TypeDeclarationFacetContext, EObject> typeDeclarationsToResolve =
                    ArrayListMultimap.create(unresolvedTypeDeclarations);
            for (final RAMLParser.TypeDeclarationFacetContext typeDeclarationFacet : typeDeclarationsToResolve.keySet()) {
                for (final EObject unresolved : typeDeclarationsToResolve.get(typeDeclarationFacet)) {
                    final TypeResolvingVisitor typeResolvingVisitor = new TypeResolvingVisitor(unresolved, scope.with(rootObject));

                    final EObject resolvedType = typeResolvingVisitor.visitTypeDeclarationFacet(typeDeclarationFacet);
                    if (resolvedType != null && !resolvedType.eIsProxy()) {
                        unresolvedTypeDeclarations.remove(typeDeclarationFacet, unresolved);
                    }
                }
            }
            newUnresolvedTypes = unresolvedTypeDeclarations.size();
        }
        if (unresolvedTypeDeclarations.size() > 0) {
            unresolvedTypeDeclarations.keySet().forEach(typeDeclarationFacet -> {
                final Token nameToken = typeDeclarationFacet.typeDeclarationTuple() == null ?
                        Optional.ofNullable(typeDeclarationFacet.typeDeclarationMap()).map(t -> t.name.start).orElse(null) :
                        Optional.ofNullable(typeDeclarationFacet.typeDeclarationTuple()).map(t -> t.name.start).orElse(null);

                if (nameToken == null) {
                    unresolvedTypeDeclarations.get(typeDeclarationFacet)
                            .forEach(eObject -> scope.addError("Type ''{0}'' couldn't be resolved", eObject));
                } else {
                    scope.addErrorWithLocation("Type ''{0}'' couldn't be resolved",
                            nameToken, nameToken.getText());
                }
            });
        }
    }

    /**
     * This visitor creates declarations and potentially unresolved types.
     */
    private class DeclarationConstructingVisitor extends AbstractScopedVisitor<Object> {
        private final UnresolvedTypesCollector unresolvedTypesCollector = new UnresolvedTypesCollector();

        public DeclarationConstructingVisitor(final Scope scope) {
            this.scope = scope;
        }

        @Override
        public Object visitLibrary(final RAMLParser.LibraryContext ctx) {
            final Library library = create(LIBRARY, ctx);
            scope.getResource().getContents().add(library);

            withinScope(scope.with(library), libraryScope ->
                    super.visitLibrary(ctx));

            return library;
        }

        @Override
        public Object visitApi(final RAMLParser.ApiContext ctx) {
            final Api api = create(API, ctx);
            scope.getResource().getContents().add(api);

            withinScope(scope.with(api), apiScope ->
                    super.visitApi(ctx));

            return api;
        }

        @Override
        public Object visitMediaTypeFacet(final RAMLParser.MediaTypeFacetContext ctx) {
            final List<String> mediaTypes =
                    ctx.types.stream().map(RAMLParser.IdContext::getText).collect(Collectors.toList());

            scope.setValue(API_BASE__MEDIA_TYPE, mediaTypes, ctx.start);
            return super.visitMediaTypeFacet(ctx);
        }

        @Override
        public Object visitExtension(RAMLParser.ExtensionContext ctx) {
            final Extension extension = create(EXTENSION, ctx);
            scope.getResource().getContents().add(extension);

            withinScope(scope.with(extension), extensionScope ->
                    super.visitExtension(ctx));

            return extension;
        }

        @Override
        public Object visitLibraryUse(final RAMLParser.LibraryUseContext libraryUseFacet) {
            final String libraryUri = libraryUseFacet.libraryUri.getText();
            final Resource libraryResource = scope.getResource(libraryUri);
            final EList<EObject> libraryResourceContents = libraryResource.getContents();

            final LibraryUse libraryUse = create(LIBRARY_USE, libraryUseFacet);
            libraryUse.setName(libraryUseFacet.name.getText());
            if (libraryResourceContents.size() == 1 && libraryResourceContents.get(0) instanceof Library) {
                final Library library = (Library) libraryResourceContents.get(0);
                libraryUse.setLibrary(library);
            }

            scope.with(TYPE_CONTAINER__USES).setValue(libraryUse, libraryUseFacet.name.getStart());

            return libraryUse;
        }

        @Override
        public Object visitResourceTypesFacet(RAMLParser.ResourceTypesFacetContext resourceTypesFacet) {
            return withinScope(scope.with(TYPE_CONTAINER__RESOURCE_TYPES), resourceTypesScope ->
                    super.visitResourceTypesFacet(resourceTypesFacet));
        }

        @Override
        public Object visitResourceTypeDeclarationFacet(RAMLParser.ResourceTypeDeclarationFacetContext resourceTypeDeclarationFacet) {
            final ResourceType resourceType = create(RESOURCE_TYPE, resourceTypeDeclarationFacet);
            scope.setValue(resourceType, resourceTypeDeclarationFacet.getStart());
            resourceType.setName(resourceTypeDeclarationFacet.name.getText());

            return resourceType;
        }

        @Override
        public Object visitTraitsFacet(RAMLParser.TraitsFacetContext ctx) {
            return withinScope(scope.with(TYPE_CONTAINER__TRAITS), traitScope ->
                    super.visitTraitsFacet(ctx));
        }

        @Override
        public Object visitTraitFacet(RAMLParser.TraitFacetContext traitFacet) {
            final Trait trait = create(TRAIT, traitFacet);
            scope.setValue(trait, traitFacet.getStart());
            trait.setName(traitFacet.name.getText());

            return trait;
        }

        @Override
        public Object visitTypesFacet(final RAMLParser.TypesFacetContext typesFacet) {
            return withinScope(scope.with(TYPE_CONTAINER__TYPES), typesScope -> {
                final List<Object> types = typesFacet.types.stream()
                        .map(this::visitTypeDeclarationFacet)
                        .collect(Collectors.toList());

                return types;
            });
        }

        @Override
        public Object visitAnnotationTypesFacet(final RAMLParser.AnnotationTypesFacetContext annotationTypesFacet) {
            return withinScope(scope.with(TYPE_CONTAINER__ANNOTATION_TYPES), typesScope -> {
                final List<Object> types = annotationTypesFacet.annotationTypes.stream()
                        .map(this::visitTypeDeclarationFacet)
                        .collect(Collectors.toList());

                return types;
            });
        }

        @Override
        public Object visitTypeDeclarationFacet(final RAMLParser.TypeDeclarationFacetContext typeDeclarationFacet) {
            final EObject eObject = (EObject) super.visitTypeDeclarationFacet(typeDeclarationFacet);
            final List<EObject> unresolvedTypes = getUnresolvedTypes(eObject);
            if (unresolvedTypes.isEmpty()) {
                unresolvedTypeDeclarations.removeAll(typeDeclarationFacet);
            } else {
                unresolvedTypeDeclarations.putAll(typeDeclarationFacet, unresolvedTypes);
            }
            return eObject;
        }

        @Override
        public EObject visitTypeDeclarationTuple(final RAMLParser.TypeDeclarationTupleContext typeDeclarationTuple) {
            final EObject resolved = getType(typeDeclarationTuple, scope);
            scope.setValue(resolved, typeDeclarationTuple.getStart());

            return resolved;
        }

        @Override
        public EObject visitTypeDeclarationMap(final RAMLParser.TypeDeclarationMapContext typeDeclarationMap) {
            final EObject resolved = getType(typeDeclarationMap, scope);
            scope.setValue(resolved, typeDeclarationMap.getStart());

            return resolved;
        }

        @Override
        public Object visitSecuritySchemesFacet(final RAMLParser.SecuritySchemesFacetContext ctx) {
            return withinScope(scope.with(SECURITY_SCHEME_CONTAINER__SECURITY_SCHEMES), securitySchemesScope ->
                    super.visitSecuritySchemesFacet(ctx));
        }

        @Override
        public Object visitSecuritySchemeFacet(final RAMLParser.SecuritySchemeFacetContext securitySchemeFacet) {
            final SecurityScheme securityScheme = create(SECURITY_SCHEME, securitySchemeFacet);

            final String name = securitySchemeFacet.name.getText();
            securityScheme.setName(name);
            scope.setValue(securityScheme, securitySchemeFacet.getStart());

            return securityScheme;
        }


        private List<EObject> getUnresolvedTypes(final EObject type) {
            return unresolvedTypesCollector.doSwitch(type);
        }
    }

    /**
     * This switch returns the unresolved types of a given type.
     * E.g. an array or union type can reference a yet unresolved type.
     */
    private static class UnresolvedTypesCollector extends TypesSwitch<List<EObject>> {

        @Override
        public List<EObject> doSwitch(final EObject eObject) {
            return eObject != null ?
                    super.doSwitch(eObject) : Collections.emptyList();
        }

        @Override
        public List<EObject> defaultCase(final EObject eObject) {
            return eObject.eIsProxy() ?
                    Collections.singletonList(eObject) : Collections.emptyList();
        }


        @Override
        public List<EObject> caseArrayType(final ArrayType arrayType) {
            return doSwitch(arrayType.getItems());
        }

        @Override
        public List<EObject> caseIntersectionType(final IntersectionType intersectionType) {
            return intersectionType.getAllOf().stream()
                    .map(this::doSwitch)
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
        }

        @Override
        public List<EObject> caseUnionType(final UnionType unionType) {
            return unionType.getOneOf().stream()
                    .map(this::doSwitch)
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
        }
    }

    /**
     * This visitor resolves the yet unresolved types.
     */
    private class TypeResolvingVisitor extends AbstractScopedVisitor<EObject> {
        private final EObject unresolved;

        private TypeResolvingVisitor(final EObject unresolved, final Scope rootScope) {
            this.unresolved = unresolved;
            this.scope = rootScope;
        }

        @Override
        public EObject visitAnnotationTypesFacet(RAMLParser.AnnotationTypesFacetContext ctx) {
            return null;
        }

        @Override
        public EObject visitTypeDeclarationTuple(final RAMLParser.TypeDeclarationTupleContext typeDeclarationTuple) {
            return withinScope(scope.with(TYPE_CONTAINER__TYPES),
                    typesScope -> resolveType(typeDeclarationTuple, getType(typeDeclarationTuple, typesScope)));

        }

        @Override
        public EObject visitTypeDeclarationMap(final RAMLParser.TypeDeclarationMapContext typeDeclarationMap) {
            return withinScope(scope.with(TYPE_CONTAINER__TYPES),
                    typesScope -> resolveType(typeDeclarationMap, getType(typeDeclarationMap, typesScope)));
        }

        private EObject resolveType(final ParserRuleContext ruleContext, final EObject resolvedType) {
            if (resolvedType != null && !resolvedType.eIsProxy()) {
                EcoreUtil.replace(unresolved, resolvedType);

                final Token nameToken = ruleContext.getStart();
                final String name = nameToken.getText();
                final Scope typeScope = scope.with(resolvedType, NAMED_ELEMENT__NAME);

                typeScope.setValue(name, nameToken);
            }
            return resolvedType;
        }
    }

    private EObject getType(final RAMLParser.TypeDeclarationMapContext typeDeclarationMap, final Scope scope) {
        final String typeExpression;
        if (typeDeclarationMap.typeFacet().size() == 1) {
            final RAMLParser.TypeFacetContext typeFacet = typeDeclarationMap.typeFacet().get(0);
            typeExpression = getTypeExpression(typeFacet.typeExpression());
        } else if (typeDeclarationMap.propertiesFacet().size() == 1) {
            typeExpression = BuiltinType.OBJECT.getName();

        } else {
            typeExpression = BuiltinType.STRING.getName();
        }

        final EObject resolved = typeExpressionResolver.resolve(typeExpression, scope);
        if (resolved != null && !resolved.eIsProxy()) {
            setTypeName(resolved, typeDeclarationMap.name.start);
            setType(resolved, typeExpression, typeDeclarationMap.getStart(), scope);
            final RamlParserAdapter adapter = RamlParserAdapter.of(typeDeclarationMap);
            resolved.eAdapters().add(adapter);
        }

        return resolved;
    }

    private String getTypeExpression(final RAMLParser.TypeExpressionContext typeExpressionContext) {
        final List<RAMLParser.IdContext> expressions = typeExpressionContext.id();
        final String typeExpression;
        if (expressions.size() > 1) {
            final StringJoiner stringJoiner = new StringJoiner(",", "[", "]");
            expressions.forEach(e -> stringJoiner.add(e.getText()));
            typeExpression = stringJoiner.toString();
        } else {
            typeExpression = expressions.isEmpty() ? null : expressions.get(0).getText();
        }
        return typeExpression;
    }

    private EObject getType(final RAMLParser.TypeDeclarationTupleContext typeDeclarationTuple, final Scope scope) {
        final Token typeExpressionToken = typeDeclarationTuple.typeExpression().start;
        final String typeExpression = typeExpressionToken.getText().isEmpty() ?
                BuiltinType.STRING.getName() :
                typeExpressionToken.getText();

        final EObject resolved = typeExpressionResolver.resolve(typeExpression, scope);
        if (resolved != null && !resolved.eIsProxy()) {
            setTypeName(resolved, typeDeclarationTuple.name.start);
            setType(resolved, typeExpression, typeExpressionToken, scope);
        }

        return resolved;
    }

    private void setType(final EObject resolved, final String typeExpression, final Token typeExpressionToken,
                         final Scope scope) {
        if (resolved instanceof AnyType) {
            final Scope anyTypeTypeScope = scope.with(resolved, ANY_TYPE__TYPE);
            final EObject resolvedType = typeExpressionResolver.resolve(typeExpression, anyTypeTypeScope);
            if (resolvedType != null) {
                anyTypeTypeScope.setValue(resolvedType, typeExpressionToken);
            }
        }
    }

    private void setTypeName(final EObject resolved, final Token nameToken) {
        final String name = nameToken.getText();
        resolved.eSet(NAMED_ELEMENT__NAME, name);
    }
}

package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.modules.Extension;
import io.vrap.rmf.raml.model.modules.Library;
import io.vrap.rmf.raml.model.modules.LibraryUse;
import io.vrap.rmf.raml.model.resources.ResourceType;
import io.vrap.rmf.raml.model.resources.Trait;
import io.vrap.rmf.raml.model.types.BuiltinType;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.*;
import java.util.stream.Collectors;

import static io.vrap.rmf.raml.model.elements.ElementsPackage.Literals.IDENTIFIABLE_ELEMENT__NAME;
import static io.vrap.rmf.raml.model.modules.ModulesPackage.Literals.*;
import static io.vrap.rmf.raml.model.resources.ResourcesPackage.Literals.RESOURCE_TYPE;
import static io.vrap.rmf.raml.model.resources.ResourcesPackage.Literals.TRAIT;
import static io.vrap.rmf.raml.model.types.TypesPackage.Literals.ANY_TYPE;

/**
 * Resolves all types and annotation types so that they all have a resolved type.
 * This is necessary because the type defines which facets a type declaration can have.
 *
 * Additiopnally it creates all tarits and resource types.
 */
public class TypeDeclarationResolver {
    private final TypeExpressionConstructor typeExpressionConstructor = new TypeExpressionConstructor();

    /**
     * The ordered map of unresolved type declarations.
     */
    private final Map<RAMLParser.TypeDeclarationFacetContext, EObject> unresolvedTypeDeclarations = new LinkedHashMap<>();

    public void resolve(final ParserRuleContext ruleContext, final Scope scope) {
        final TypeConstructingVisitor typeConstructingVisitor = new TypeConstructingVisitor(scope);
        final EObject rootObject = (EObject) typeConstructingVisitor.visit(ruleContext);

        int unresolvedTypes = unresolvedTypeDeclarations.size();
        int newUnresolvedTypes = 0;
        while (newUnresolvedTypes < unresolvedTypes) {
            unresolvedTypes = unresolvedTypeDeclarations.size();

            final Map<RAMLParser.TypeDeclarationFacetContext, EObject> typeDeclarationsToResolve =
                    new HashMap<>(unresolvedTypeDeclarations);
            for (final RAMLParser.TypeDeclarationFacetContext typeDeclarationFacet : typeDeclarationsToResolve.keySet()) {
                EObject unresolved = typeDeclarationsToResolve.get(typeDeclarationFacet);
                final TypeResolvingVisitor typeResolvingVisitor = new TypeResolvingVisitor(unresolved, scope.with(rootObject));

                final EObject resolvedType = typeResolvingVisitor.visitTypeDeclarationFacet(typeDeclarationFacet);
                if (resolvedType != null) {
                    unresolvedTypeDeclarations.remove(typeDeclarationFacet);
                }
            }
            newUnresolvedTypes = unresolvedTypeDeclarations.size();
        }
        if (unresolvedTypeDeclarations.size() > 0) {
            unresolvedTypeDeclarations.keySet().forEach(typeDeclarationFacet -> {
                final Token nameToken = typeDeclarationFacet.typeDeclarationTuple() == null ?
                        Optional.ofNullable(typeDeclarationFacet.typeDeclarationMap()).map(t -> t.name).orElse(null) :
                        Optional.ofNullable(typeDeclarationFacet.typeDeclarationTuple()).map(t -> t.name).orElse(null);

                if (nameToken == null) {
                    final EObject eObject = unresolvedTypeDeclarations.get(typeDeclarationFacet);
                    scope.addError("Type {0} couldn't be resolved", eObject);
                } else {
                    scope.addError("Type {0} couldn't be resolved at {1}",
                            nameToken.getText(), nameToken);
                }
            });
        }
    }

    /**
     * This visitor creates potentially unresolved types.
     */
    private class TypeConstructingVisitor extends AbstractScopedVisitor<Object> {

        public TypeConstructingVisitor(final Scope scope) {
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
        public Object visitExtension(RAMLParser.ExtensionContext ctx) {
            final Extension extension = create(EXTENSION, ctx);
            scope.getResource().getContents().add(extension);

            withinScope(scope.with(extension), extensionScope ->
                    super.visitExtension(ctx));

            return extension;
        }

        @Override
        public Object visitLibraryUse(final RAMLParser.LibraryUseContext libraryUseFacet) {
            final Resource libraryResource = scope.getResource(libraryUseFacet.libraryUri.getText());
            final EList<EObject> contents = libraryResource.getContents();
            final LibraryUse libraryUse = create(LIBRARY_USE, libraryUseFacet);

            libraryUse.setName(libraryUseFacet.name.getText());
            libraryUse.setLibrary((Library) contents.get(0));

            scope.with(TYPE_CONTAINER__USES).setValue(libraryUse, libraryUseFacet.name);

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
            if (eObject == null || eObject.eIsProxy()) {
                unresolvedTypeDeclarations.put(typeDeclarationFacet, eObject);
            } else {
                unresolvedTypeDeclarations.remove(typeDeclarationFacet);
            }
            return eObject;
        }

        @Override
        public Object visitTypeDeclarationMap(final RAMLParser.TypeDeclarationMapContext typeDeclarationMap) {
            final EObject superType = getSuperType(scope, typeDeclarationMap);

            return constructType(typeDeclarationMap, superType);
        }

        @Override
        public Object visitTypeDeclarationTuple(final RAMLParser.TypeDeclarationTupleContext typeDeclarationTuple) {
            final EObject superType = getSuperType(scope, typeDeclarationTuple);

            return constructType(typeDeclarationTuple, superType);
        }

        private EObject constructType(final ParserRuleContext context, final EObject superType) {
            final EObject declaredType;
            final Token nameToken = context.getStart();
            final Optional<BuiltinType> optionalBuiltinType = BuiltinType.of(nameToken.getText());
            if (optionalBuiltinType.isPresent() || superType == null || !superType.eIsProxy()) {
                final EClass eClass = optionalBuiltinType
                        .map(builtinType -> builtinType.getScopedMetaType(scope))
                        .orElseGet(() -> superType == null ? BuiltinType.STRING.getScopedMetaType(scope) : superType.eClass());
                declaredType = create(eClass, context);
                final Scope typeScope = scope.with(declaredType);

                final String name = nameToken.getText();
                typeScope.with(declaredType.eClass().getEStructuralFeature("name"))
                        .setValue(name, nameToken);
                if (!optionalBuiltinType.isPresent() && ANY_TYPE.isSuperTypeOf(eClass)) {
                    typeScope.with(declaredType.eClass().getEStructuralFeature("type"))
                            .setValue(superType, nameToken);
                }
            } else {
                final InternalEObject proxy = (InternalEObject) EcoreUtil.create(superType.eClass());
                final String uriFragment = scope.getUriFragment(nameToken.getText());
                proxy.eSetProxyURI(scope.getResource().getURI().appendFragment(uriFragment));
                declaredType = proxy;
            }
            scope.setValue(declaredType, nameToken);
            return declaredType;
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
            final EObject superType = withinScope(scope.with(TYPE_CONTAINER__TYPES),
                    typesScope -> getSuperType(typesScope, typeDeclarationTuple));

            return resolveType(typeDeclarationTuple, superType);
        }

        @Override
        public EObject visitTypeDeclarationMap(final RAMLParser.TypeDeclarationMapContext typeDeclarationMap) {
            final EObject superType = withinScope(scope.with(TYPE_CONTAINER__TYPES),
                    typesScope -> getSuperType(typesScope, typeDeclarationMap));

            return resolveType(typeDeclarationMap, superType);
        }

        private EObject resolveType(final ParserRuleContext ruleContext, final EObject superType) {
            final EObject resolvedType;

            if (superType.eIsProxy()) {
                resolvedType = null;
            } else {
                resolvedType = create(superType.eClass(), ruleContext);
                EcoreUtil.replace(unresolved, resolvedType);

                final Token nameToken = ruleContext.getStart();
                final String name = nameToken.getText();
                final Scope typeScope = scope.with(resolvedType, TYPE_CONTAINER__TYPES);

                typeScope.with(IDENTIFIABLE_ELEMENT__NAME)
                        .setValue(name, nameToken);

                typeScope.with(unresolved.eClass().getEStructuralFeature("type"))
                        .setValue(superType, nameToken);
            }

            return resolvedType;
        }
    }

    protected EObject getSuperType(final Scope scope, final RAMLParser.TypeDeclarationMapContext typeDeclarationMap) {
        final EObject superType;
        if (typeDeclarationMap.typeFacet().size() == 1) {
            final RAMLParser.TypeFacetContext typeFacet = typeDeclarationMap.typeFacet().get(0);
            superType = (EObject) visitTypeFacet(scope, typeFacet);
        } else if (typeDeclarationMap.propertiesFacet().size() == 1) {
            superType = scope.getEObjectByName(BuiltinType.OBJECT.getName());

        } else {
            superType = scope.getEObjectByName(BuiltinType.STRING.getName());
        }
        return superType;
    }

    protected EObject getSuperType(final Scope scope, final RAMLParser.TypeDeclarationTupleContext typeDeclarationTuple) {
        final EObject superType;
        final String typeExpression = typeDeclarationTuple.typeExpression.getText();
        if (typeExpression.isEmpty()) {
            superType = scope.getEObjectByName(BuiltinType.STRING.getName());
        } else {
            superType = typeExpressionConstructor.parse(typeExpression, scope);
        }
        return superType;
    }

    private Object visitTypeFacet(final Scope scope, final RAMLParser.TypeFacetContext typeFacet) {
        final String typeExpression = typeFacet.typeExpression.getText();

        return typeExpressionConstructor.parse(typeExpression, scope);
    }
}

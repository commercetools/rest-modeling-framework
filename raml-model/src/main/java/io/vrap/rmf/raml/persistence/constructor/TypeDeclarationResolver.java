package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.modules.Library;
import io.vrap.rmf.raml.model.modules.LibraryUse;
import io.vrap.rmf.raml.model.modules.ModulesFactory;
import io.vrap.rmf.raml.model.types.BuiltinType;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.*;
import java.util.stream.Collectors;

import static io.vrap.rmf.raml.model.elements.ElementsPackage.Literals.IDENTIFIABLE_ELEMENT__NAME;
import static io.vrap.rmf.raml.model.modules.ModulesPackage.Literals.TYPE_CONTAINER__TYPES;
import static io.vrap.rmf.raml.model.modules.ModulesPackage.Literals.TYPE_CONTAINER__USES;

/**
 * Resolves all types and annotation types so that they all have a resolved type.
 * This is necessary because the type defines which facets a type declaration can have.
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
                        typeDeclarationFacet.typeDeclarationMap().name :
                        typeDeclarationFacet.typeDeclarationTuple().name;

                scope.addError("Type {0} ({1}) couldn't be resolved",
                        nameToken.getText(), nameToken);
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
            final Library library = ModulesFactory.eINSTANCE.createLibrary();
            scope.getResource().getContents().add(library);

            withinScope(scope.with(library), libraryScope ->
                    super.visitLibrary(ctx));

            return library;
        }

        @Override
        public Object visitApi(final RAMLParser.ApiContext ctx) {
            final Api api = ModulesFactory.eINSTANCE.createApi();
            scope.getResource().getContents().add(api);

            withinScope(scope.with(api), apiScope ->
                    super.visitApi(ctx));

            return api;
        }

        @Override
        public Object visitLibraryUse(final RAMLParser.LibraryUseContext libraryUseFacet) {
            final Resource libraryResource = scope.getResource(libraryUseFacet.libraryUri.getText());
            final EList<EObject> contents = libraryResource.getContents();
            final LibraryUse libraryUse = ModulesFactory.eINSTANCE.createLibraryUse();

            libraryUse.setName(libraryUseFacet.name.getText());
            libraryUse.setLibrary((Library) contents.get(0));

            scope.with(TYPE_CONTAINER__USES).setValue(libraryUse, libraryUseFacet.name);

            return libraryUse;
        }

        @Override
        public Object visitTypesFacet(final RAMLParser.TypesFacetContext typesFacet) {
            final String typesReferenceName = typesFacet.facet.getText();
            final EClass eClass = scope.eObject().eClass();
            final EStructuralFeature typesFeature = eClass.getEStructuralFeature(typesReferenceName);

            return withinScope(scope.with(typesFeature), typesScope -> {
                final List<Object> types = typesFacet.types.stream()
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

            return constructType(typeDeclarationMap.name, superType);
        }

        @Override
        public Object visitTypeDeclarationTuple(final RAMLParser.TypeDeclarationTupleContext typeDeclarationTuple) {
            final EObject superType = getSuperType(scope, typeDeclarationTuple);

            return constructType(typeDeclarationTuple.name, superType);
        }

        private EObject constructType(final Token nameToken, final EObject superType) {
            final EObject declaredType;
            final Optional<BuiltinType> optionalBuiltinType = BuiltinType.of(nameToken.getText());
            if (optionalBuiltinType.isPresent() || superType == null || !superType.eIsProxy()) {
                final EClass eClass = optionalBuiltinType
                        .map(builtinType -> builtinType.getScopedMetaType(scope))
                        .orElseGet(() -> superType == null ? BuiltinType.STRING.getTypeDeclarationType() : superType.eClass());
                declaredType = EcoreUtil.create(eClass);
                final Scope typeScope = scope.with(declaredType);

                final String name = nameToken.getText();
                typeScope.with(declaredType.eClass().getEStructuralFeature("name"))
                        .setValue(name, nameToken);
                typeScope.with(declaredType.eClass().getEStructuralFeature("type"))
                        .setValue(superType, nameToken);
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
        public EObject visitTypeDeclarationTuple(final RAMLParser.TypeDeclarationTupleContext typeDeclarationTuple) {
            final Token nameToken = typeDeclarationTuple.name;
            final EObject superType = withinScope(scope.with(TYPE_CONTAINER__TYPES),
                    typesScope -> getSuperType(typesScope, typeDeclarationTuple));

            return resolveType(nameToken, superType);
        }

        @Override
        public EObject visitTypeDeclarationMap(final RAMLParser.TypeDeclarationMapContext typeDeclarationMap) {
            final Token nameToken = typeDeclarationMap.name;
            final EObject superType = withinScope(scope.with(TYPE_CONTAINER__TYPES),
                    typesScope -> getSuperType(typesScope, typeDeclarationMap));

            return resolveType(nameToken, superType);
        }

        private EObject resolveType(final Token nameToken, final EObject superType) {
            final EObject resolvedType;

            if (superType.eIsProxy()) {
                resolvedType = null;
            } else {
                resolvedType = EcoreUtil.create(superType.eClass());
                EcoreUtil.replace(unresolved, resolvedType);

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
            superType = scope.getResource()
                    .getResourceSet()
                    .getEObject(BuiltinType.RESOURCE_URI.appendFragment("/types/object"), true);

        } else {
            superType = scope.getResource()
                    .getResourceSet()
                    .getEObject(BuiltinType.RESOURCE_URI.appendFragment("/types/string"), true);
        }
        return superType;
    }

    protected EObject getSuperType(final Scope scope, final RAMLParser.TypeDeclarationTupleContext typeDeclarationTuple) {
        final EObject superType;
        final String typeExpression = typeDeclarationTuple.typeExpression.getText();
        if (typeExpression.isEmpty()) {
            superType = scope.getResource()
                    .getResourceSet()
                    .getEObject(BuiltinType.RESOURCE_URI.appendFragment("/types/string"), true);
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

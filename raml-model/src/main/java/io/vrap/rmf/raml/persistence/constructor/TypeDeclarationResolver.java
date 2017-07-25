package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.modules.Library;
import io.vrap.rmf.raml.model.modules.LibraryUse;
import io.vrap.rmf.raml.model.modules.ModulesFactory;
import io.vrap.rmf.raml.model.types.BuiltinType;
import io.vrap.rmf.raml.persistence.antlr.RAMLBaseVisitor;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import io.vrap.rmf.raml.persistence.typeexpressions.TypeExpressionsParser;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.vrap.rmf.raml.model.modules.ModulesPackage.Literals.TYPE_CONTAINER__TYPES;

public class TypeDeclarationResolver extends RAMLBaseVisitor<Object> {
    private final TypeExpressionsParser typeExpressionsParser = new TypeExpressionsParser();
    private Scope scope;
    private final Set<RAMLParser.TypeDeclarationFacetContext> unresolvedTypeDeclarations = new HashSet<>();
    private final List<EObject> resolvedTypes = new ArrayList<>();


    public int resolve(final RAMLParser.ApiContext apiContext, final Scope scope) {
        return withinScope(scope, s -> {
            final Api api = (Api) visitApi(apiContext);
            int count = 0;
            while (unresolvedTypeDeclarations.size() > 0) {
                count++;
                final List<RAMLParser.TypeDeclarationFacetContext> typeDeclarationsToResolve =
                        new ArrayList<>(unresolvedTypeDeclarations);
                for (final RAMLParser.TypeDeclarationFacetContext typeDeclarationFacet : typeDeclarationsToResolve) {
                    withinScope(scope.with(api, TYPE_CONTAINER__TYPES),
                            apiScope -> visitTypeDeclarationFacet(typeDeclarationFacet));
                }
            }

            return count;
        });
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

        scope.setValue(libraryUse, (CommonToken) libraryUseFacet.name);

        return libraryUse;
    }

    @Override
    public Object visitTypesFacet(final RAMLParser.TypesFacetContext typesFacet) {
        final String typesReferenceName = typesFacet.facet.getText();
        if ("types".equals(typesReferenceName)) {
            final EClass eClass = scope.eObject().eClass();
            final EStructuralFeature typesFeature = eClass.getEStructuralFeature(typesReferenceName);

            return withinScope(scope.with(typesFeature), typesScope -> {
                final List<Object> types = typesFacet.types.stream()
                        .map(this::visitTypeDeclarationFacet)
                        .collect(Collectors.toList());

                return types;
            });
        }
        return null;
    }

    @Override
    public Object visitTypeDeclarationFacet(final RAMLParser.TypeDeclarationFacetContext typeDeclarationFacet) {
        final Object eObject = super.visitTypeDeclarationFacet(typeDeclarationFacet);
        if (eObject == null) {
            unresolvedTypeDeclarations.add(typeDeclarationFacet);
        } else {
            unresolvedTypeDeclarations.remove(typeDeclarationFacet);
        }
        return eObject;
    }

    @Override
    public Object visitTypeDeclarationMap(final RAMLParser.TypeDeclarationMapContext typeDeclarationMap) {
        final EObject superType;

        if (typeDeclarationMap.typeFacet().size() == 1) {
            final RAMLParser.TypeFacetContext typeFacet = typeDeclarationMap.typeFacet().get(0);
            superType = (EObject) visitTypeFacet(typeFacet);
        } else if (typeDeclarationMap.propertiesFacet().size() == 1) {
            superType = scope.getResource()
                    .getResourceSet()
                    .getEObject(BuiltinType.RESOURCE_URI.appendFragment("/types/object"), true);

        } else {
            superType = scope.getResource()
                    .getResourceSet()
                    .getEObject(BuiltinType.RESOURCE_URI.appendFragment("/types/string"), true);
        }

        return processType(typeDeclarationMap.name, superType);
    }


    @Override
    public Object visitTypeDeclarationTuple(final RAMLParser.TypeDeclarationTupleContext typeDeclarationTuple) {
        final EObject superType;
        final String typeExpression = typeDeclarationTuple.typeExpression.getText();
        if (typeExpression.isEmpty()) {
            superType = scope.getResource()
                    .getResourceSet()
                    .getEObject(BuiltinType.RESOURCE_URI.appendFragment("/types/string"), true);
        } else {
            superType = typeExpressionsParser.parse(typeExpression, scope);
        }

        return processType(typeDeclarationTuple.name, superType);
    }

    @Override
    public Object visitTypeFacet(final RAMLParser.TypeFacetContext typeFacet) {
        final String typeExpression = typeFacet.typeExpression.getText();

        return typeExpressionsParser.parse(typeExpression, scope);
    }


    protected <T> T withinScope(final Scope scope, final Function<Scope, T> within) {
        pushScope(scope);

        T value = within.apply(scope);

        popScope();

        return value;
    }

    protected Scope pushScope(final Scope scope) {
        return this.scope = scope;
    }

    protected Scope popScope() {
        return this.scope = scope.getParent();
    }


    private EObject processType(final Token nameToken, final EObject superType) {
        if (superType.eIsProxy()) {
            return null;
        } else {
            final EObject declaredType = EcoreUtil.create(superType.eClass());
            final Scope typeScope = scope.with(declaredType);

            final String name = nameToken.getText();
            typeScope.with(declaredType.eClass().getEStructuralFeature("name"))
                    .setValue(name, nameToken);
            typeScope.with(declaredType.eClass().getEStructuralFeature("type"))
                    .setValue(superType, nameToken);

            resolvedTypes.add(declaredType);
            scope.setValue(declaredType, nameToken);

            return declaredType;
        }
    }
}

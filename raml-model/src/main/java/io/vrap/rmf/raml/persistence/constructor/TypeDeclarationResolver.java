package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.modules.Library;
import io.vrap.rmf.raml.model.modules.LibraryUse;
import io.vrap.rmf.raml.model.modules.ModulesFactory;
import io.vrap.rmf.raml.model.types.BuiltinType;
import io.vrap.rmf.raml.persistence.antlr.RAMLBaseVisitor;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import io.vrap.rmf.raml.persistence.typeexpressions.TypeExpressionsParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.vrap.rmf.raml.model.modules.ModulesPackage.Literals.TYPE_CONTAINER__TYPES;
import static io.vrap.rmf.raml.model.modules.ModulesPackage.Literals.TYPE_CONTAINER__USES;

/**
 * Resolves all types and annotation types so that they all have a resolved type.
 * This is necessary because the type defines which facets a type declration can have.
 */
public class TypeDeclarationResolver extends RAMLBaseVisitor<Object> {
    private final TypeExpressionsParser typeExpressionsParser = new TypeExpressionsParser();
    private Scope scope;
    private final Map<RAMLParser.TypeDeclarationFacetContext, EObject> unresolvedTypeDeclarations = new HashMap<>();

    public int resolve(final ParserRuleContext ruleContext, final Scope scope) {
        return withinScope(scope, s -> {
            EObject rootObject = (EObject) visit(ruleContext);

            int count = 0;
            while (unresolvedTypeDeclarations.size() > 0) {
                count++;
                final Map<RAMLParser.TypeDeclarationFacetContext, EObject> typeDeclarationsToResolve =
                        new HashMap<>(unresolvedTypeDeclarations);
                for (final RAMLParser.TypeDeclarationFacetContext typeDeclarationFacet : typeDeclarationsToResolve.keySet()) {
                    EObject unresolved = typeDeclarationsToResolve.get(typeDeclarationFacet);
                    if (typeDeclarationFacet.typeDeclarationTuple() != null) {
                        Token nameToken = typeDeclarationFacet.typeDeclarationTuple().name;
                        EObject superType = typeExpressionsParser.parse(typeDeclarationFacet.typeDeclarationTuple().typeExpression.getText(), scope);
                        if (!superType.eIsProxy()) {
                            EObject eObject = EcoreUtil.create(superType.eClass());
                            EcoreUtil.replace(unresolved, eObject);
                            final String name = nameToken.getText();
                            Scope typeScope = scope.with(eObject, TYPE_CONTAINER__TYPES);
                            typeScope.with(unresolved.eClass().getEStructuralFeature("name"))
                                    .setValue(name, nameToken);
                            typeScope.with(unresolved.eClass().getEStructuralFeature("type"))
                                    .setValue(superType, nameToken);
                            unresolvedTypeDeclarations.remove(typeDeclarationFacet);
                        }
                    } else {
                        EObject superType = (EObject) withinScope(scope.with(rootObject, TYPE_CONTAINER__TYPES), scope1 -> visitTypeFacet(typeDeclarationFacet.typeDeclarationMap().typeFacet(0)));
                        if (!superType.eIsProxy()) {
                            EObject eObject = EcoreUtil.create(superType.eClass());
                            EcoreUtil.replace(unresolved, eObject);
                            Scope typeScope = scope.with(eObject, TYPE_CONTAINER__TYPES);
                            Token nameToken = typeDeclarationFacet.typeDeclarationMap().name;

                            final String name = nameToken.getText();
                            typeScope.with(unresolved.eClass().getEStructuralFeature("name"))
                                    .setValue(name, nameToken);
                            typeScope.with(unresolved.eClass().getEStructuralFeature("type"))
                                    .setValue(superType, nameToken);
                            unresolvedTypeDeclarations.remove(typeDeclarationFacet);
                        }
                    }
                }
            }

            return count;
        });
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
        final EObject declaredType;
        final Optional<BuiltinType> optionalBuiltinType = BuiltinType.of(nameToken.getText());
        if (optionalBuiltinType.isPresent() || !superType.eIsProxy()) {
            final EClass eClass = optionalBuiltinType
                    .map(builtinType -> builtinType.getScopedMetaType(scope))
                    .orElse(superType.eClass());
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

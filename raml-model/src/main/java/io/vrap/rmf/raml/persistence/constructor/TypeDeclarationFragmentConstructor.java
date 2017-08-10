package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.types.BuiltinType;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

public class TypeDeclarationFragmentConstructor extends BaseConstructor {
    private final EReference typeContainer;

    public TypeDeclarationFragmentConstructor(final EReference typeContainer) {
        this.typeContainer = typeContainer;
    }

    @Override
    public EObject construct(final RAMLParser parser, final Scope scope) {
        final TypeDeclarationResolver typeDeclarationResolver =
                new TypeDeclarationResolver();
        typeDeclarationResolver.resolve(parser.typeDeclarationFragment(), scope.with(typeContainer));
        parser.reset();

        return (EObject) withinScope(scope.with(typeContainer),
                typeScope -> visitTypeDeclarationFragment(parser.typeDeclarationFragment()));
    }

    @Override
    public Object visitTypeDeclarationFragment(final RAMLParser.TypeDeclarationFragmentContext typeDeclarationFragment) {
        final EObject superType;

        if (typeDeclarationFragment.typeFacet().size() > 0) {
            final RAMLParser.TypeFacetContext typeFacet = typeDeclarationFragment.typeFacet().get(0);
            superType = (EObject) visitTypeFacet(typeFacet);
        } else if (typeDeclarationFragment.propertiesFacet().size() > 0) {
            superType = scope.getEObjectByName(BuiltinType.OBJECT.getName());
        } else {
            superType = scope.getEObjectByName(BuiltinType.STRING.getName());
        }

        final EObject declaredType = EcoreUtil.create(superType.eClass());
        scope.getResource().getContents().add(declaredType);

        withinScope(scope.with(declaredType), typeScope -> {
            final EStructuralFeature typeReference = superType.eClass().getEStructuralFeature("type");
            typeScope.setValue(typeReference, superType, typeDeclarationFragment.getStart());

            typeDeclarationFragment.annotationFacet().forEach(this::visitAnnotationFacet);
            typeDeclarationFragment.attributeFacet().forEach(this::visitAttributeFacet);
            typeDeclarationFragment.propertiesFacet().forEach(this::visitPropertiesFacet);
            typeDeclarationFragment.defaultFacet().forEach(this::visitDefaultFacet);
            typeDeclarationFragment.exampleFacet().forEach(this::visitExampleFacet);
            typeDeclarationFragment.examplesFacet().forEach(this::visitExamplesFacet);
            typeDeclarationFragment.enumFacet().forEach(this::visitEnumFacet);
            typeDeclarationFragment.itemsFacet().forEach(this::visitItemsFacet);

            return declaredType;
        });

        return declaredType;
    }
}

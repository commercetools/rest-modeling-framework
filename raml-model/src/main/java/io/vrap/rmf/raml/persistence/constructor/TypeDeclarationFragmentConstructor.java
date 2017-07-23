package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.types.BuiltinType;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

public class TypeDeclarationFragmentConstructor extends AbstractConstructor {
    private final EReference typeContainer;

    public TypeDeclarationFragmentConstructor(final EReference typeContainer) {
        this.typeContainer = typeContainer;
    }

    @Override
    public EObject construct(final RAMLParser parser, final Scope scope) {

        return (EObject) withinScope(scope.with(typeContainer),
                typeScope -> visitTypeDeclarationFragment(parser.typeDeclarationFragment()));
    }

    @Override
    public Object visitTypeDeclarationFragment(final RAMLParser.TypeDeclarationFragmentContext typeDeclarationFragment) {
        final EObject superType;

        if (typeDeclarationFragment.typeFacet().size() > 0) {
            final RAMLParser.TypeFacetContext typeFacet = typeDeclarationFragment.typeFacet().get(0);
            superType = (EObject) visitTypeFacet(typeFacet);
        } else {
            superType = scope.getImportedTypeById(BuiltinType.OBJECT.getName());
        }

        final EObject declaredType = EcoreUtil.create(superType.eClass());
        scope.getResource().getContents().add(declaredType);

        withinScope(scope.with(declaredType), typeScope -> {
            final EStructuralFeature typeReference = superType.eClass().getEStructuralFeature("type");
            typeScope.setValue(typeReference, superType);


            typeDeclarationFragment.annotationFacet().forEach(this::visitAnnotationFacet);
            typeDeclarationFragment.attributeFacet().forEach(this::visitAttributeFacet);
            typeDeclarationFragment.propertiesFacet().forEach(this::visitPropertiesFacet);

            return declaredType;
        });

        return declaredType;
    }
}

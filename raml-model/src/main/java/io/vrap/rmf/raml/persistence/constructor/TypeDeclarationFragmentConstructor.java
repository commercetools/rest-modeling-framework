package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.types.BuiltinType;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import static io.vrap.rmf.raml.model.types.TypesPackage.Literals.ANY_TYPE;

public class TypeDeclarationFragmentConstructor extends BaseConstructor {
    private final EReference typeContainer;

    public TypeDeclarationFragmentConstructor(final EReference typeContainer) {
        this.typeContainer = typeContainer;
    }

    @Override
    public EObject construct(final RAMLParser parser, final Scope scope) {
        final DeclarationResolver declarationResolver =
                new DeclarationResolver();
        final RAMLParser.TypeDeclarationFragmentContext typeDeclarationFragmentContext =
                parser.typeDeclarationFragment();
        declarationResolver.resolve(typeDeclarationFragmentContext, scope.with(typeContainer));

        return (EObject) withinScope(scope.with(typeContainer),
                typeScope -> visitTypeDeclarationFragment(typeDeclarationFragmentContext));
    }

    @Override
    public Object visitTypeDeclarationFragment(final RAMLParser.TypeDeclarationFragmentContext typeDeclarationFragment) {
        final EObject superType;

        if (typeDeclarationFragment.typeFacet().size() > 0) {
            final RAMLParser.TypeFacetContext typeFacet = typeDeclarationFragment.typeFacet().get(0);
            superType = (EObject) visitTypeFacet(typeFacet);
        } else if (typeDeclarationFragment.propertiesFacet().size() > 0) {
            superType = BuiltinType.OBJECT.getType(scope.getResourceSet());
        } else {
            superType = BuiltinType.STRING.getType(scope.getResourceSet());
        }

        final EClass eClass = superType.eClass();
        final EObject declaredType = create(eClass, typeDeclarationFragment);
        scope.getResource().getContents().add(declaredType);

        withinScope(scope.with(declaredType), typeScope -> {
            if (ANY_TYPE.isSuperTypeOf(eClass)) {
                final EStructuralFeature typeReference = eClass.getEStructuralFeature("type");
                typeScope.setValue(typeReference, superType, typeDeclarationFragment.getStart());
            }

            typeDeclarationFragment.annotationFacet().forEach(this::visitAnnotationFacet);
            typeDeclarationFragment.attributeFacet().forEach(this::visitAttributeFacet);
            typeDeclarationFragment.descriptionFacet().forEach(this::visitDescriptionFacet);
            typeDeclarationFragment.displayNameFacet().forEach(this::visitDisplayNameFacet);
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

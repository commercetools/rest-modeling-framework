package io.vrap.rmf.raml.persistence.antlr;

import io.vrap.rmf.raml.model.annotations.AnyAnnotationType;
import io.vrap.rmf.raml.model.types.AnyType;
import io.vrap.rmf.raml.model.types.BuiltinType;
import io.vrap.rmf.raml.persistence.constructor.Scope;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

import static io.vrap.rmf.raml.model.elements.ElementsPackage.Literals.IDENTIFIABLE_ELEMENT__NAME;

/**
 * Constructs a type {@link AnyType} or an annotation type {@link AnyAnnotationType}
 * from a type declaration {@link RAMLParser.TypeDeclarationContext}.
 */
public class TypeDeclarationConstructor extends AbstractConstructor {
    protected TypeDeclarationConstructor(final Scope scope) {
        super(scope);
    }

    @Override
    public Object visitTypeDeclaration(final RAMLParser.TypeDeclarationContext ctx) {
        final BuiltinType baseType;
        final EObject superType;

        if (ctx.typeFacet().size() > 0) {
            final RAMLParser.TypeFacetContext typeFacet = ctx.typeFacet().get(0);
            superType = (EObject) TypeExpressionConstructor.of(scope).visitTypeFacet(typeFacet);
            final String typeName = (String) superType.eGet(IDENTIFIABLE_ELEMENT__NAME); // TODO handle arrays
            baseType = BuiltinType.of(typeName)
                    .orElse(BuiltinType.OBJECT);
        } else {
            baseType = BuiltinType.OBJECT;
            superType = scope.getImportedTypeById(baseType.getName());
        }

        final EClass scopedMetaType = baseType.getScopedMetaType(scope);
        final EObject declaredType = EcoreUtil.create(scopedMetaType);
        final EStructuralFeature typeReference = scopedMetaType.getEStructuralFeature("type");
        declaredType.eSet(typeReference, superType);

        final String name = ctx.name.getText();
        declaredType.eSet(IDENTIFIABLE_ELEMENT__NAME, name);

        for (RAMLParser.AttributeFacetContext attributeFacet : ctx.attributeFacet()) {
            setAttribute(attributeFacet, declaredType);
        }

        return declaredType;
    }

    public static TypeDeclarationConstructor of(final Scope scope) {
        return new TypeDeclarationConstructor(scope);
    }
}

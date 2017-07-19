package io.vrap.rmf.raml.persistence.antlr;

import io.vrap.rmf.raml.model.types.BuiltinType;
import io.vrap.rmf.raml.persistence.constructor.Scope;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import static io.vrap.rmf.raml.model.elements.ElementsPackage.Literals.IDENTIFIABLE_ELEMENT__NAME;
import static io.vrap.rmf.raml.model.types.TypesPackage.Literals.ANY_TYPE__TYPE;

/**
 * Constructs a type from a type declaration {@link RAMLParser.Type_declarationContext}.
 */
public class TypeDeclarationConstructor extends AbstractConstructor {
    protected TypeDeclarationConstructor(final Scope scope) {
        super(scope);
    }

    @Override
    public Object visitType_declaration(final RAMLParser.Type_declarationContext ctx) {
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

        final EObject declaredType = EcoreUtil.create(baseType.getTypeDeclarationType());
        declaredType.eSet(ANY_TYPE__TYPE, superType);

        final String name = ctx.name.getText();
        declaredType.eSet(IDENTIFIABLE_ELEMENT__NAME, name);

        for (RAMLParser.Type_declaration_facetContext typeDeclarationFacet : ctx.type_declaration_facet()) {
            constructAttribute(declaredType, typeDeclarationFacet.facet, typeDeclarationFacet.value);
        }

        for (RAMLParser.EnumFacetContext enumFacet : ctx.enumFacet()) {
            constructAttribute(declaredType, enumFacet.facet, enumFacet.literals);
        }

        return declaredType;
    }

    public static TypeDeclarationConstructor of(final Scope scope) {
        return new TypeDeclarationConstructor(scope);
    }
}

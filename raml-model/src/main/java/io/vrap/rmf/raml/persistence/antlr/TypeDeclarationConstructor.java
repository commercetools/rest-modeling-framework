package io.vrap.rmf.raml.persistence.antlr;

import io.vrap.rmf.raml.model.types.BuiltinType;
import io.vrap.rmf.raml.model.types.TypesFactory;
import io.vrap.rmf.raml.persistence.constructor.Scope;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.List;

import static io.vrap.rmf.raml.model.elements.ElementsPackage.Literals.IDENTIFIABLE_ELEMENT__NAME;

/**
 * Constructs types from type declarations {@link RAMLParser.Type_declarationContext}.
 */
public class TypeDeclarationConstructor extends AbstractConstructor {
    private static final TypesFactory TYPES_FACTORY = TypesFactory.eINSTANCE;

    protected TypeDeclarationConstructor(final Scope scope) {
        super(scope);
    }

    @Override
    public Object visitType_declaration(final RAMLParser.Type_declarationContext ctx) {
        final List<RAMLParser.TypeFacetContext> typeFacets = ctx.typeFacet();
        final BuiltinType baseType;
        if (typeFacets.size() > 0) {
            final RAMLParser.TypeFacetContext typeFacet = typeFacets.get(0);
            final EObject typeExpressionType = (EObject) TypeExpressionConstructor.of(scope).visitTypeFacet(typeFacet);
            final String typeName = (String) typeExpressionType.eGet(IDENTIFIABLE_ELEMENT__NAME); // TODO handle arrays
            baseType = BuiltinType.of(typeName)
                    .orElse(BuiltinType.OBJECT);
        } else {
            baseType = BuiltinType.OBJECT;
        }
        final EObject declaredType = EcoreUtil.create(baseType.getTypeDeclarationType());

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

    public static TypeExpressionConstructor of(final Scope scope) {
        return new TypeExpressionConstructor(scope);
    }
}

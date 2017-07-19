package io.vrap.rmf.raml.persistence.antlr;

import io.vrap.rmf.raml.persistence.constructor.Scope;
import io.vrap.rmf.raml.persistence.typeexpressions.TypeExpressionsParser;

/**
 * Constructs a type expression from a {@link RAMLParser.TypeFacetContext}.
 */
public class TypeExpressionConstructor extends AbstractConstructor {
    private final TypeExpressionsParser typeExpressionsParser = new TypeExpressionsParser();

    protected TypeExpressionConstructor(final Scope scope) {
        super(scope);
    }

    @Override
    public Object visitTypeFacet(final RAMLParser.TypeFacetContext ctx) {
        final String typeExpression = ctx.SCALAR().getText();

        return typeExpressionsParser.parse(typeExpression, scope);
    }

    public static TypeExpressionConstructor of(final Scope scope) {
        return new TypeExpressionConstructor(scope);
    }
}

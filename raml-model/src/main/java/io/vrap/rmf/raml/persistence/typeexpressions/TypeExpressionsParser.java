package io.vrap.rmf.raml.persistence.typeexpressions;

import io.vrap.rmf.raml.model.types.AnyType;
import io.vrap.rmf.raml.model.types.ArrayType;
import io.vrap.rmf.raml.model.types.TypesFactory;
import io.vrap.rmf.raml.persistence.antlr.TypeExpressionBaseVisitor;
import io.vrap.rmf.raml.persistence.antlr.TypeExpressionLexer;
import io.vrap.rmf.raml.persistence.antlr.TypeExpressionParser;
import io.vrap.rmf.raml.persistence.constructor.Scope;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

/**
 * This class parses a type expression and transforms it to an {@link AnyType}.
 */
public class TypeExpressionsParser {

    public AnyType parse(final String typeExpression, final Scope scope) {
        final CharStream charStream = CharStreams.fromString(typeExpression);
        final TypeExpressionLexer lexer = new TypeExpressionLexer(charStream);
        final TokenStream tokenStream = new CommonTokenStream(lexer);
        final TypeExpressionParser typeExpressionParser = new TypeExpressionParser(tokenStream);
        final TypeExpressionParser.Type_exprContext typeExpr = typeExpressionParser.type_expr();
        final AnyType anyType = new TypeExpressionBuilder(scope)
                .visit(typeExpr);

        return anyType;
    }

    private final static class TypeExpressionBuilder extends TypeExpressionBaseVisitor<AnyType> {
        private final TypesFactory factory = TypesFactory.eINSTANCE;
        private final Scope scope;

        public TypeExpressionBuilder(final Scope scope) {
            this.scope = scope;
        }

        @Override
        public AnyType visitArrayType(final TypeExpressionParser.ArrayTypeContext ctx) {
            final AnyType itemsType = visit(ctx.type_expr());

            final ArrayType arrayType = factory.createArrayType();
            arrayType.setItems(itemsType);

            return arrayType;
        }

        @Override
        public AnyType visitUnionType(final TypeExpressionParser.UnionTypeContext ctx) {
            return super.visitUnionType(ctx);
        }

        @Override
        public AnyType visitParens(final TypeExpressionParser.ParensContext ctx) {
            return super.visitParens(ctx);
        }

        @Override
        public AnyType visitTypeReference(final TypeExpressionParser.TypeReferenceContext ctx) {
            final String typeName = ctx.getText();
            final AnyType anyType = (AnyType) scope.getImportedTypeById(typeName);

            return anyType;
        }
    }
}

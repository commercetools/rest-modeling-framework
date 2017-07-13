package io.vrap.rmf.raml.persistence.antlr;

import org.antlr.v4.runtime.*;

/**
 * Test fixtures for {@link TypeExpressionParser}.
 */
public interface TypeExpressionFixtures {

    /**
     * Parses the given type expression.
     *
     * @param typeExpression the RAML type expression
     * @return the parse context
     */
    default TypeExpressionParser.Type_exprContext parse(final String typeExpression) {
        final CharStream charStream = CharStreams.fromString(typeExpression);
        final TypeExpressionLexer lexer = new TypeExpressionLexer(charStream);
        final TokenStream tokenStream = new CommonTokenStream(lexer);
        return new TypeExpressionParser(tokenStream).type_expr();
    }
}

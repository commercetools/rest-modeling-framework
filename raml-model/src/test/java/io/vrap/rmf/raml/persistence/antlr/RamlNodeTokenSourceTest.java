package io.vrap.rmf.raml.persistence.antlr;

import org.antlr.v4.runtime.Token;
import org.junit.Test;

import java.net.URL;

/**
 * Unit tests for {@link RamlNodeTokenSource}.
 */
public class RamlNodeTokenSourceTest implements RAMLParserFixtures {

    @Test
    public void circularInclude() {
        final URL url = getClass().getResource("/includes/circular-include.raml");
        final RamlNodeTokenSource lexer = lexer(url);

        for (Token token = lexer.nextToken(); token.getType() != Token.EOF; token = lexer.nextToken()) {

        }
    }
}

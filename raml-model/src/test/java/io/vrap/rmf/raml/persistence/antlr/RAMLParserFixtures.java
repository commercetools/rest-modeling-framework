package io.vrap.rmf.raml.persistence.antlr;

import io.vrap.rmf.raml.persistence.RamlResourceTest;
import org.antlr.v4.runtime.CommonTokenFactory;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Test fixtures for {@link RAMLParser}.
 */
public interface RAMLParserFixtures {
    /**
     * Parses the given resource from the classpath.
     *
     * @param name the resource name
     * @return the parser
     * @throws IOException
     */
    default RAMLParser parseFromClasspath(final String name) throws IOException {
        final InputStream inputStream = RamlResourceTest.class.getResourceAsStream(name);
        final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        final RAMLCustomLexer yamlLexer = new RAMLCustomLexer(inputStreamReader);
        yamlLexer.setTokenFactory(CommonTokenFactory.DEFAULT);
        final TokenStream tokenStream = new CommonTokenStream(yamlLexer);
        return new RAMLParser(tokenStream);
    }
}

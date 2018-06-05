package io.vrap.rmf.raml.persistence.antlr;

import io.vrap.rmf.raml.persistence.RamlResourceSet;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;

import java.io.IOException;
import java.net.URL;

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
    default RAMLParser parserFromClasspath(final String name) throws IOException {
        final URL url = getClass().getResource(name);
        final RamlNodeTokenSource lexer = lexer(url);
        final TokenStream tokenStream = new CommonTokenStream(lexer);

        final RAMLParser ramlParser = new RAMLParser(tokenStream);

        ramlParser.removeErrorListeners();
        ramlParser.addErrorListener(new ParserErrorCollector());

        return ramlParser;
    }

    default RamlNodeTokenSource lexer(final URL url) {
        final URIConverter uriConverter = new RamlResourceSet().getURIConverter();
        final RamlNodeTokenSource lexer = new RamlNodeTokenSource(URI.createURI(url.toString()), uriConverter);
        return lexer;
    }
}

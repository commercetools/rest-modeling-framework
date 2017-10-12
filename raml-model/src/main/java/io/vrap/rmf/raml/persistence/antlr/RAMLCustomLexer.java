package io.vrap.rmf.raml.persistence.antlr;

import org.antlr.v4.runtime.*;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.yaml.snakeyaml.events.Event;

import java.util.Stack;

/**
 * An antlr lexer that uses snakeyaml events {@link Event} to
 * generate antlr tokens.
 */
public class RAMLCustomLexer implements TokenSource {
    private final Stack<TokenSource> currentLexer = new Stack<>();
    private final Stack<URI> uri = new Stack<>();
    private final URIConverter uriConverter;

    public RAMLCustomLexer(final URI uri, final URIConverter uriConverter) {
        this.uri.push(uri);
        this.uriConverter = uriConverter;
        currentLexer.push(createLexer(uri));
    }


    public RAMLCustomLexer(final String input, final URI uri, final URIConverter uriConverter) {
        this.uri.push(uri);
        this.uriConverter = uriConverter;
        currentLexer.push(new YamlLexer(input, uri, uriConverter));
    }

    private TokenSource createLexer(final URI uri) {
        switch (uri.fileExtension()) {
            case "json":
                return new JsonLexer(uri, uriConverter);
            default:
                return new YamlLexer(uri, uriConverter);
        }
    }

    private URI resolve(final String relativePath) {
        final String[] segments = URI.createURI(relativePath).segments();
        final URI baseUri = getBaseUri();
        return baseUri.appendSegments(segments);
    }

    private URI getBaseUri() {
        return uri.peek().trimSegments(1);
    }

    @Override
    public Token nextToken() {
        final RamlToken token = (RamlToken) currentLexer.peek().nextToken();
        if (token.getIncludeUri() != null) {
            final URI resolvedIncludeUri = resolve(token.getIncludeUri());
            if (uri.contains(resolvedIncludeUri)) {
                return token; // TODO throw circular dependency error
            }
            currentLexer.push(createLexer(resolvedIncludeUri));
            uri.push(resolvedIncludeUri);
            return currentLexer.peek().nextToken();
        } else if (token.getType() == IntStream.EOF && currentLexer.size() > 1) {
            currentLexer.pop();
            uri.pop();
            return currentLexer.peek().nextToken();
        } else {
            return token;
        }
    }

    @Override
    public int getLine() {
        return currentLexer.peek().getLine();
    }

    @Override
    public int getCharPositionInLine() {
        return currentLexer.peek().getCharPositionInLine();
    }

    @Override
    public CharStream getInputStream() {
        return null;
    }

    @Override
    public String getSourceName() {
        return currentLexer.peek().getSourceName();
    }

    @Override
    public void setTokenFactory(final TokenFactory<?> factory) {
    }

    @Override
    public TokenFactory<?> getTokenFactory() {
        return null;
    }
}

package io.vrap.rmf.nodes.antlr;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.google.common.io.Closeables;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.Pair;
import org.apache.commons.io.IOUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

/**
 * A node lexer that reads a file into a string token.
 */
class TextNodeLexer implements TokenSource {
    private final URI uri;
    private final InputStream input;
    private NodeTokenFactory factory;
    private final StringWriter writer = new StringWriter();
    private final String encoding = StandardCharsets.UTF_8.name();
    private boolean inputClosed = false;

    private TextNodeLexer(InputStream input, final URI uri) {
        this.uri = uri;
        this.input = input;
        factory = NodeTokenFactory.DEFAULT;
    }

    public TextNodeLexer(final String input, final URI uri) {
        this(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)), uri);
    }

    public TextNodeLexer(final URI uri, final URIConverter uriConverter) {
        this(convertUriToStream(uri, uriConverter), uri);
    }

    private static InputStream convertUriToStream(final URI uri, final URIConverter uriConverter) {
        try {
            return uriConverter.createInputStream(uri);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Token nextToken() {
        if (!inputClosed) {
            try {
                IOUtils.copy(input, writer, encoding);
                inputClosed = true;
                return createToken(writer.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                IOUtils.closeQuietly(input);
            }
        } else {
            return factory.create(IntStream.EOF, null);
        }
    }

    @Override
    public int getLine() {
        return 1;
    }

    @Override
    public int getCharPositionInLine() {
        return 0;
    }

    @Override
    public CharStream getInputStream() {
        return null;
    }

    @Override
    public String getSourceName() {
        return uri.toString();
    }

    @Override
    public void setTokenFactory(final TokenFactory<?> factory) {
        this.factory = (NodeTokenFactory) factory;
    }

    @Override
    public TokenFactory<?> getTokenFactory() {
        return factory;
    }

    private Token createToken(final String text) {
        final Pair<TokenSource, CharStream> source = new Pair<>(this, null);

        final NodeToken nodeToken = factory.create(source, NodeParser.STRING, text, Token.DEFAULT_CHANNEL,
                0, 0,
                getLine(), getCharPositionInLine());
        nodeToken.setLocation(uri.toString());

        return nodeToken;
    }
}

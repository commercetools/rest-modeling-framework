package io.vrap.rmf.nodes.antlr;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.Pair;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * A node lexer that can tokenize json.
 */
class JsonNodeLexer implements TokenSource {
    private final URI uri;
    private NodeTokenFactory factory;
    private final JsonFactory jsonFactory;
    private final JsonParser parser;

    private final int mapStart  = NodeParser.MAP_START;
    private final int mapEnd = NodeParser.MAP_END;
    private final int listStart = NodeParser.LIST_START;
    private final int listEnd = NodeParser.LIST_END;

    private JsonNodeLexer(InputStream input, final URI uri) {
        this.uri = uri;
        factory = NodeTokenFactory.DEFAULT;
        jsonFactory = new JsonFactory();
        try {
            parser = jsonFactory.createParser(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public JsonNodeLexer(final String input, final URI uri) {
        this(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)), uri);
    }

    public JsonNodeLexer(final URI uri, final URIConverter uriConverter) {
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
        try {
            final JsonToken jsonToken = parser.nextToken();
            if (jsonToken != null) {
                switch (jsonToken) {
                    case START_ARRAY:
                        return createToken(listStart, "");
                    case END_ARRAY:
                        return createToken(listEnd, "");
                    case START_OBJECT:
                        return createToken(mapStart, "");
                    case END_OBJECT:
                        return createToken(mapEnd, "");
                    case VALUE_NUMBER_INT:
                        return createToken(NodeParser.INT, parser.getNumberValue().toString());
                    case VALUE_NUMBER_FLOAT:
                        return createToken(NodeParser.FLOAT, parser.getNumberValue().toString());
                    case FIELD_NAME:
                        return createToken(NodeParser.STRING, parser.getCurrentName());
                    case VALUE_TRUE:
                        return createToken(NodeParser.BOOL, "true");
                    case VALUE_FALSE:
                        return createToken(NodeParser.BOOL, "false");
                    case VALUE_NULL:
                        return createToken(NodeParser.NULL, "null");
                    case VALUE_STRING:
                        return createToken(NodeParser.STRING, parser.getText());
                    default:
                        throw new IllegalStateException("Unsupported json token: " + jsonToken);
                }
            } else {
                parser.close();
                return factory.create(IntStream.EOF, null);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getLine() {
        return parser.getTokenLocation().getLineNr();
    }

    @Override
    public int getCharPositionInLine() {
        return parser.getTokenLocation().getColumnNr();
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

    private Token createToken(final int type, final String text) {
        final Pair<TokenSource, CharStream> source = new Pair<>(this, null);

        final NodeToken nodeToken = factory.create(source, type, text, Token.DEFAULT_CHANNEL,
                0, 0,
                getLine(), getCharPositionInLine());
        nodeToken.setLocation(uri.toString());

        return nodeToken;
    }
}

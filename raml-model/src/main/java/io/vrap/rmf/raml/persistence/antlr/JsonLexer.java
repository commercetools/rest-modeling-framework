package io.vrap.rmf.raml.persistence.antlr;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.Pair;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;

import java.io.IOException;

class JsonLexer implements TokenSource {
    private final URI uri;
    private RamlTokenFactory factory;
    private final JsonFactory jsonFactory;
    private final JsonParser parser;

    private final int mapStart  = RAMLParser.MAP_START;
    private final int mapEnd = RAMLParser.MAP_END;
    private final int listStart = RAMLParser.LIST_START;
    private final int listEnd = RAMLParser.LIST_END;
    private final int scalar = RAMLParser.SCALAR;

    public JsonLexer(final URI uri, final URIConverter uriConverter) {
        this.uri = uri;
        factory = RamlTokenFactory.DEFAULT;
        jsonFactory = new JsonFactory();
        try {
            parser = jsonFactory.createParser(uriConverter.createInputStream(uri));
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
                    case VALUE_NUMBER_FLOAT:
                        return createToken(scalar, parser.getNumberValue().toString());
                    case FIELD_NAME:
                        return createToken(scalar, parser.getCurrentName());
                    case VALUE_TRUE:
                        return createToken(scalar, "true");
                    case VALUE_FALSE:
                        return createToken(scalar, "false");
                    case VALUE_NULL:
                        return createToken(scalar, "null");
                    case VALUE_STRING:
                        return createToken(scalar, parser.getText());
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
        return parser.getCurrentLocation().getLineNr();
    }

    @Override
    public int getCharPositionInLine() {
        return parser.getCurrentLocation().getColumnNr();
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
        this.factory = (RamlTokenFactory) factory;
    }

    @Override
    public TokenFactory<?> getTokenFactory() {
        return factory;
    }

    private Token createToken(final int type, final String text) {
        final Pair<TokenSource, CharStream> source = new Pair<>(this, null);

        final RamlToken ramlToken = factory.create(source, type, text, Token.DEFAULT_CHANNEL,
                0, 0,
                getLine(), getCharPositionInLine());
        ramlToken.setLocation(uri.toString());

        return ramlToken;
    }
}

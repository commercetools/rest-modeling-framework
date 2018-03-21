package io.vrap.rmf.raml.persistence.antlr;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.Pair;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Stack;

public class JacksonLexer implements TokenSource {
    private final URI uri;
    private final RamlTokenFactory factory;
    private final Stack<JsonNode> nextNode = new Stack<>();
    private final Stack<Iterator<JsonNode>> children = new Stack<>();

    private JacksonLexer(InputStream input, final URI uri) {
        this.uri = uri;
        factory = RamlTokenFactory.DEFAULT;
        final JsonFactory jsonFactory;
        switch (uri.fileExtension()) {
            case "json":
                jsonFactory = new JsonFactory();
                break;
            default:
                jsonFactory = new YAMLFactory();
        }
        final ObjectMapper objectMapper = new ObjectMapper(jsonFactory);
        try {
            final JsonNode tree = objectMapper.readTree(input);
            nextNode.push(tree);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public JacksonLexer(final String input, final URI uri) {
        this(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)), uri);
    }

    public JacksonLexer(final URI uri, final URIConverter uriConverter) {
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
        if (nextNode.isEmpty()) {
            return factory.create(IntStream.EOF, null);
        } else {
            final Token token;
            final JsonNode node = nextNode.peek();
            if (node.isValueNode()) {
                final int tokenType;
                switch (node.getNodeType()) {
                    case NUMBER:
                        if (node.isIntegralNumber()) {
                            tokenType = RAMLParser.INT;
                        } else {
                            tokenType = RAMLParser.FLOAT;
                        }
                        break;
                    case BOOLEAN:
                        tokenType = RAMLParser.BOOL;
                        break;
                    default:
                        tokenType = RAMLParser.SCALAR;
                }
                token = createToken(tokenType, node.asText());
            } else {
                token = null;
            }
            return token;
        }
    }

    @Override
    public int getLine() {
        return 0;
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
        return null;
    }

    @Override
    public void setTokenFactory(final TokenFactory<?> factory) {

    }

    @Override
    public TokenFactory<?> getTokenFactory() {
        return null;
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

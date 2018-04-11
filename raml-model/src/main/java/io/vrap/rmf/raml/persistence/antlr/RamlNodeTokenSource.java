package io.vrap.rmf.raml.persistence.antlr;

import io.vrap.rmf.nodes.Node;
import io.vrap.rmf.nodes.NodeModelBuilder;
import io.vrap.rmf.nodes.antlr.NodeParser;
import io.vrap.rmf.nodes.antlr.NodeToken;
import org.antlr.v4.runtime.*;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RamlNodeTokenSource implements TokenSource {
    private static final Pattern ANNOTATION_TYPE_REF_PATTERN = Pattern.compile("\\(([^\\)]*)\\)");

    private final URI uri;
    private final NodeModelBuilder nodeModelBuilder = new NodeModelBuilder();
    private final Node node;
    private final List<NodeToken> tokens;
    private int index;

    private Map<String, Integer> literalTokenTypes = new HashMap<>();

    private RamlNodeTokenSource(final URI uri, final Node node) {
        initTokens();
        this.uri = uri;
        this.node = node;
        tokens = nodeModelBuilder.asToken(node)
                .stream()
                .map(this::tokenize)
                .collect(Collectors.toList());
    }

    public RamlNodeTokenSource(final URI uri, final URIConverter uriConverter) {
        this(uri, new NodeModelBuilder().parse(uri, uriConverter));
    }

    public RamlNodeTokenSource(final String input, final URI uri, final URIConverter uriConverter) {
        this(uri, new NodeModelBuilder().parse(input, uri, uriConverter));
    }


    private void initTokens() {
        final Vocabulary vocabulary = RAMLParser.VOCABULARY;
        for (int tokenType = 0; tokenType <= vocabulary.getMaxTokenType(); tokenType++) {
            final String literalName = vocabulary.getLiteralName(tokenType);
            if (literalName != null) {
                final String literalText = literalName.substring(1, literalName.length() - 1);
                literalTokenTypes.put(literalText, tokenType);
            }
        }
    }

    private NodeToken tokenize(final NodeToken nodeToken) {
        final int type;
        String text = nodeToken.getText();
        if (nodeToken.getType() == NodeParser.STRING) {
            final Matcher matcher = ANNOTATION_TYPE_REF_PATTERN.matcher(text);
            if (literalTokenTypes.containsKey(text)) {
                type = literalTokenTypes.get(text);
            } else if (text.startsWith("/") && !text.endsWith("/")) {
                type = RAMLParser.RELATIVE_URI;
            } else if (matcher.matches()) {
                type = RAMLParser.ANNOTATION_TYPE_REF;
                text = matcher.group(1);
            } else {
                type = RAMLParser.SCALAR;
            }
        } else {
            switch (nodeToken.getType()) {
                case NodeParser.MAP_START:
                    type = RAMLParser.MAP_START;
                    break;
                case NodeParser.MAP_END:
                    type = RAMLParser.MAP_END;
                    break;
                case NodeParser.INT:
                    type = RAMLParser.INT;
                    break;
                case NodeParser.BOOL:
                    type = RAMLParser.BOOL;
                    break;
                case NodeParser.FLOAT:
                    type = RAMLParser.FLOAT;
                    break;
                case NodeParser.LIST_START:
                    type = RAMLParser.LIST_START;
                    break;
                case NodeParser.LIST_END:
                    type = RAMLParser.LIST_END;
                    break;
                case NodeParser.NULL:
                    type = RAMLParser.SCALAR;
                    break;
                default:
                    type = nodeToken.getType();
                    break;
            }
        }
        return nodeToken.withType(type, text);
    }

    @Override
    public Token nextToken() {
        return index < tokens.size() ?
                tokens.get(index++) : new NodeToken(IntStream.EOF, null);
    }

    @Override
    public int getLine() {
        return index < tokens.size() ?
                tokens.get(index).getLine() : -1;
    }

    @Override
    public int getCharPositionInLine() {
        return index < tokens.size() ?
                tokens.get(index).getCharPositionInLine() : -1;
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

    }

    @Override
    public TokenFactory<?> getTokenFactory() {
        return null;
    }
}

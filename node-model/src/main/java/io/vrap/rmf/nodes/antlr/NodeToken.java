package io.vrap.rmf.nodes.antlr;

import io.vrap.rmf.nodes.Node;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.misc.Pair;

/**
 * Common token with additional location info and with a link to the corresponding node {@link #getNode()}.
 */
public class NodeToken extends CommonToken {
    private String location;
    private String includeUri;
    private Node node;

    public NodeToken(final Pair<TokenSource, CharStream> source,
                     final int type, final int channel, final int start, final int stop) {
        super(source, type, channel, start, stop);
    }

    public NodeToken(int type, String text) {
        super(type, text);
    }

    /**
     * The location (filename/URI) of this token.
     * This is useful when using {@code !include} tags.
     *
     * @return the location or null
     */
    public String getLocation() {
        return location;
    }

    void setLocation(final String location) {
        this.location = location;
    }

    public String getIncludeUri() {
        return includeUri;
    }

    void setIncludeUri(final String includeUri) {
        this.includeUri = includeUri;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(final Node node) {
        this.node = node;
    }

    @Override
    public String toString(Recognizer r) {
        if (location == null) {
            return super.toString(r);
        } else {
            final int lastSegmentIndex = location.lastIndexOf("/");
            final String lastSegment = lastSegmentIndex > 0 ?
                    location.substring(lastSegmentIndex + 1) : "";
            return String.format("%s(%s:%d:%d)", location, lastSegment, getLine(), getCharPositionInLine());
        }
    }

    /**
     * This methods copies this token and sets its type to the given type and text.
     *
     * @param newType the new token type
     * @param newText the new token text
     * @return a copy of this token with the given type
     */
    public NodeToken withType(final int newType, final String newText) {
        final NodeToken nodeToken = new NodeToken(source, newType, channel, start, stop);

        nodeToken.setText(newText);

        nodeToken.setTokenIndex(getTokenIndex());
        nodeToken.setLine(getLine());
        nodeToken.setCharPositionInLine(getCharPositionInLine());
        nodeToken.setLocation(getLocation());

        nodeToken.setIncludeUri(getIncludeUri());
        nodeToken.setNode(getNode());

        return nodeToken;
    }

    /**
     * Copies this token.
     *
     * @return the copy of this token
     */
    public NodeToken copy() {
        return withType(type, text);
    }
}

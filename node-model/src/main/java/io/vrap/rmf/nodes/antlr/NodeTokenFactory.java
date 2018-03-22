package io.vrap.rmf.nodes.antlr;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.TokenFactory;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.misc.Pair;

/**
 * Token factory for {@link NodeToken}.
 */
public class NodeTokenFactory implements TokenFactory<NodeToken> {
    public static final NodeTokenFactory DEFAULT = new NodeTokenFactory();

    @Override
    public NodeToken create(Pair<TokenSource, CharStream> source, int type, String text, int channel, int start, int stop, int line, int charPositionInLine) {
        NodeToken nodeToken = new NodeToken(source, type, channel, start, stop);
        nodeToken.setText(text);
        return nodeToken;
    }

    @Override
    public NodeToken create(int type, String text) {
        return new NodeToken(type, text);
    }
}

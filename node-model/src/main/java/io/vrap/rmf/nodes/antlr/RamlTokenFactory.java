package io.vrap.rmf.nodes.antlr;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.TokenFactory;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.misc.Pair;

/**
 * Token factory for {@link RamlToken}.
 */
public class RamlTokenFactory implements TokenFactory<RamlToken> {
    public static final RamlTokenFactory DEFAULT = new RamlTokenFactory();

    @Override
    public RamlToken create(Pair<TokenSource, CharStream> source, int type, String text, int channel, int start, int stop, int line, int charPositionInLine) {
        RamlToken ramlToken = new RamlToken(source, type, channel, start, stop);
        ramlToken.setText(text);
        return ramlToken;
    }

    @Override
    public RamlToken create(int type, String text) {
        return new RamlToken(type, text);
    }
}

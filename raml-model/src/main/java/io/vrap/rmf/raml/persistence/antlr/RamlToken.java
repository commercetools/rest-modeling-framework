package io.vrap.rmf.raml.persistence.antlr;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.Pair;

/**
 * Common token with additional location info.
 */
public class RamlToken extends CommonToken {
    private String location;

    public RamlToken(final Pair<TokenSource, CharStream> source,
                     final int type, final int channel, final int start, final int stop) {
        super(source, type, channel, start, stop);
    }

    public RamlToken(int type, String text) {
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
}

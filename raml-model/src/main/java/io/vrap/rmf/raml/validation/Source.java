package io.vrap.rmf.raml.validation;

public class Source {
    private final String location;
    private final long line;
    private final long charPositionInLine;


    @Override
    public String toString() {
        return "Source(" +
                "location='" + location + '\'' +
                ", line=" + line +
                ", charPositionInLine=" + charPositionInLine +
                ')';
    }

    public Source(final String location, final long line, final long charPositionInLine) {
        this.location = location;
        this.line = line;
        this.charPositionInLine = charPositionInLine;
    }

    public String getLocation() {
        return location;
    }

    public long getLine() {
        return line;
    }

    public long getCharPositionInLine() {
        return charPositionInLine;
    }
}

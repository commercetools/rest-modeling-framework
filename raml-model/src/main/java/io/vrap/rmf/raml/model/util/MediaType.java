package io.vrap.rmf.raml.model.util;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import io.vrap.rmf.raml.persistence.antlr.MediaTypeBaseVisitor;
import io.vrap.rmf.raml.persistence.antlr.MediaTypeLexer;
import io.vrap.rmf.raml.persistence.antlr.MediaTypeParser;
import io.vrap.rmf.raml.persistence.antlr.ParserErrorCollector;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.Objects;

/**
 * This class represents a MIME media type.
 */
public final class MediaType {
    private final String type;
    private final String subtype;

    private MediaType(final String type, final String subtype) {
        this.type = type;
        this.subtype = subtype;
    }

    public String getType() {
        return type;
    }

    public String getSubtype() {
        return subtype;
    }

    @Override
    public String toString() {
        return Joiner.on("/").join(type, subtype);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MediaType mediaType = (MediaType) o;
        return Objects.equals(type, mediaType.type) &&
                Objects.equals(subtype, mediaType.subtype);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, subtype);
    }

    /**
     * Creates a new media type object.
     *
     * @param type    the type
     * @param subtype the subtype
     * @return the new media type
     */
    public static MediaType of(final String type, final String subtype) {
        return new MediaType(type, subtype);
    }

    /**
     * Parses the given media type string into a media type object.
     *
     * @param mediaType the media type string to parse
     * @return the parsed media type
     */
    public static MediaType of(final String mediaType) {
        final MediaTypeLexer lexer = new MediaTypeLexer(CharStreams.fromString(mediaType));
        final ParserErrorCollector errorCollector = new ParserErrorCollector();
        lexer.addErrorListener(errorCollector);
        final CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        final MediaTypeParser mediaTypeParser = new MediaTypeParser(tokenStream);
        mediaTypeParser.addErrorListener(errorCollector);
        final MediaTypeParser.MediaTypeContext mediaTypeCtx = mediaTypeParser.mediaType();
        if (errorCollector.getErrors().isEmpty()) {
            return new MediaTypeVisitor().visit(mediaTypeCtx);
        } else {
            throw new IllegalArgumentException("Illegal media type '" + mediaType + "'");
        }
    }

    private static class MediaTypeVisitor extends MediaTypeBaseVisitor<MediaType> {
        @Override
        public MediaType visitMediaType(final MediaTypeParser.MediaTypeContext mediaType) {
            return of(mediaType.type.getText(), mediaType.subtype.getText());
        }
    }
}

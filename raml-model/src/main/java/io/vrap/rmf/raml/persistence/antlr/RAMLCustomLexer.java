package io.vrap.rmf.raml.persistence.antlr;

import io.vrap.functional.utils.TypeSwitch;
import org.antlr.v4.runtime.*;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.events.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.*;

/**
 * An antlr lexer that uses snakeyaml events {@link Event} to
 * generate antlr tokens.
 */
public class RAMLCustomLexer implements TokenSource {
    private static final String MAP_START = "MAP_START";
    private static final String MAP_END = "MAP_END";
    private static final String LIST_START = "LIST_START";
    private static final String LIST_END = "LIST_END";
    private static final String SCALAR = "SCALAR";

    private final Yaml yaml = new Yaml();
    private final Iterator<Event> eventIterator;
    private Map<String, Integer> literalTokenTypes = new HashMap<>();
    private Map<String, Integer> symbolTokenTypes = new HashMap<>();
    private final TypeSwitch<Event, Token> eventSwitch;

    private TokenFactory<?> factory;
    private Event currentEvent;

    private final int mapStart;
    private final int mapEnd;
    private final int listStart;
    private final int listEnd;
    private final int scalar;

    public RAMLCustomLexer(final Reader reader) {
        initTokens();
        mapStart = symbolTokenTypes.get(MAP_START);
        mapEnd = symbolTokenTypes.get(MAP_END);
        listStart = symbolTokenTypes.get(LIST_START);
        listEnd = symbolTokenTypes.get(LIST_END);
        scalar = symbolTokenTypes.get(SCALAR);

        this.eventIterator = yaml.parse(reader).iterator();
        eventSwitch = new TypeSwitch<Event, Token>()
                .on(MappingStartEvent.class, this::mapStart)
                .on(MappingEndEvent.class, this::mapEnd)
                .on(SequenceStartEvent.class, this::listStart)
                .on(SequenceEndEvent.class, this::listEnd)
                .on(ScalarEvent.class, this::scalar)
                .fallthrough(event -> getTokenFactory().create(Token.INVALID_TYPE, null));
    }

    private void initTokens() {
        final Properties tokens = loadTokens();
        final Set<String> stringPropertyNames = tokens.stringPropertyNames();
        for (final String token : stringPropertyNames) {
            final int tokenType = Integer.parseInt(tokens.getProperty(token));
            if (token.startsWith("'")) {
                final String keyWord = token.substring(1, token.length() - 1);
                literalTokenTypes.put(keyWord, tokenType);
            } else {
                symbolTokenTypes.put(token, tokenType);
            }
        }
    }

    private Properties loadTokens() {
        final Properties tokens = new Properties();
        try (final InputStream inputStream = getClass ().getResourceAsStream("RAML.tokens")) {
              tokens.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return tokens;
    }

    private Token mapStart(final MappingStartEvent event) {
        return factory.create(mapStart, null);
    }

    private Token mapEnd(final MappingEndEvent event) {
        return factory.create(mapEnd, null);
    }

    private Token listStart(final SequenceStartEvent event) {
        return factory.create(listStart, null);
    }

    private Token listEnd(final SequenceEndEvent event) {
        return factory.create(listEnd, null);
    }

    private Token scalar(final ScalarEvent scalarEvent) {
        final String value = scalarEvent.getValue();
        final int type = literalTokenTypes.containsKey(value) ?
                literalTokenTypes.get(value) :
                scalar;
        return factory.create(type, value);
    }

    @Override
    public Token nextToken() {
        while (eventIterator.hasNext()) {
            currentEvent = eventIterator.next();
            final Token token = eventSwitch.apply(currentEvent);
            if (token.getType() != Token.INVALID_TYPE) {
                System.out.println(token);
                return token;
            }
        }
        return factory.create(IntStream.EOF, null);
    }

    @Override
    public int getLine() {
        return currentEvent.getStartMark().getLine();
    }

    @Override
    public int getCharPositionInLine() {
        return currentEvent.getStartMark().getIndex();
    }

    @Override
    public CharStream getInputStream() {
        return null;
    }

    @Override
    public String getSourceName() {
        return IntStream.UNKNOWN_SOURCE_NAME;
    }

    @Override
    public void setTokenFactory(final TokenFactory<?> factory) {
        this.factory = factory;
    }

    @Override
    public TokenFactory<?> getTokenFactory() {
        return factory;
    }
}

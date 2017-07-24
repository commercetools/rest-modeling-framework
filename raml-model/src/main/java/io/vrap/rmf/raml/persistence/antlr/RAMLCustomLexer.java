package io.vrap.rmf.raml.persistence.antlr;

import io.vrap.functional.utils.TypeSwitch;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.Pair;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.events.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private static final String ANNOTATION_TYPE_REF = "ANNOTATION_TYPE_REF";

    private static final String INCLUDE_TAG = "!include";

    private static final Pattern ANNOTATION_TYPE_REF_PATTERN = Pattern.compile("\\(([^\\)]*)\\)");

    private final Yaml yaml = new Yaml();
    private final Stack<Iterator<Event>> eventIteratorStack = new Stack<>();
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
    private final int annotationTypeRef;
    private final Stack<URI> uri = new Stack<>();
    private final URIConverter uriConverter;

    public RAMLCustomLexer(final URI uri, final URIConverter uriConverter) {
        initTokens();
        this.uriConverter = uriConverter;
        mapStart = symbolTokenTypes.get(MAP_START);
        mapEnd = symbolTokenTypes.get(MAP_END);
        listStart = symbolTokenTypes.get(LIST_START);
        listEnd = symbolTokenTypes.get(LIST_END);
        scalar = symbolTokenTypes.get(SCALAR);
        annotationTypeRef = symbolTokenTypes.get(ANNOTATION_TYPE_REF);
        eventSwitch = new TypeSwitch<Event, Token>()
                .on(MappingStartEvent.class, this::mapStart)
                .on(MappingEndEvent.class, this::mapEnd)
                .on(SequenceStartEvent.class, this::listStart)
                .on(SequenceEndEvent.class, this::listEnd)
                .on(ScalarEvent.class, this::scalar)
                .fallthrough(event -> getTokenFactory().create(Token.INVALID_TYPE, null));

        loadEvents(uri);
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


    private void loadEvents(final URI uri) {
        if (this.uri.contains(uri)) {
            // TODO add circular include error
        } else {
            this.uri.push(uri);
            final Iterator<Event> eventIterator;
            try (final InputStreamReader reader = new InputStreamReader(uriConverter.createInputStream(uri))) {
                final Iterable<Event> eventIterable = yaml.parse(reader);

                final List<Event> eagerLoadedEvents = new ArrayList<>();
                eventIterable.forEach(eagerLoadedEvents::add);
                eventIterator = eagerLoadedEvents.iterator();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.eventIteratorStack.push(eventIterator);
        }
    }

    private Token mapStart(final MappingStartEvent event) {
        return createToken(mapStart, null);
    }

    private Token mapEnd(final MappingEndEvent event) {
        return createToken(mapEnd, null);
    }

    private Token listStart(final SequenceStartEvent event) {
        return createToken(listStart, null);
    }

    private Token listEnd(final SequenceEndEvent event) {
        return createToken(listEnd, null);
    }

    private Token scalar(final ScalarEvent scalarEvent) {
        final String scalarValue = scalarEvent.getValue();
        final Matcher matcher = ANNOTATION_TYPE_REF_PATTERN.matcher(scalarValue);
        final int type = literalTokenTypes.containsKey(scalarValue) ?
                literalTokenTypes.get(scalarValue) :
                matcher.matches() ?
                        annotationTypeRef :
                        scalar;
        final String text = matcher.matches() ?
                matcher.group(1) :
                scalarValue;

        return createToken(type, text);
    }

    private URI resolve(final String relativePath) {
        final String[] segments = URI.createURI(relativePath).segments();
        return getBaseUri().appendSegments(segments);
    }

    private URI getBaseUri() {
        return uri.peek().trimSegments(1);
    }

    @Override
    public Token nextToken() {
        while (eventIteratorStack.size() > 0 && eventIterator().hasNext()) {
            currentEvent = eventIterator().next();
            if (currentEvent instanceof ScalarEvent) {
                final ScalarEvent scalarEvent = (ScalarEvent) currentEvent;
                if (INCLUDE_TAG.equals(scalarEvent.getTag())) {
                    final String importUri = scalarEvent.getValue();
                    final URI uri = resolve(importUri);
                    loadEvents(uri);
                } else {
                    final Token token = eventSwitch.apply(currentEvent);
                    return token;
                }
            } else {
                final Token token = eventSwitch.apply(currentEvent);
                if (token.getType() != Token.INVALID_TYPE) {
                    return token;
                }
            }
        }
        if (eventIteratorStack.empty()) {
            return factory.create(IntStream.EOF, null);
        } else {
            eventIteratorStack.pop();
            uri.pop();

            return nextToken();
        }
    }

    private Iterator<Event> eventIterator() {
        return eventIteratorStack.peek();
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
        return uri.peek().toString();
    }

    @Override
    public void setTokenFactory(final TokenFactory<?> factory) {
        this.factory = factory;
    }

    @Override
    public TokenFactory<?> getTokenFactory() {
        return factory;
    }

    private Token createToken(final int type, final String text) {
        final Pair<TokenSource, CharStream> source = new Pair<>(this, null);

        return factory.create(source, type, text, Token.DEFAULT_CHANNEL,
                0, 0,
                getLine(), getCharPositionInLine());
    }
}

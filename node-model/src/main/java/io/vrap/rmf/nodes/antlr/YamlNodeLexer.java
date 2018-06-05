package io.vrap.rmf.nodes.antlr;

import com.google.common.collect.ImmutableSet;
import io.vrap.functional.utils.TypeSwitch;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.Pair;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.events.*;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.resolver.Resolver;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A node lexer that can tokenize yaml.
 */
class YamlNodeLexer implements TokenSource {
    private static final String INCLUDE_TAG = "!include";
    private static final Set<Event.ID> RELEVANT_EVENT_IDS =
            ImmutableSet.of(Event.ID.MappingEnd, Event.ID.MappingStart, Event.ID.SequenceEnd, Event.ID.SequenceStart, Event.ID.Scalar);
    public static final Mark EMPTY_FILE_MARK = new Mark(null, 0, 0, 0, "", 0);
    public static final ScalarEvent EMPTY_SCALAR_EVENT = new ScalarEvent(null, null, null, "", EMPTY_FILE_MARK, EMPTY_FILE_MARK, null);

    private static final Resolver IMPLICIT_TAG_RESOLVER = new Resolver();
    private final Yaml yaml = new Yaml();
    private Iterator<Event> eventIterator;
    private Map<String, Integer> literalTokenTypes = new HashMap<>();
    private final TypeSwitch<Event, Token> eventSwitch;
    private final Map<Tag, Integer> scalarTagTokenTypes = new HashMap<>();
    private NodeTokenFactory factory;
    private Event currentEvent;

    private final int mapStart  = NodeParser.MAP_START;
    private final int mapEnd = NodeParser.MAP_END;
    private final int listStart = NodeParser.LIST_START;
    private final int listEnd = NodeParser.LIST_END;
    private final int scalar = NodeParser.STRING;

    private URI uri;
    private final URIConverter uriConverter;

    public YamlNodeLexer(final URI uri, final URIConverter uriConverter) {
        this(uriConverter);
        this.uri = uri;
        loadEvents(uri);
    }

    public YamlNodeLexer(final String input, final URI uri, final URIConverter uriConverter) {
        this(uriConverter);
        this.uri = uri;
        final InputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        loadEvents(inputStream);
    }

    private YamlNodeLexer(final URIConverter uriConverter) {
        initTokens();
        setTokenFactory(NodeTokenFactory.DEFAULT);
        this.uriConverter = uriConverter;
        eventSwitch = new TypeSwitch<Event, Token>()
                .on(MappingStartEvent.class, this::mapStart)
                .on(MappingEndEvent.class, this::mapEnd)
                .on(SequenceStartEvent.class, this::listStart)
                .on(SequenceEndEvent.class, this::listEnd)
                .on(ScalarEvent.class, this::scalar)
                .fallthrough(event -> getTokenFactory().create(Token.INVALID_TYPE, null));
    }

    private void initTokens() {
        final Vocabulary vocabulary = NodeParser.VOCABULARY;
        for (int tokenType = 0; tokenType <= vocabulary.getMaxTokenType(); tokenType++) {
            final String literalName = vocabulary.getLiteralName(tokenType);
            if (literalName != null) {
                final String literalText = literalName.substring(1, literalName.length() - 1);
                literalTokenTypes.put(literalText, tokenType);
            }
        }
        scalarTagTokenTypes.put(Tag.FLOAT, NodeParser.FLOAT);
        scalarTagTokenTypes.put(Tag.INT, NodeParser.INT);
        scalarTagTokenTypes.put(Tag.BOOL, NodeParser.BOOL);
        scalarTagTokenTypes.put(Tag.NULL, NodeParser.NULL);
    }

    private void loadEvents(final URI uri) {
            try {
                final InputStream inputStream = uriConverter.createInputStream(uri);
                loadEvents(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    private void loadEvents(InputStream inputStream) {
        try (final InputStreamReader reader = new InputStreamReader(inputStream)) {
            final Iterable<Event> eventIterable = yaml.parse(reader);

            final List<Event> eagerLoadedEvents = new ArrayList<>();
            eventIterable.forEach(eagerLoadedEvents::add);
            final List<Event> filteredEvents = eagerLoadedEvents.stream()
                    .filter(event -> RELEVANT_EVENT_IDS.stream().anyMatch(id -> event.is(id)))
                    .collect(Collectors.toList());
            final List<Event> events = filteredEvents.isEmpty() ?
                    Collections.singletonList(EMPTY_SCALAR_EVENT) :
                    filteredEvents;
            eventIterator = events.iterator();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        final Tag implicitTag = IMPLICIT_TAG_RESOLVER.resolve(
                NodeId.scalar,
                scalarValue,
                Optional.ofNullable(scalarEvent.getImplicit()).map(ImplicitTuple::canOmitTagInPlainScalar).orElse(false)
        );
        final int type = literalTokenTypes.containsKey(scalarValue) ?
                literalTokenTypes.get(scalarValue) :
                scalarTagTokenTypes.getOrDefault(implicitTag, scalar);

        return createToken(type, scalarValue);
    }

    @Override
    public Token nextToken() {
        while (eventIterator.hasNext()) {
            currentEvent = eventIterator.next();
            if (currentEvent instanceof ScalarEvent) {
                final ScalarEvent scalarEvent = (ScalarEvent) currentEvent;
                if (INCLUDE_TAG.equals(scalarEvent.getTag()) && !scalarEvent.getValue().endsWith(".graphql")) {
                    final String includeUri = scalarEvent.getValue();
                    final Token token = createToken(scalar, "", includeUri);
                    return token;
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
        return factory.create(IntStream.EOF, null);
    }

    @Override
    public int getLine() {
        return currentEvent.getStartMark().getLine();
    }

    @Override
    public int getCharPositionInLine() {
        return currentEvent.getStartMark().getColumn();
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
        this.factory = (NodeTokenFactory) factory;
    }

    @Override
    public TokenFactory<?> getTokenFactory() {
        return factory;
    }

    private Token createToken(final int type, final String text) {
        return createToken(type, text, null);
    }

    private Token createToken(final int type, final String text, final String includeUri) {
        final Pair<TokenSource, CharStream> source = new Pair<>(this, null);

        final NodeToken nodeToken = factory.create(source, type, text, Token.DEFAULT_CHANNEL,
                0, 0,
                getLine(), getCharPositionInLine());
        nodeToken.setLocation(uri.toString());
        nodeToken.setIncludeUri(includeUri);
        return nodeToken;
    }
}

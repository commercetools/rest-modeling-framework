package io.vrap.rmf.nodes;

import io.vrap.rmf.nodes.antlr.*;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import java.math.BigDecimal;

/**
 * Provides methods to build a node {@link Node} model from a string
 */
public class NodeModelBuilder {
    private final URI uri;
    private final URIConverter uriConverter;

    public NodeModelBuilder() {
        this(null, null);
    }

    /**
     * @param uri          the uri to parse a node from
     * @param uriConverter the uri converter
     */
    public NodeModelBuilder(final URI uri, final URIConverter uriConverter) {
        this.uri = uri;
        this.uriConverter = uriConverter;
    }

    /**
     * Parses a node instance from the given uri using the given uri converter.
     *
     * @return the parsed node
     */
    public Node parse() {
        final NodeLexer lexer = new NodeLexer(uri, uriConverter);
        final TokenStream tokenStream = new CommonTokenStream(lexer);
        final NodeParser nodeParser = new NodeParser(tokenStream);

        final NodeParser.NodeContext node = nodeParser.node();

        final Node parsedNode = new NodeModelBuilderVisitor().visit(node);

        return parsedNode;
    }

    /**
     * Parses the given input json as a node.
     *
     * @param input the json input
     * @return the parsed node
     */
    public Node parseJson(final String input) {
        final ResourceSetImpl resourceSet = new ResourceSetImpl();
        final URI uri = URI.createFileURI("input.json");

        final NodeLexer lexer = new NodeLexer(input, uri, resourceSet.getURIConverter());
        final TokenStream tokenStream = new CommonTokenStream(lexer);
        final NodeParser nodeParser = new NodeParser(tokenStream);

        final NodeParser.NodeContext node = nodeParser.node();

        final Node parsedNode = new NodeModelBuilderVisitor().visit(node);

        return parsedNode;
    }

    /**
     * Parses the given input yaml as a node.
     *
     * @param input the yaml input
     * @return the parsed node
     */
    public Node parseYaml(final String input) {
        final ResourceSetImpl resourceSet = new ResourceSetImpl();
        final URI uri = URI.createFileURI("input.yaml");

        final NodeLexer lexer = new NodeLexer(input, uri, resourceSet.getURIConverter());
        final TokenStream tokenStream = new CommonTokenStream(lexer);
        final NodeParser nodeParser = new NodeParser(tokenStream);

        final NodeParser.NodeContext node = nodeParser.node();

        final Node parsedNode = new NodeModelBuilderVisitor().visit(node);

        return parsedNode;
    }

    private class NodeModelBuilderVisitor extends NodeBaseVisitor<Node> {

        @Override
        public Node visitArrayNode(final NodeParser.ArrayNodeContext ctx) {
            final ArrayNode arrayNode = NodesFactory.eINSTANCE.createArrayNode();

            final NodeToken start = (NodeToken) ctx.LIST_START().getSymbol();
            start.setNode(arrayNode);

            final NodeToken stop = (NodeToken) ctx.LIST_END().getSymbol();
            stop.setNode(arrayNode);

            arrayNode.eAdapters().add(NodeParserAdapter.of(start, stop));

            ctx.node().stream()
                    .map(this::visitNode)
                    .forEach(node -> arrayNode.getElements().add(node));

            return arrayNode;
        }

        @Override
        public Node visitObjectNode(final NodeParser.ObjectNodeContext ctx) {
            final ObjectNode objectNode = NodesFactory.eINSTANCE.createObjectNode();

            final NodeToken start = (NodeToken) ctx.MAP_START().getSymbol();
            start.setNode(objectNode);

            final NodeToken stop = (NodeToken) ctx.MAP_END().getSymbol();
            stop.setNode(objectNode);

            objectNode.eAdapters().add(NodeParserAdapter.of(start, stop));

            final EList<Property> properties = objectNode.getProperties();
            for (final NodeParser.PropertyContext propertyContext : ctx.property()) {
                final Node key = visitValueNode(propertyContext.key);
                final Node node = visitNode(propertyContext.node());

                final Property property = NodesFactory.eINSTANCE.createProperty();
                property.setKey((ValueNode<?>) key);
                property.setValue(node);

                properties.add(property);
            }
            return objectNode;
        }

        @Override
        public Node visitValueNode(final NodeParser.ValueNodeContext ctx) {
            Node node = null;
            NodeToken token = null;
            if (ctx.BOOL() != null) {
                final BooleanNode booleanNode = NodesFactory.eINSTANCE.createBooleanNode();
                booleanNode.setValue(Boolean.valueOf(ctx.getText()));

                token = (NodeToken) ctx.BOOL().getSymbol();
                booleanNode.eAdapters().add(NodeParserAdapter.of(token));

                node = booleanNode;
            }
            if (ctx.STRING() != null) {
                final StringNode stringNode = NodesFactory.eINSTANCE.createStringNode();
                stringNode.setValue(ctx.getText());

                token = (NodeToken) ctx.STRING().getSymbol();
                stringNode.eAdapters().add(NodeParserAdapter.of(token));

                node = stringNode;
            }
            if (ctx.FLOAT() != null) {
                final NumberNode numberNode = NodesFactory.eINSTANCE.createNumberNode();
                numberNode.setValue(new BigDecimal(ctx.getText()));

                token = (NodeToken) ctx.FLOAT().getSymbol();
                numberNode.eAdapters().add(NodeParserAdapter.of(token));

                node = numberNode;
            }
            if (ctx.INT() != null) {
                final IntegerNode integerNode = NodesFactory.eINSTANCE.createIntegerNode();
                integerNode.setValue(Integer.parseInt(ctx.getText()));

                token = (NodeToken) ctx.INT().getSymbol();
                integerNode.eAdapters().add(NodeParserAdapter.of(token));

                node = integerNode;
            }
            if (ctx.NULL() != null) {
                final NullNode nullNode = NodesFactory.eINSTANCE.createNullNode();

                token = (NodeToken) ctx.NULL().getSymbol();
                nullNode.eAdapters().add(NodeParserAdapter.of(token));

                node = nullNode;
            }
            if (token != null) {
                token.setNode(node);
            }
            return node;
        }
    }

    private static class NodeParserAdapter extends AdapterImpl implements NodeTokenProvider {
        private final NodeToken start;
        private final NodeToken end;

        private NodeParserAdapter(final NodeToken start, final NodeToken end) {
            this.start = start;
            this.end = end;
        }


        @Override
        public boolean isAdapterForType(final Object type) {
            return type == NodeTokenProvider.class;
        }

        @Override
        public NodeToken getStart() {
            return start;
        }

        @Override
        public NodeToken getStop() {
            return end;
        }

        public static NodeParserAdapter of(final NodeToken start, final NodeToken end) {
            return new NodeParserAdapter(start, end);
        }

        public static NodeParserAdapter of(final NodeToken token) {
            return new NodeParserAdapter(token, token);
        }
    }
}

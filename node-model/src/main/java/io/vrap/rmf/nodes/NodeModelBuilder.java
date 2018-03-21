package io.vrap.rmf.nodes;

import io.vrap.rmf.nodes.antlr.NodeBaseVisitor;
import io.vrap.rmf.nodes.antlr.NodeParser;
import io.vrap.rmf.nodes.antlr.RAMLCustomLexer;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import java.math.BigDecimal;

/**
 * Provides methods to build a node {@link Node} model from a string
 */
public class NodeModelBuilder {

    /**
     * Parses the given input json as a node.
     *
     * @param input the json input
     * @return the parsed node
     */
    public Node parseJson(final String input) {
        final ResourceSetImpl resourceSet = new ResourceSetImpl();
        final URI uri = URI.createFileURI("input.json");

        final RAMLCustomLexer lexer = new RAMLCustomLexer(input, uri, resourceSet.getURIConverter());
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

        final RAMLCustomLexer lexer = new RAMLCustomLexer(input, uri, resourceSet.getURIConverter());
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
            ctx.node().stream()
                    .map(this::visitNode)
                    .forEach(node -> arrayNode.getElements().add(node));

            return arrayNode;
        }

        @Override
        public Node visitObjectNode(final NodeParser.ObjectNodeContext ctx) {
            final ObjectNode objectNode = NodesFactory.eINSTANCE.createObjectNode();

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
            if (ctx.BOOL() != null) {
                final BooleanNode booleanNode = NodesFactory.eINSTANCE.createBooleanNode();
                booleanNode.setValue(Boolean.valueOf(ctx.getText()));

                return booleanNode;
            }
            if (ctx.STRING() != null) {
                final StringNode stringNode = NodesFactory.eINSTANCE.createStringNode();
                stringNode.setValue(ctx.getText());

                return stringNode;
            }
            if (ctx.FLOAT() != null) {
                final NumberNode numberNode = NodesFactory.eINSTANCE.createNumberNode();
                numberNode.setValue(new BigDecimal(ctx.getText()));

                return numberNode;
            }
            if (ctx.INT() != null) {
                final IntegerNode integerNode = NodesFactory.eINSTANCE.createIntegerNode();
                integerNode.setValue(Integer.parseInt(ctx.getText()));

                return integerNode;
            }
            return null;
        }
    }
}

package io.vrap.rmf.nodes;

import io.vrap.rmf.nodes.antlr.NodeToken;
import io.vrap.rmf.nodes.antlr.NodeTokenProvider;
import io.vrap.rmf.nodes.util.NodesSwitch;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Collects the {@link NodeToken}s from a given {@link Node}.
 */
class NodeTokenCollector extends NodesSwitch<List<NodeToken>> {

    @Override
    public List<NodeToken> caseObjectNode(final ObjectNode objectNode) {
        final List<NodeToken> tokens = new ArrayList<>();

        final NodeTokenProvider tokenProvider = getNodeTokenProvider(objectNode);

        tokens.add(tokenProvider.getStart());
        objectNode.getProperties().stream()
                .map(this::doSwitch)
                .forEach(tokens::addAll);
        tokens.add(tokenProvider.getStop());

        return tokens;
    }

    @Override
    public List<NodeToken> caseArrayNode(final ArrayNode arrayNode) {
        final List<NodeToken> tokens = new ArrayList<>();

        final NodeTokenProvider tokenProvider = getNodeTokenProvider(arrayNode);

        tokens.add(tokenProvider.getStart());
        arrayNode.getElements().stream()
                .map(this::doSwitch)
                .forEach(tokens::addAll);
        tokens.add(tokenProvider.getStop());

        return tokens;
    }

    @Override
    public List<NodeToken> caseProperty(final Property property) {
        final List<NodeToken> tokens = new ArrayList<>();

        if (property.getKey() != null) {
            final List<NodeToken> keyTokens = doSwitch(property.getKey());
            tokens.addAll(keyTokens);
        }
        if (property.getValue() != null) {
            final List<NodeToken> valueTokens = doSwitch(property.getValue());
            tokens.addAll(valueTokens);
        }
        return tokens;
    }

    @Override
    public <T> List<NodeToken> caseValueNode(final ValueNode<T> valueNode) {
        final NodeTokenProvider nodeTokenProvider = getNodeTokenProvider(valueNode);

        return Collections.singletonList(nodeTokenProvider.getStart());
    }

    private NodeTokenProvider getNodeTokenProvider(final Node node) {
        return (NodeTokenProvider) EcoreUtil.getExistingAdapter(node, NodeTokenProvider.class);
    }
}

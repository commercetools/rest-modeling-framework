package io.vrap.rmf.nodes;


import io.vrap.rmf.nodes.util.NodesSwitch;
import org.eclipse.emf.common.util.EList;

import static io.vrap.rmf.nodes.NodeCopier.copy;

/**
 * This class provides merging of different nodes.
 */
public class NodeMerger {
    private final boolean mergeOptionalNodes;

    /**
     * @param mergeOptionalNodes if set to true, optional nodes (string nodes which value ends with '?')
     *                           will be merged into mandatory nodes
     */
    public NodeMerger(final boolean mergeOptionalNodes) {
        this.mergeOptionalNodes = mergeOptionalNodes;
    }

    /**
     * Merges the given source and target node into a newly created node.
     *
     * @param source the source node
     * @param target the target node
     *
     * @return the merged node
     */
    public Node merge(final Node source, final Node target) {
        if (source.eClass() != target.eClass()) {
            if (target instanceof NullNode || (target instanceof ValueNode && source instanceof ContainerNode)) {
                return copy(source);
            } else {
                return copy(target);
            }
        } else {
            return new MergeSwitch(target).doSwitch(source);
        }
    }

    private class MergeSwitch extends NodesSwitch<Node> {
        private final Node target;

        public MergeSwitch(final Node target) {
            this.target = target;
        }

        @Override
        public Node caseObjectNode(final ObjectNode source) {
            return mergeObjectNodes(source, (ObjectNode) target);
        }

        @Override
        public Node caseArrayNode(final ArrayNode source) {
            return mergeArrayNodes(source, (ArrayNode) target);
        }

        @Override
        public <T> Node caseValueNode(final ValueNode<T> source) {
            return  mergeValueNodes(source, target);
        }
    }

    private Node mergeValueNodes(final Node source, final Node target) {
        return copy(target);
    }

    private Node mergeArrayNodes(final ArrayNode source, final ArrayNode target) {
        final ArrayNode merged = copy(target);
        final EList<Node> elements = merged.getElements();
        source.getElements().stream()
                .map(NodeCopier::copy)
                .forEach(elements::add);

        return merged;
    }

    private Node mergeObjectNodes(final ObjectNode source, final ObjectNode target) {
        final ObjectNode merged = copy(target);

        for (final PropertyNode sourceProperty : source.getProperties()) {
            final Object key = sourceProperty.getKey().getValue();
            final String keyValue = key.toString();
            final boolean isOptionalNode = mergeOptionalNodes && keyValue.endsWith("?");
            final Object targetValue = isOptionalNode ?
                    keyValue.substring(0, keyValue.length() - 1) : key;
            final PropertyNode targetProperty = merged.getProperty(targetValue);
            if (targetProperty != null) {
                if (sourceProperty.getValue() != null && targetProperty.getValue() != null) {
                    final Node mergedValue = merge(sourceProperty.getValue(), targetProperty.getValue());
                    targetProperty.setValue(mergedValue);
                }
            } else if (!isOptionalNode) {
                final PropertyNode copied = copy(sourceProperty);
                merged.getProperties().add(copied);
            }

        }
        return merged;
    }
}

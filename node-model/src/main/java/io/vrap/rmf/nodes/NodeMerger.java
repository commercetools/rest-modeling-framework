package io.vrap.rmf.nodes;


import io.vrap.rmf.nodes.util.NodesSwitch;
import org.eclipse.emf.common.util.EList;

import static io.vrap.rmf.nodes.NodeCopier.copy;

/**
 * This class provides merging of different nodes.
 */
public class NodeMerger {

    public NodeMerger() {
    }

    public Node merge(final Node source, final Node target) {
        return merge(source, target, false);
    }

    /**
     * Merges the given source and target node into a newly created node.
     *
     * @param source the source node
     * @param target the target node
     * @param mergeOptionalToMandatoryTarget if set to true, optional nodes (string nodes which value ends with '?')
     *                           will be merged into mandatory nodes
     * @return the merged node
     */
    public Node merge(final Node source, final Node target, boolean mergeOptionalToMandatoryTarget) {
        if (source.eClass() != target.eClass()) {
            if (target instanceof NullNode || (target instanceof ValueNode && source instanceof ContainerNode)) {
                return copy(source);
            } else {
                return copy(target);
            }
        } else {
            return new MergeSwitch(target, mergeOptionalToMandatoryTarget).doSwitch(source);
        }
    }

    private class MergeSwitch extends NodesSwitch<Node> {
        private final Node target;
        private final boolean mergeOptionalToMandatoryTarget;

        public MergeSwitch(final Node target, final boolean mergeOptionalToMandatoryTarget) {
            this.target = target;
            this.mergeOptionalToMandatoryTarget = mergeOptionalToMandatoryTarget;
        }

        @Override
        public Node caseObjectNode(final ObjectNode source) {
            return mergeObjectNodes(source, (ObjectNode) target, mergeOptionalToMandatoryTarget);
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

    private Node mergeObjectNodes(final ObjectNode source, final ObjectNode target, final boolean mergeOptionalToMandatoryTarget) {
        final ObjectNode merged = copy(target);

        for (final PropertyNode sourceProperty : source.getProperties()) {
            final Object key = sourceProperty.getKey().getValue();
            final String keyValue = key.toString();

            final boolean isOptionalNode = keyValue.endsWith("?");

            PropertyNode targetProperty = merged.getProperty(keyValue);
            if (targetProperty == null && isOptionalNode) {
                final Object targetValue = keyValue.substring(0, keyValue.length() - 1);
                targetProperty = merged.getProperty(targetValue);
            }

            if (targetProperty != null) {
                if (sourceProperty.getValue() != null && targetProperty.getValue() != null) {
                    final Node mergedValue = merge(sourceProperty.getValue(), targetProperty.getValue(), false);
                    targetProperty.setValue(mergedValue);
                }
            } else if (!mergeOptionalToMandatoryTarget || !isOptionalNode) {
                final PropertyNode copied = copy(sourceProperty);
                merged.getProperties().add(copied);
            }

        }
        return merged;
    }
}

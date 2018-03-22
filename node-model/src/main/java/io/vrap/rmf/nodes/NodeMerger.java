package io.vrap.rmf.nodes;


import io.vrap.rmf.nodes.util.NodesSwitch;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;

public class NodeMerger {

    public Node merge(final Node source, final Node target) {
        if (source.eClass() != target.eClass()) {
            if (target instanceof NullNode || (target instanceof ValueNode && source instanceof ContainerNode)) {
                return EcoreUtil.copy(source);
            } else {
                return EcoreUtil.copy(target);
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
        return EcoreUtil.copy(target);
    }

    private Node mergeArrayNodes(final ArrayNode source, final ArrayNode target) {
        final ArrayNode merged = EcoreUtil.copy(target);
        final EList<Node> elements = merged.getElements();
        source.getElements().stream().map(EcoreUtil::copy).forEach(elements::add);

        return merged;
    }

    private Node mergeObjectNodes(final ObjectNode source, final ObjectNode target) {
        final ObjectNode merged = EcoreUtil.copy(target);

        for (final Property sourceProperty : source.getProperties()) {
            final Property targetProperty = merged.getProperty(sourceProperty.getKey().getValue());
            if (targetProperty != null) {
                final Node mergedValue = merge(sourceProperty.getValue(), targetProperty.getValue());
                targetProperty.setValue(mergedValue);
            } else {
                final Property copied = EcoreUtil.copy(sourceProperty);
                merged.getProperties().add(copied);
            }

        }
        return merged;
    }
}

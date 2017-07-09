package io.vrap.rmf.raml.persistence.constructor;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.vrap.functional.utils.Classes.as;
import static io.vrap.functional.utils.Classes.instancesOf;

public class EAttributeConstructor extends Constructor<NodeTuple> {
    private final EAttribute eAttribute;

    public EAttributeConstructor(final EAttribute eAttribute) {
        this.eAttribute = eAttribute;
    }

    @Override
    public Object apply(final NodeTuple nodeTuple, final Scope attributeScope) {
        final Object value = getValue(nodeTuple, attributeScope);
        return attributeScope.with(eAttribute).setValue(value);
    }

    private Object getValue(final NodeTuple nodeTuple, final Scope attributeScope) {
        final Node valueNode = nodeTuple.getValueNode();
        if (eAttribute.isMany()) {
            final Optional<SequenceNode> sequenceNode = as(SequenceNode.class, valueNode);
            if (sequenceNode.isPresent()) {
                final List<Node> nodeList = sequenceNode.get().getValue();
                if (nodeList.stream().allMatch(ScalarNode.class::isInstance)) {
                    final List<Object> values = instancesOf(ScalarNode.class, nodeList)
                            .map(ScalarNode::getValue)
                            .map(this::fromString)
                            .collect(Collectors.toList());
                    return ECollections.asEList(values);
                } else {
                    attributeScope.addError("Sequence node contains invalid nodes {0}", sequenceNode);
                }
            } else {
                return ECollections.asEList(getSingleValue(valueNode));
            }
            return ECollections.emptyEList();
        } else {
            return getSingleValue(valueNode);
        }
    }

    private Object getSingleValue(final Node node) {
        return as(ScalarNode.class, node).map(ScalarNode::getValue)
                .map(this::fromString).orElse(null);
    }

    private Object fromString(final String value) {
        return EcoreUtil.createFromString(eAttribute.getEAttributeType(), value);
    }
}

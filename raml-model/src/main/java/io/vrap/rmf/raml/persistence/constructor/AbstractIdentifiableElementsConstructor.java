package io.vrap.rmf.raml.persistence.constructor;

import org.eclipse.emf.ecore.EObject;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.NodeTuple;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractIdentifiableElementsConstructor extends Constructor<MappingNode> {

    @Override
    public Object apply(final MappingNode mappingNode, final Scope container) {
        final List<Object> elements = mappingNode.getValue().stream()
                .map(nodeTuple -> apply(nodeTuple, container))
                .collect(Collectors.toList());
        return elements;
    }

    protected Object apply(final NodeTuple nodeTuple, final Scope container) {
        final Optional<String> name = getKey(nodeTuple);
        if (name.isPresent()) {
            return create(nodeTuple, container, name.get());
        } else {
            container.addError("Name is missing for node {0}", nodeTuple.getKeyNode());
        }
        return null;
    }

    protected abstract EObject create(final NodeTuple nodeTuple, final Scope container, final String name);
}

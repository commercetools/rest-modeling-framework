package io.vrap.rmf.raml.persistence.constructor;

import org.eclipse.emf.ecore.EReference;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.NodeTuple;

import java.util.Optional;

import static io.vrap.functional.utils.Classes.as;

/**
 * This constructor forwards the construction of the referenced elements
 * to its constructor.
 */
public class EReferenceConstructor extends Constructor<NodeTuple> {
    protected final EReference eReference;
    protected final Constructor<MappingNode> constructor;


    public EReferenceConstructor(final EReference reference, final Constructor<MappingNode> constructor) {
        this.eReference = reference;
        this.constructor = constructor;
    }

    @Override
    public Object apply(final NodeTuple nodeTuple, final Scope referenceScope) {
        final Scope resolvedScope = resolve(nodeTuple.getValueNode(), referenceScope);
        final Optional<MappingNode> mappingNode = as(MappingNode.class, resolvedScope.getValueNode());

        final Object eObject;
        if (mappingNode.isPresent()) {
            eObject = constructor.apply(mappingNode.get(), resolvedScope);
        } else {
            resolvedScope.addError("Reference {0} is invalid", nodeTuple.getKeyNode());
            eObject = null;
        }
        return eObject;
    }
}

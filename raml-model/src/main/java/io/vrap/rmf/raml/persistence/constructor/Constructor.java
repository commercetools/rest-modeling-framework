package io.vrap.rmf.raml.persistence.constructor;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.yaml.snakeyaml.nodes.*;

import java.util.*;
import java.util.function.BiFunction;

import static io.vrap.functional.utils.Classes.as;

/**
 * Constructs an object from a given yaml node element.
 *
 * @param <N> the node element type
 */
public abstract class Constructor<N> implements BiFunction<N, Scope, Object> {
    private static Tag INCLUDE_TAG = new Tag("!include");
    private final Map<EStructuralFeature, Constructor<NodeTuple>> featureConstructors = new HashMap<>();
    private final List<KeyNodeMatchConstructor> keyNodeMatchConstructors = new ArrayList<>();

    public abstract Object apply(final N n, final Scope container);

    protected Optional<String> getKey(final NodeTuple nodeTuple) {
        return as(ScalarNode.class, nodeTuple.getKeyNode())
                .map(ScalarNode::getValue);
    }

    protected Optional<NodeTuple> getNodeTuple(final Node node, final EStructuralFeature feature) {
        return getNodeTuple(node, feature.getName());
    }

    protected Optional<NodeTuple> getNodeTuple(final Node node, final String key) {
        final Optional<MappingNode> mappingNodeOptional = as(MappingNode.class, node);
        if (mappingNodeOptional.isPresent()) {
            for (final NodeTuple nodeTuple : mappingNodeOptional.get().getValue()) {
                if (as(ScalarNode.class, nodeTuple.getKeyNode()).filter(scalarNode -> scalarNode.getValue().equals(key)).isPresent()) {
                    return Optional.of(nodeTuple);
                }
            }
        }
        return Optional.empty();
    }

    protected Optional<EStructuralFeature> feature(final EClass eClass, final NodeTuple nodeTuple) {
        final Optional<EStructuralFeature> feature = as(ScalarNode.class, nodeTuple.getKeyNode())
                .map(ScalarNode::getValue)
                .map(eClass::getEStructuralFeature);

        return feature;
    }

    protected Optional<Constructor<NodeTuple>> constructor(final EClass eClass, final NodeTuple nodeTuple) {
        final Optional<EStructuralFeature> feature = feature(eClass, nodeTuple);

        final Constructor<NodeTuple> nodeTupleConstructor = feature
                .map(this::constructor)
                .orElseGet(() -> constructor(nodeTuple));

        return Optional.ofNullable(nodeTupleConstructor);
    }

    protected Constructor<NodeTuple> constructor(final EStructuralFeature feature) {
        return featureConstructors.get(feature);
    }

    private Constructor<NodeTuple> constructor(final NodeTuple nodeTuple) {
        final KeyNodeMatchConstructor constructor = keyNodeMatchConstructors.stream()
                .filter(keyNodeMatchConstructor -> keyNodeMatchConstructor.test(nodeTuple))
                .findFirst()
                .orElse(null);
        return constructor;
    }

    protected Constructor<N> addConstructor(final EAttribute attribute) {
        final EAttributeConstructor constructor = new EAttributeConstructor(attribute);
        featureConstructors.put(attribute, constructor);
        return this;
    }

    protected Constructor<N> addConstruct(final EReference reference, final Constructor<NodeTuple> constructor) {
        featureConstructors.put(reference, constructor);
        return this;
    }

    protected Constructor<N> addConstructor(final EReference reference, final Constructor<MappingNode> constructor) {
        final EReferenceConstructor referenceConstructor =
                new EReferenceConstructor(reference, constructor);
        featureConstructors.put(reference, referenceConstructor);
        return this;
    }


    protected Constructor<N> addConstructor(final KeyNodeMatchConstructor constructor) {
        keyNodeMatchConstructors.add(constructor);
        return this;
    }

    /**
     * Returns the resolved node if the given value node is an include.
     * Otherwise returns the given node.
     *
     * @param valueNode    the value node
     * @param resolveScope the scope to resolve against
     * @return the resolved node
     */
    protected Scope resolve(final Node valueNode, final Scope resolveScope) {
        final boolean isInclude = INCLUDE_TAG.equals(valueNode.getTag()) &&
                        NodeId.scalar == valueNode.getNodeId();

        if (isInclude) {
            final ScalarNode includeNode = (ScalarNode) valueNode;
            final String includePath = includeNode.getValue();

            final Scope composedScope = resolveScope.compose(includePath);
            return composedScope;
        } else {
            return resolveScope.with(valueNode);
        }
    }
}

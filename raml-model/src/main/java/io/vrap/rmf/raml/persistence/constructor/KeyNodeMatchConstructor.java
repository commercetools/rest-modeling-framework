package io.vrap.rmf.raml.persistence.constructor;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;

import java.util.function.Predicate;
import java.util.regex.Pattern;

import static io.vrap.functional.utils.Classes.as;

/**
 * An abstract constructor that matches the value of the key node against a pattern.
 */
public abstract class KeyNodeMatchConstructor extends Constructor<NodeTuple> implements Predicate<NodeTuple> {
    protected final EClass eClass;
    protected final EReference reference;
    private final Pattern pattern;

    public KeyNodeMatchConstructor(final EClass eClass, final EReference reference, final Pattern pattern) {
        this.eClass = eClass;
        this.reference = reference;
        this.pattern = pattern;
    }

    @Override
    public Object apply(final NodeTuple nodeTuple, final Scope container) {
        final Object value = getKey(nodeTuple)
                .filter(key -> pattern.matcher(key).matches())
                .map(key -> constructFeature(key, nodeTuple.getValueNode(), container))
                .orElse(null);

        return value;
    }

    protected Object constructFeature(final String key, final Node valueNode, final Scope container) {
        final Object value = construct(key, container);

        return value;
    }

    protected abstract Object construct(final String key, final Scope scope);

    @Override
    public boolean test(final NodeTuple nodeTuple) {
        final boolean matches = as(ScalarNode.class, nodeTuple.getKeyNode())
                .map(scalarNode -> pattern.matcher(scalarNode.getValue()).matches())
                .orElse(false);

        return matches;
    }
}

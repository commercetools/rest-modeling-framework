package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.persistence.typeexpressions.TypeExpressionsParser;
import org.eclipse.emf.ecore.EObject;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;

import java.util.Optional;

import static io.vrap.functional.utils.Classes.as;

/**
 * Resolves a type referenced by its name. Qualified name (e.g {@code library.MyType}
 * are resolved against the libraries imported in the current scope.
 */
public class TypeReferenceConstructor extends Constructor<NodeTuple> {
    private final TypeExpressionsParser typeExpressionsParser = new TypeExpressionsParser();

    @Override
    public Object apply(final NodeTuple nodeTuple, final Scope typeScope) {
        final Optional<String> optionalTypeName = as(ScalarNode.class, nodeTuple.getValueNode())
                .map(ScalarNode::getValue);

        final EObject typeReference = optionalTypeName
                .map(typeExr -> typeExpressionsParser.parse(typeExr, typeScope))
                .map(typeScope::setValue)
                .orElse(null);

        return typeReference;
    }
}

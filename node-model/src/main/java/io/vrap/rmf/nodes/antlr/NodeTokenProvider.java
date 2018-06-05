package io.vrap.rmf.nodes.antlr;

import io.vrap.rmf.nodes.Node;
import io.vrap.rmf.nodes.PropertyNode;
import org.eclipse.emf.ecore.EObject;

/**
 * Provides an start and end token {@link NodeToken} for an object.
 */
public interface NodeTokenProvider {
    /**
     * @return the start token associated with this object or null
     */
    NodeToken getStart();

    /**
     * @return the stop token associated with this object or null
     */
    NodeToken getStop();

    default Node getNode() {
        return getStart().getNode();
    }

    /**
     * Returns the property {@link PropertyNode} that contains this node.
     *
     * @return the container property or null
     */
    default PropertyNode getPropertyContainer() {
        final EObject container = getNode().eContainer();
        return container instanceof PropertyNode ?
                (PropertyNode) container : null;
    }

    /**
     * Copies this token provider.
     *
     * @return the copy of this token provider
     */
    NodeTokenProvider copy();
}

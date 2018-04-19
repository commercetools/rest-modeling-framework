package io.vrap.rmf.nodes.antlr;

import io.vrap.rmf.nodes.Node;
import io.vrap.rmf.nodes.Property;
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
     * Returns the property {@link Property} that contains this node.
     *
     * @return the container property or null
     */
    default Property getPropertyContainer() {
        final EObject container = getNode().eContainer();
        return container instanceof Property ?
                (Property) container : null;
    }

    /**
     * Copies this token provider.
     *
     * @return the copy of this token provider
     */
    NodeTokenProvider copy();
}

package io.vrap.rmf.nodes;

import io.vrap.rmf.nodes.antlr.NodeTokenProvider;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * Provides a copy method to copy a node {@link Node} with all required information
 * to convert it back to a token stream.
 */
public class NodeCopier {

    /**
     * Copies the given node and all adapters required to convert it back to a
     * token stream.
     *
     * @param nodeElement the node element
     * @param <T> the node type
     *
     * @return the copy of the given node
     */
    public static <T extends Node> T copy(final T nodeElement) {
        final Copier copier = new Copier();
        final T copy = (T) copier.copy(nodeElement);

        return copy;
    }

    /**
     * The actual copier.
     */
    private static class Copier extends EcoreUtil.Copier {
        @Override
        public EObject copy(final EObject eObject) {
            final EObject copy = super.copy(eObject);

            final NodeTokenProvider nodeTokenProvider =
                    (NodeTokenProvider) EcoreUtil.getExistingAdapter(eObject, NodeTokenProvider.class);

            if (nodeTokenProvider != null) {
                final NodeTokenProvider nodeTokenProviderCopy = nodeTokenProvider.copy();
                final Node copiedNode = (Node) copy;

                nodeTokenProviderCopy.getStart().setNode(copiedNode);
                nodeTokenProviderCopy.getStop().setNode(copiedNode);
                copy.eAdapters().add((Adapter) nodeTokenProviderCopy);
            }
            return copy;
        }
    }
}

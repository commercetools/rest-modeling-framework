package io.vrap.rmf.nodes;

import io.vrap.rmf.nodes.antlr.NodeTokenProvider;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * Provides a copy method to copy a node element ({@link Node}, {@link Property}) with all required information
 * to convert it back to a token stream.
 */
public class NodeElementCopier {

    /**
     * Copies the given node and all adapters required to convert it back to a
     * token stream.
     *
     * @param nodeElement the node element
     * @param <T> the node element type, this is necessary to be able to handle {@link Node} and {@link Property}.
     *
     * @return the copy of the given node element
     */
    public static <T extends EObject> T copy(final T nodeElement) {
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

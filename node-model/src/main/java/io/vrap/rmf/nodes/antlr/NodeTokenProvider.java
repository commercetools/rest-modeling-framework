package io.vrap.rmf.nodes.antlr;

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
}

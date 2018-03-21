package io.vrap.rmf.nodes.antlr;

/**
 * Provides an start and end token {@link RamlToken} for an object.
 */
public interface RamlTokenProvider {
    /**
     * @return the start token associated with this object or null
     */
    RamlToken getStart();

    /**
     * @return the stop token associated with this object or null
     */
    RamlToken getStop();
}

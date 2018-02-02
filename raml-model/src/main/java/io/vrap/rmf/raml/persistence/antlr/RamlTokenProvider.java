package io.vrap.rmf.raml.persistence.antlr;

/**
 * Provides a {@link RamlToken} for an object.
 */
public interface RamlTokenProvider {
    /**
     * @return the raml token associated with this object or null
     */
    RamlToken getToken();
}

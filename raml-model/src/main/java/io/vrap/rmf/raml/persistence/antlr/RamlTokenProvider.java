package io.vrap.rmf.raml.persistence.antlr;

import io.vrap.rmf.raml.persistence.antlr.RamlToken;

import java.util.function.Supplier;

/**
 * Provides a {@link RamlToken} for an object.
 */
public interface RamlTokenProvider extends Supplier<RamlToken> {
    /**
     * @return the raml token associated with this object or null
     */
    @Override
    RamlToken get();
}

package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.persistence.antlr.RamlToken;
import io.vrap.rmf.raml.persistence.antlr.RamlTokenProvider;
import org.eclipse.emf.common.notify.impl.AdapterImpl;

/**
 * EMF adapter for {@link RamlTokenProvider}.
 */
class RAMLTokenProviderAdapter extends AdapterImpl implements RamlTokenProvider {
    private final RamlToken ramlToken;

    private RAMLTokenProviderAdapter(RamlToken ramlToken) {
        this.ramlToken = ramlToken;
    }

    @Override
    public boolean isAdapterForType(final Object type) {
        return type == RamlTokenProvider.class;
    }

    /**
     * @return the raml token associated with the adaptee
     */
    public RamlToken get() {
        return ramlToken;
    }

    public static final RAMLTokenProviderAdapter of(final RamlToken token) {
        final RAMLTokenProviderAdapter adapter = new RAMLTokenProviderAdapter(token);
        return adapter;
    }
}

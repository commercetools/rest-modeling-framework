package io.vrap.rmf.raml.model.util;

import io.vrap.rmf.raml.persistence.antlr.RamlToken;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;

/**
 * EMF adapter to provide the {@link io.vrap.rmf.raml.persistence.antlr.RamlToken} of a parsed object..
 */
public class RAMLTokenAdapter extends AdapterImpl {
    private final RamlToken ramlToken;

    private RAMLTokenAdapter(RamlToken ramlToken) {
        this.ramlToken = ramlToken;
    }

    /**
     * @return the raml token associated with the adaptee
     */
    public RamlToken getToken() {
        return ramlToken;
    }

    public static final RAMLTokenAdapter of(final RamlToken token) {
        final RAMLTokenAdapter adapter = new RAMLTokenAdapter(token);
        return adapter;
    }
}

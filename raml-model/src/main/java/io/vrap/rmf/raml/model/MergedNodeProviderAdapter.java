package io.vrap.rmf.raml.model;

import io.vrap.rmf.nodes.PropertyNode;
import org.eclipse.emf.common.notify.impl.AdapterImpl;

/**
 * This adapter acts as a cache for merged nodes.
 */
class MergedNodeProviderAdapter extends AdapterImpl implements MergedNodeProvider {
    private final PropertyNode mergedNode;

    private MergedNodeProviderAdapter(final PropertyNode mergedNode) {
        this.mergedNode = mergedNode;
    }

    @Override
    public boolean isAdapterForType(final Object type) {
        return type == MergedNodeProvider.class;
    }

    public static final MergedNodeProviderAdapter of(final PropertyNode mergedNode) {
        final MergedNodeProviderAdapter adapter = new MergedNodeProviderAdapter(mergedNode);
        return adapter;
    }

    @Override
    public PropertyNode getMergedNode() {
        return mergedNode;
    }
}

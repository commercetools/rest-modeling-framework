package io.vrap.rmf.raml.model;

import io.vrap.rmf.nodes.PropertyNode;

/**
 * Provides the merge node.
 */
interface MergedNodeProvider {
    /**
     * @return the merged node
     */
    PropertyNode getMergedNode();
}

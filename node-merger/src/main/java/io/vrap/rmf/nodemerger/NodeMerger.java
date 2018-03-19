package io.vrap.rmf.nodemerger;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Iterator;
import java.util.Map;

public class NodeMerger {

    // pre condition: source != null &&
    public JsonNode merge(final JsonNode source, final JsonNode target) {
        if (source.getNodeType() != target.getNodeType()) {
            if (target.isNull()) {
                return source;
            }
            if (source.isNull() || source.isValueNode()) {
                return target;
            }
            return null;
        } else {
            switch (source.getNodeType()) {
                case OBJECT:
                    return mergeObjectNodes((ObjectNode) source, (ObjectNode) target);
                case ARRAY:
                    return mergeArrayNode((ArrayNode) source, (ArrayNode) target);
                default:
                    return mergeValueNode(source, target);
            }
        }
    }

    private JsonNode mergeValueNode(final JsonNode source, final JsonNode target) {
        return target.deepCopy();
    }

    private JsonNode mergeArrayNode(final ArrayNode source, final ArrayNode target) {
        final ArrayNode merged = target.deepCopy();
        source.elements().forEachRemaining(merged::add);

        return merged;
    }

    private JsonNode mergeObjectNodes(final ObjectNode source, final ObjectNode target) {
        final ObjectNode merged = target.deepCopy();
        for (final Iterator<Map.Entry<String, JsonNode>> fields = source.fields(); fields.hasNext(); ) {
            final Map.Entry<String, JsonNode> field = fields.next();

            final String fieldName = field.getKey();
            final JsonNode sourceValue = field.getValue();

            if (target.has(fieldName)) {
                merged.replace(fieldName, merge(sourceValue, target.get(fieldName)));
            } else {
                merged.set(fieldName, sourceValue);
            }
        }
        return merged;
    }
}

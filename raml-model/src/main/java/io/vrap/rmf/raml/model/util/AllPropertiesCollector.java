package io.vrap.rmf.raml.model.util;

import io.vrap.rmf.raml.model.types.BuiltinType;
import io.vrap.rmf.raml.model.types.ObjectType;
import io.vrap.rmf.raml.model.types.Property;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Static helper class for collecting the properties that an {@link ObjectType}
 * inherits from it's parents {@link ObjectType#getType()}.
 */
public class AllPropertiesCollector {

    private AllPropertiesCollector() {
        // disallow instance creation
    }

    private static Map<String, Property> getAllPropertiesAsMap(final ObjectType objectType) {
        final Map<String, Property> allPropertiesAsMap = new LinkedHashMap<>();
        if (objectType != null) {
            if (!BuiltinType.OBJECT.getName().equals(objectType.getName())) {
                final ObjectType parent = (ObjectType) objectType.getType();
                allPropertiesAsMap.putAll(getAllPropertiesAsMap(parent));
            }
            for (final Property property : objectType.getProperties()) {
                allPropertiesAsMap.put(property.getName(), property);
            }
        }
        return allPropertiesAsMap;
    }

    /**
     * Returns all properties (with inherited) of the given object type.
     *
     * If an object type specializes the type of an inherited property,
     * the specialize property will be returned by this method.
     */
    public static EList<Property> getAllProperties(final ObjectType objectType) {
        final Collection<Property> values = getAllPropertiesAsMap(objectType).values();
        return ECollections.toEList(values);
    }
}

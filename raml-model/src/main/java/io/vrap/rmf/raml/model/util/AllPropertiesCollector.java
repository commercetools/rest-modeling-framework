package io.vrap.rmf.raml.model.util;

import io.vrap.rmf.raml.model.types.ObjectType;
import io.vrap.rmf.raml.model.types.ObjectTypeFacet;
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

    private static Map<String, Property> getAllPropertiesAsMapInternal(final ObjectType objectType) {
        final Map<String, Property> allPropertiesAsMap = new LinkedHashMap<>();
        if (objectType.getType() != null) {
            final ObjectType parent = (ObjectType) objectType.getType();
            allPropertiesAsMap.putAll(getAllPropertiesAsMap(parent));
        }
        allPropertiesAsMap.putAll(getPropertiesAsMapInternal(objectType));
        return allPropertiesAsMap;
    }

    private static Map<String, Property> getPropertiesAsMapInternal(final ObjectTypeFacet objectType) {
        final Map<String, Property> allPropertiesAsMap = new LinkedHashMap<>();
        if (objectType != null) {
            for (final Property property : objectType.getProperties()) {
                allPropertiesAsMap.put(property.getName(), property);
            }
        }
        return allPropertiesAsMap;
    }

    public static Map<String, Property> getAllPropertiesAsMap(final ObjectTypeFacet objectTypeFacet) {
        if (objectTypeFacet instanceof ObjectType) {
            final ObjectType objectType = (ObjectType) objectTypeFacet;
            return getAllPropertiesAsMapInternal(objectType);
        } else {
            return getPropertiesAsMapInternal(objectTypeFacet);
        }
    }

    /**
     * Returns all properties (with inherited) of the given object type.
     * <p>
     * If an object type specializes the type of an inherited property,
     * the specialize property will be returned by this method.
     */
    public static EList<Property> getAllProperties(final ObjectType objectType) {
        final Collection<Property> values = getAllPropertiesAsMapInternal(objectType).values();
        return ECollections.toEList(values);
    }
}

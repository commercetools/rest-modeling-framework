package io.vrap.rmf.raml.generic.generator;

import io.vrap.rmf.raml.generic.generator.PropertyGenModel;

public class SerializerGenModel {
    final private PropertyGenModel property;
    final private String serializer;
    final private String format;

    public SerializerGenModel(final PropertyGenModel property, final String serializer, final String format) {
        this.property = property;
        this.serializer = serializer;
        this.format = format;
    }

    public String getSerializer() {
        return serializer;
    }

    public String getFormat() {
        return format;
    }

    public PropertyGenModel getProperty() {
        return property;
    }
}

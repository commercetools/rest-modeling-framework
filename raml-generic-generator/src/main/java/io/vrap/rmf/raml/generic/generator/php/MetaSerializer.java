package io.vrap.rmf.raml.generic.generator.php;

public class MetaSerializer {
    final private MetaProperty property;
    final private String serializer;
    final private String format;

    public MetaSerializer(final MetaProperty property, final String serializer, final String format) {
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

    public MetaProperty getProperty() {
        return property;
    }
}

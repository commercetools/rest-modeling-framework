package io.vrap.rmf.raml.generic.generator.php;

import javax.annotation.Nullable;

public class MetaGetter {
    final private String getter;
    final private MetaProperty property;
    final private String format;

    public MetaGetter(final String getter, final MetaProperty property) {
        this(getter, property, null);
    }

    public MetaGetter(final String getter, final MetaProperty property, final String format) {
        this.getter = getter;
        this.property = property;
        this.format = format;
    }

    public String getGetter() {
        return getter;
    }

    public MetaProperty getProperty() {
        return property;
    }

    @Nullable
    public String getFormat() {
        return format;
    }

    public MetaProperty getBaseProperty() {
        return new MetaProperty(MetaHelper.getBaseProperty(property.getProperty()));
    }
}

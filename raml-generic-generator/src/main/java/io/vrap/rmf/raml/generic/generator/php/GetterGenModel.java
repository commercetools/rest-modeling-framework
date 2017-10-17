package io.vrap.rmf.raml.generic.generator.php;

import javax.annotation.Nullable;

public class GetterGenModel {
    final private String getter;
    final private PropertyGenModel property;
    final private String format;

    public GetterGenModel(final String getter, final PropertyGenModel property) {
        this(getter, property, null);
    }

    public GetterGenModel(final String getter, final PropertyGenModel property, final String format) {
        this.getter = getter;
        this.property = property;
        this.format = format;
    }

    public String getGetter() {
        return getter;
    }

    public PropertyGenModel getProperty() {
        return property;
    }

    @Nullable
    public String getFormat() {
        return format;
    }

    @Nullable
    public String getFormatTest() {
        return format;
    }

    public PropertyGenModel getBaseProperty() {
        return new PropertyGenModel(GeneratorHelper.getBaseProperty(property.getProperty()));
    }
}

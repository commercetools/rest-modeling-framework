package io.vrap.rmf.raml.generic.generator;

import io.vrap.rmf.raml.model.types.AnyType;

import javax.annotation.Nullable;

public class GetterGenModel {
    final private String getter;
    final private PropertyGenModel property;
    final private TypeGenModel type;
    final private String format;

    public GetterGenModel(final String getter, final AnyType type, final PropertyGenModel property) {
        this(getter, type, property, null);
    }

    public GetterGenModel(final String getter, final AnyType type, final PropertyGenModel property, final String format) {
        this.getter = getter;
        this.property = property;
        this.type = new TypeGenModel(type);
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

    public TypeGenModel getType() {
        return type;
    }
}

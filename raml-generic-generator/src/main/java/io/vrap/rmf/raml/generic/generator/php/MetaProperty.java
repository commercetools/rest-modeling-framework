package io.vrap.rmf.raml.generic.generator.php;

import io.vrap.rmf.raml.model.types.Property;

public class MetaProperty {
    private Property property;

    public MetaProperty(Property property) {
        this.property = property;
    }

    public Property getProperty() {
        return property;
    }

    public MetaType getType()
    {
        return new MetaType(property.getType());
    }

    public String getName()
    {
        return property.getName();
    }

    public String getParamType()
    {
        return new MetaHelper.ParamVisitor(property).doSwitch(property.getType());
    }
}

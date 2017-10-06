package io.vrap.rmf.raml.generic.generator.php;

import com.google.common.base.CaseFormat;
import io.vrap.rmf.raml.model.types.Property;

public class MetaProperty {
    final private static String CONSTANT_PREFIX = "FIELD_";
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

    public String getConstantName() {
        return CONSTANT_PREFIX +  CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE,getName());
    }

    public MetaGetter getGetter()
    {
        return new MetaHelper.PropertyGetterVisitor(this).doSwitch(property.getType());
    }

    public MetaSetter getSetter()
    {
        return new MetaHelper.PropertySetterVisitor(this).doSwitch(property.getType());
    }
}

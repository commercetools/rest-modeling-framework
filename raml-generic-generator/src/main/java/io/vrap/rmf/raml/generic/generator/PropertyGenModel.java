package io.vrap.rmf.raml.generic.generator;

import io.vrap.rmf.raml.model.types.Annotation;
import io.vrap.rmf.raml.model.types.AnyAnnotationType;
import io.vrap.rmf.raml.model.types.ObjectType;
import io.vrap.rmf.raml.model.types.Property;
import io.vrap.rmf.raml.model.util.StringCaseFormat;

import javax.annotation.Nullable;

public class PropertyGenModel {
    final private static String CONSTANT_PREFIX = "FIELD_";
    private Property property;

    public PropertyGenModel(Property property) {
        this.property = property;
    }

    public Property getProperty() {
        return property;
    }

    public TypeGenModel getType()
    {
        return new TypeGenModel(property.getType());
    }

    public String getName()
    {
        return property.getName();
    }

    public String getParamType()
    {
        return GeneratorHelper.getParamVisitor(property).doSwitch(property.getType());
    }

    public String getConstantName() {
        return CONSTANT_PREFIX +  StringCaseFormat.UPPER_UNDERSCORE_CASE.apply(getPatternName());
    }

    public GetterGenModel getGetter()
    {
        return GeneratorHelper.getPropertyGetterVisitor(this).doSwitch(property.getType());
    }

    public SetterGenModel getSetter()
    {
        return GeneratorHelper.getPropertySetterVisitor(this).doSwitch(property.getType());
    }

    public String getPatternName()
    {
        return property.getName().startsWith("/") && property.getName().endsWith("/") ?
                "pattern" + ((ObjectType)property.eContainer()).getProperties().indexOf(property) : getName();
    }

    private AnyAnnotationType getIdentifierAnnotation()
    {
        final ObjectType o = (ObjectType)property.eContainer();
        final TypeGenModel t = new TypeGenModel(o);
        return t.getApi().getAnnotationType("identifier");
    }

    @Nullable
    public Annotation getIdentifier()
    {
        return property.getAnnotation("identifier");
    }
}

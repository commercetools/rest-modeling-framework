package io.vrap.rmf.raml.generic.generator.php;

import com.google.common.base.CaseFormat;
import io.vrap.rmf.raml.model.types.Annotation;
import io.vrap.rmf.raml.model.types.AnyAnnotationType;
import io.vrap.rmf.raml.model.types.ObjectType;
import io.vrap.rmf.raml.model.types.Property;

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
        return new GeneratorHelper.ParamVisitor(property).doSwitch(property.getType());
    }

    public String getConstantName() {
        return CONSTANT_PREFIX +  CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, getPatternName());
    }

    public GetterGenModel getGetter()
    {
        return new GeneratorHelper.PropertyGetterVisitor(this).doSwitch(property.getType());
    }

    public SetterGenModel getSetter()
    {
        return new GeneratorHelper.PropertySetterVisitor(this).doSwitch(property.getType());
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
        final AnyAnnotationType identifierAnnotation = getIdentifierAnnotation();

        if (identifierAnnotation != null) {
            return property.getAnnotation(identifierAnnotation);
        }
        return null;
    }
}

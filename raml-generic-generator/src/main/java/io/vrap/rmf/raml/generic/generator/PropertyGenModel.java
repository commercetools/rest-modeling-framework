package io.vrap.rmf.raml.generic.generator;

import com.google.common.collect.Lists;
import io.vrap.rmf.raml.model.types.*;
import io.vrap.rmf.raml.model.util.StringCaseFormat;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<GetterGenModel> getUnionGetters()
    {
        List<GetterGenModel> getters = Lists.newArrayList();
        if (!(property.getType() instanceof UnionType)) {
            return null;
        }
        UnionType type = (UnionType)property.getType();
        final List<GetterGenModel> collect = type.getOneOf().stream().map(anyType -> GeneratorHelper.getPropertyGetterVisitor(this).doSwitch(anyType)).collect(Collectors.toList());
        return collect;
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

package io.vrap.rmf.raml.generic.generator;

import io.vrap.rmf.raml.model.types.*;

import javax.annotation.Nullable;
import java.util.Optional;

public class MapGenModel extends TypeGenModel {
    public MapGenModel(final AnyType type) {
        super(type);
    }

    public String getName()
    {
        return super.getName();
    }


    @Nullable
    public TypeGenModel getKeyType()
    {
        Annotation anno = getType().getAnnotation("asMap");
        if (anno != null) {
            ObjectInstance i = (ObjectInstance)anno.getValue();
            String keyName = ((StringInstance)i.getValue("key")).getValue();
            return new TypeGenModel(Optional.ofNullable(getApi().getType(keyName)).orElse((AnyType) TypesFactory.eINSTANCE.create(BuiltinType.of(keyName).get().getTypeDeclarationType())));
        }
        return null;
    }

    @Nullable
    public TypeGenModel getValueType()
    {
        Annotation anno = getType().getAnnotation("asMap");
        if (anno != null) {
            ObjectInstance i = (ObjectInstance)anno.getValue();
            String valueName = ((StringInstance)i.getValue("value")).getValue();
            return new TypeGenModel(Optional.ofNullable(getApi().getType(valueName)).orElse((AnyType) TypesFactory.eINSTANCE.create(BuiltinType.of(valueName).get().getTypeDeclarationType())));
        }
        return null;
    }

    public GetterGenModel getMapper()
    {
        return GeneratorHelper.getPropertyMapperVisitor().doSwitch(getValueType().getType());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MapGenModel that = (MapGenModel) o;

        return getName().equals(that.getName());
    }
}

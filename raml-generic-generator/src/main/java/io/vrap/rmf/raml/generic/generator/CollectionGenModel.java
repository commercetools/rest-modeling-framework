package io.vrap.rmf.raml.generic.generator;

import io.vrap.rmf.raml.model.types.AnyType;

import javax.annotation.Nullable;

public class CollectionGenModel extends TypeGenModel {
    public CollectionGenModel(final AnyType type) {
        super(type);
    }

    @Nullable
    public String getName()
    {
        return super.getName() + "Collection";
    }


    @Nullable
    public String getElementName()
    {
        return super.getName();
    }
}

package io.vrap.rmf.raml.generic.generator;

import io.vrap.rmf.raml.model.types.AnyType;

import javax.annotation.Nullable;

public class CollectionGenModel extends TypeGenModel {
    public CollectionGenModel(final AnyType type) {
        super(type);
    }

    public String getName()
    {
        return super.getName() + "Collection";
    }


    @Nullable
    public String getElementName()
    {
        return super.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CollectionGenModel that = (CollectionGenModel) o;

        return getName().equals(that.getName());
    }
}

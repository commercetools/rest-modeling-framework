package io.vrap.rmf.raml.generic.generator.php;

import io.vrap.rmf.raml.model.types.AnyType;

import javax.annotation.Nullable;

public class MetaCollection extends MetaType {
    public MetaCollection(final AnyType type) {
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

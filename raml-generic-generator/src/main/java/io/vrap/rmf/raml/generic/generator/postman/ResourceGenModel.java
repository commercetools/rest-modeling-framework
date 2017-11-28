package io.vrap.rmf.raml.generic.generator.postman;

import io.vrap.rmf.raml.model.resources.Resource;
import io.vrap.rmf.raml.model.util.StringCaseFormat;

import java.util.Optional;

public class ResourceGenModel {
    private final Resource resource;

    public ResourceGenModel(final Resource resource) {
        this.resource = resource;
    }

    public String getName()
    {
        return StringCaseFormat.UPPER_CAMEL_CASE.apply(Optional.ofNullable(resource.getDisplayName()).orElse(resource.getResourcePathName()));
    }
}

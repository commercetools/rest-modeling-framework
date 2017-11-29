package io.vrap.rmf.raml.generic.generator.postman;

import io.vrap.rmf.raml.model.resources.HttpMethod;
import io.vrap.rmf.raml.model.resources.Method;
import io.vrap.rmf.raml.model.resources.Resource;
import io.vrap.rmf.raml.model.util.StringCaseFormat;

import java.util.Optional;

public class ItemGenModel {
    private final Resource resource;
    private final String template;
    private final Method method;

    public ItemGenModel(final Resource resource, final String template, final Method method) {
        this.resource = resource;
        this.template = template;
        this.method = method;
    }

    public String getName()
    {
        return StringCaseFormat.UPPER_CAMEL_CASE.apply(Optional.ofNullable(resource.getDisplayName()).orElse(resource.getResourcePathName()));
    }

    public Resource getResource() {
        return resource;
    }

    public Method getMethod() {
        return method;
    }

    public String getTemplate()
    {
        return template;
    }
}

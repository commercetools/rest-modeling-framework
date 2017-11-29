package io.vrap.rmf.raml.generic.generator.postman;

import com.google.common.collect.Lists;
import io.vrap.rmf.raml.model.resources.HttpMethod;
import io.vrap.rmf.raml.model.resources.Method;
import io.vrap.rmf.raml.model.resources.Resource;
import io.vrap.rmf.raml.model.util.StringCaseFormat;

import java.util.List;
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

    public Resource getResource() {
        return resource;
    }

    public List<ItemGenModel> getItems() {
        List<ItemGenModel> items = Lists.newArrayList();

        if (resource.getMethod(HttpMethod.GET) != null) {
            items.add(new ItemGenModel(resource, "query", resource.getMethod(HttpMethod.GET)));
        }
        if (resource.getMethod(HttpMethod.POST) != null) {
            items.add(new ItemGenModel(resource, "create", resource.getMethod(HttpMethod.POST)));
        }

        return items;
    }
}

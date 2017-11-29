package io.vrap.rmf.raml.generic.generator.postman;

import com.google.common.collect.Lists;
import io.vrap.rmf.raml.model.resources.HttpMethod;
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
        Resource byId = resource.getResources().stream().filter(resource1 -> resource1.getUriParameter("ID") != null).findFirst().orElse(null);
        if (byId != null && byId.getMethod(HttpMethod.GET) != null) {
            items.add(new ItemGenModel(resource, "getById", byId.getMethod(HttpMethod.GET)));
        }
        Resource byKey = resource.getResources().stream().filter(resource1 -> resource1.getUriParameter("key") != null).findFirst().orElse(null);
        if (byKey != null && byKey.getMethod(HttpMethod.GET) != null) {
            items.add(new ItemGenModel(resource, "getByKey", byKey.getMethod(HttpMethod.GET)));
        }

        return items;
    }
}

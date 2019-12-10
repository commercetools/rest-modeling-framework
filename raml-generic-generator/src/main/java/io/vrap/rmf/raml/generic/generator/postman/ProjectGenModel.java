package io.vrap.rmf.raml.generic.generator.postman;

import com.google.common.collect.Lists;
import io.vrap.rmf.raml.model.resources.HttpMethod;
import io.vrap.rmf.raml.model.resources.Resource;

import java.util.List;

public class ProjectGenModel extends ResourceGenModel {
    public ProjectGenModel(final Resource resource) {
        super(resource);
    }

    @Override
    public List<ItemGenModel> getItems() {
        final List<ItemGenModel> items = Lists.newArrayList();
        final Resource resource = getResource();
        if (resource.getMethod(HttpMethod.GET) != null) {
            items.add(new ItemGenModel(resource, "getProject", resource.getMethod(HttpMethod.GET)));
        }
        if (resource.getMethod(HttpMethod.POST) != null) {
            items.add(new ItemGenModel(resource, "updateProject", resource.getMethod(HttpMethod.POST)));
        }
        if (resource.getMethod(HttpMethod.POST) != null) {
            items.addAll(getActionItems(resource.getMethod(HttpMethod.POST), "projectAction"));
        }
        return items;
    }
}

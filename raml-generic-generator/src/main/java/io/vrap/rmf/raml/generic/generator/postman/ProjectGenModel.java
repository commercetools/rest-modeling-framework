package io.vrap.rmf.raml.generic.generator.postman;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import io.vrap.rmf.raml.model.resources.HttpMethod;
import io.vrap.rmf.raml.model.resources.Method;
import io.vrap.rmf.raml.model.resources.Resource;
import io.vrap.rmf.raml.model.responses.Body;
import io.vrap.rmf.raml.model.types.*;
import io.vrap.rmf.raml.model.util.StringCaseFormat;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

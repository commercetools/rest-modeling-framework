package io.vrap.rmf.raml.generic.generator.postman;

import io.vrap.rmf.raml.model.resources.HttpMethod;
import io.vrap.rmf.raml.model.resources.Method;
import io.vrap.rmf.raml.model.resources.Resource;
import io.vrap.rmf.raml.model.util.StringCaseFormat;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public String getDescription() {
        return StringEscapeUtils.escapeJson(method.getDescription());
    }

    public Resource getResource() {
        return resource;
    }

    public String getResourcePathName() {
        final String resourcePathName = resource.getResourcePathName();

        if (resourcePathName.isEmpty()) {
            return resource.getDisplayName().toLowerCase();
        }
        return resourcePathName;
    }

    public Method getMethod() {
        return method;
    }

    public List<ParamGenModel> getQueryParameters() {
        return method.getQueryParameters().stream().map(queryParameter -> new ParamGenModel(resource, queryParameter)).collect(Collectors.toList());
    }

    public String getTemplate()
    {
        return template;
    }
}

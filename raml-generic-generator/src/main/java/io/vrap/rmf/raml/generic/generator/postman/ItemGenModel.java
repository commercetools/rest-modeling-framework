package io.vrap.rmf.raml.generic.generator.postman;

import io.vrap.rmf.raml.generic.generator.Helper;
import io.vrap.rmf.raml.model.resources.Method;
import io.vrap.rmf.raml.model.resources.Resource;
import io.vrap.rmf.raml.model.types.Instance;
import io.vrap.rmf.raml.model.types.StringInstance;
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
        return StringCaseFormat.UPPER_CAMEL_CASE.apply(Optional.ofNullable(resource.getDisplayName()).map(StringInstance::getValue).orElse(resource.getResourcePathName()));
    }

    public String getDescription() {
        String description = Optional.ofNullable(method.getDescription()).map(StringInstance::getValue).orElse(method.getMethod().getName() + " " + getName());
        return StringEscapeUtils.escapeJson(description);
    }

    public Resource getResource() {
        return resource;
    }

    public String getResourcePathName() {
        final String resourcePathName = resource.getResourcePathName();

        if (resourcePathName.isEmpty()) {
            return resource.getDisplayName().getValue().toLowerCase();
        }
        return resourcePathName;
    }

    public Method getMethod() {
        return method;
    }

    public List<ParamGenModel> getQueryParameters() {
        return method.getQueryParameters().stream().map(queryParameter -> new ParamGenModel(resource, queryParameter)).collect(Collectors.toList());
    }

    public String getExample() {
        final Instance s = method.getBodies().get(0).getType().getExamples().get(0).getValue();
        return StringEscapeUtils.escapeJson(Helper.toJson(s));
    }

    public String getTemplate()
    {
        return template;
    }
}

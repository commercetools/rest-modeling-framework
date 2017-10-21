package io.vrap.rmf.raml.generic.generator.php;

import io.vrap.rmf.raml.model.resources.*;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ResourceGenModel {
    static final String REQUEST = "Request";

    final private Resource resource;
    final private Integer index;
    final private List<Resource> allResources;

    public ResourceGenModel(final List<Resource> allResources) {
        this(null, allResources);
    }

    public ResourceGenModel(final Resource resource, final List<Resource> allResources) {
        this.resource = resource;
        this.index = allResources.indexOf(resource);
        this.allResources = allResources;
    }

    @Nullable
    public Resource getResource() {
        return resource;
    }

    @Nullable
    public Integer getIndex() {
        return index;
    }

    public PackageGenModel getPackage()
    {
        return new PackageGenModel(REQUEST);
    }

    public UriTemplate getRelativeUri()
    {
        return resource.getRelativeUri();
    }

    @Nullable
    public List<ResourceGenModel> getResources() {
        return resource.getResources().stream().map(resource1 -> new ResourceGenModel(resource1, allResources)).collect(Collectors.toList());
    }

    public List<ResourceGenModel> getResourcesWithParams() {
        return getResources().stream().filter(resourceGenModel -> resourceGenModel.getResource().getRelativeUri().getParts().size() > 1).collect(Collectors.toList());
    }

    public List<RequestGenModel> getMethods() {
        return resource.getMethods().stream().map(RequestGenModel::new).collect(Collectors.toList());
    }

    public Boolean getHasParams() {
        return resource.getRelativeUri().getParts().size() > 1;
    }

    @Nullable
    public Set<Map.Entry<String, String>> getAllParams() {
        Map<String, String> params = GeneratorHelper.absoluteUri(resource).getParts().stream()
                .filter(uriTemplatePart -> uriTemplatePart instanceof UriTemplateExpression)
                .flatMap(uriTemplatePart -> ((UriTemplateExpression)uriTemplatePart).getVariables().stream())
                .collect(Collectors.toMap(o -> o, o -> "%s"));
        if (params.size() > 0) {
            return params.entrySet();
        }
        return null;
    }
}

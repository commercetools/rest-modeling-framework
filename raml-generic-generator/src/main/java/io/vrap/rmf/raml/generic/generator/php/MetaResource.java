package io.vrap.rmf.raml.generic.generator.php;

import io.vrap.rmf.raml.model.resources.*;
import org.eclipse.emf.ecore.util.EcoreUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MetaResource {
    static final String REQUEST = "Request";

    final private Resource resource;
    final private Integer index;
    final private List<Resource> allResources;

    public MetaResource(final List<Resource> allResources) {
        this(null, allResources);
    }

    public MetaResource(final Resource resource, final List<Resource> allResources) {
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

    public MetaPackage getPackage()
    {
        return new MetaPackage(REQUEST);
    }

    public UriTemplate getRelativeUri()
    {
        return resource.getRelativeUri();
    }

    @Nullable
    public List<MetaResource> getResources() {
        return resource.getResources().stream().map(resource1 -> new MetaResource(resource1, allResources)).collect(Collectors.toList());
    }

    public List<MetaResource> getResourcesWithParams() {
        return getResources().stream().filter(metaResource -> metaResource.getResource().getRelativeUri().getParts().size() > 1).collect(Collectors.toList());
    }

    public List<Method> getMethods() {
        return resource.getMethods();
    }

    public Boolean getHasParams() {
        return resource.getRelativeUri().getParts().size() > 1;
    }
    @Nullable
    public Set<Map.Entry<String, String>> getAllParams() {
        Map<String, String> params = absoluteUri(resource).getParts().stream()
                .filter(uriTemplatePart -> uriTemplatePart instanceof UriTemplateExpression)
                .flatMap(uriTemplatePart -> ((UriTemplateExpression)uriTemplatePart).getVariables().stream())
                .collect(Collectors.toMap(o -> o, o -> "%s"));
        if (params.size() > 0) {
            return params.entrySet();
        }
        return null;
    }

    private UriTemplate absoluteUri(final Resource resource)
    {
        final UriTemplate uri = ResourcesFactory.eINSTANCE.createUriTemplate();
        uri.getParts().addAll(absoluteUriParts(resource));
        return uri;
    }

    private List<UriTemplatePart> absoluteUriParts(final Resource resource)
    {
        if (!(resource.eContainer() instanceof Resource)) {
            return (List<UriTemplatePart>) EcoreUtil.copyAll(resource.getRelativeUri().getParts());
        }
        final List<UriTemplatePart> parts = absoluteUriParts((Resource)resource.eContainer());
        parts.addAll(EcoreUtil.copyAll(resource.getRelativeUri().getParts()));
        return parts;
    }
}

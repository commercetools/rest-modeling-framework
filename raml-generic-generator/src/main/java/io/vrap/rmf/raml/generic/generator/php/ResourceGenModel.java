package io.vrap.rmf.raml.generic.generator.php;

import com.damnhandy.uri.template.Expression;
import com.damnhandy.uri.template.UriTemplate;
import com.damnhandy.uri.template.impl.VarSpec;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import io.vrap.rmf.raml.generic.generator.GeneratorHelper;
import io.vrap.rmf.raml.generic.generator.PackageGenModel;
import io.vrap.rmf.raml.generic.generator.TypeGenModel;
import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.resources.Parameter;
import io.vrap.rmf.raml.model.resources.Resource;
import io.vrap.rmf.raml.model.resources.UriParameter;
import io.vrap.rmf.raml.model.types.Annotation;
import io.vrap.rmf.raml.model.types.BooleanInstance;
import io.vrap.rmf.raml.model.types.StringInstance;
import org.eclipse.emf.ecore.EObject;

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

    public String getMethodName() {
        Annotation annotation = resource.getAnnotation("methodName");
        if (annotation != null) {
            return ((StringInstance)annotation.getValue()).getValue();
        }
        final List<Expression> parts = resource.getRelativeUri().getComponents().stream()
                .filter(uriTemplatePart -> uriTemplatePart instanceof Expression)
                .map(uriTemplatePart -> (Expression)uriTemplatePart)
                .collect(Collectors.toList());
        if (parts.size() > 0) {
            return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, GeneratorHelper.toParamName(resource.getRelativeUri(), "With", "Value"));
        }
        final String uri = resource.getRelativeUri().getTemplate();
        return CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, uri.replaceFirst("/", ""));
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

    public List<ResourceGenModel> getResourcePath() {
        List<ResourceGenModel> path = Lists.newArrayList();
        path.add(this);
        EObject t = resource.eContainer();
        while(t instanceof Resource) {
            path.add(new ResourceGenModel((Resource)t, allResources));
            t = t.eContainer();
        }
        return Lists.reverse(path);
    }

    public List<ResourceGenModel> getResourcesWithParams() {
        return getResources().stream().filter(resourceGenModel -> resourceGenModel.getResource().getRelativeUri().getComponents().size() > 1).collect(Collectors.toList());
    }

    public List<RequestGenModel> getMethods() {
        return resource.getMethods().stream().map(RequestGenModel::new).collect(Collectors.toList());
    }

    public Boolean getHasParams() {
        return resource.getRelativeUri().getComponents().size() > 1;
    }

    public List<UriParameter> getUriParams() {
        return resource.getUriParameters();
    }

    @Nullable
    public Set<Map.Entry<String, String>> getAllParams() {
        Map<String, String> params = GeneratorHelper.absoluteUri(resource).getComponents().stream()
                .filter(uriTemplatePart -> uriTemplatePart instanceof Expression)
                .flatMap(uriTemplatePart -> ((Expression)uriTemplatePart).getVarSpecs().stream())
                .collect(Collectors.toMap(VarSpec::getVariableName,o -> "%s"));
        if (params.size() > 0) {
            return params.entrySet();
        }
        return null;
    }

    @Nullable
    public BuilderGenModel getUpdateBuilder() {
        Annotation annotation = resource.getAnnotation("updateable");
        if (annotation != null) {
            return new BuilderGenModel(this);
        }
        return null;
    }

    @Nullable
    public TypeGenModel getDeleteType() {
        Annotation annotation = resource.getAnnotation("deleteable");
        if (annotation != null) {
            return  new TypeGenModel(getApi().getType(((StringInstance)annotation.getValue()).getValue()));
        }
        return null;
    }

    @Nullable
    public TypeGenModel getCreateType() {
        Annotation annotation = resource.getAnnotation("createable");
        if (annotation != null) {
            return  new TypeGenModel(getApi().getType(((StringInstance)annotation.getValue()).getValue()));
        }
        return null;
    }

    public Api getApi()
    {
        return GeneratorHelper.getParent(resource, Api.class);
    }

    List<Resource> getAllResources() {
        return allResources;
    }
}

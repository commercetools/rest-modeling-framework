package io.vrap.rmf.raml.generic.generator.php;

import com.google.common.base.CaseFormat;
import io.vrap.rmf.raml.model.resources.*;
import io.vrap.rmf.raml.model.responses.BodyType;
import io.vrap.rmf.raml.model.responses.Response;
import io.vrap.rmf.raml.model.types.AnyType;
import io.vrap.rmf.raml.model.types.BuiltinType;
import io.vrap.rmf.raml.model.types.ObjectType;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.ecore.util.EcoreUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MetaRequest {
    static final String REQUEST = "Request";

    final private Method method;

    public MetaRequest(final Method method) {
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

    public MetaPackage getPackage()
    {
        return new MetaPackage(REQUEST);
    }

    @Nullable
    public MetaType getReturnType() {
        Response response = method.getResponses().stream().filter(response1 -> response1.getStatusCode().matches("^2[0-9]{2}$")).findFirst().orElse(null);
        if (response != null) {
            BodyType bodyType = response.getBodies().stream()
                    .filter(bodyType1 -> bodyType1.getContentTypes().size() == 0 || bodyType1.getContentTypes().contains("application/json"))
                    .findFirst().orElse(null);
            if (bodyType != null && !BuiltinType.of(bodyType.getName()).isPresent()) {
                return new MetaType(bodyType.getType());
            }
        }
        return null;
    }

    public String getName() {
        return MetaHelper.toRequestName(getAbsoluteUri(), method);
    }

    public Resource getResource() {
        return (Resource)method.eContainer();
    }

    public UriTemplate getAbsoluteUri()
    {
        return MetaHelper.absoluteUri(getResource());
    }

    @Nullable
    public Set<Map.Entry<String, String>> getAllParams() {
        Map<String, String> params = getAbsoluteUri().getParts().stream()
                .filter(uriTemplatePart -> uriTemplatePart instanceof UriTemplateExpression)
                .flatMap(uriTemplatePart -> ((UriTemplateExpression)uriTemplatePart).getVariables().stream())
                .collect(Collectors.toMap(o -> o, o -> "%s"));
        if (params.size() > 0) {
            return params.entrySet();
        }
        return null;
    }
}

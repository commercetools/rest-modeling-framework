package io.vrap.rmf.raml.generic.generator.php;

import com.damnhandy.uri.template.Expression;
import com.damnhandy.uri.template.UriTemplate;
import com.damnhandy.uri.template.impl.VarSpec;
import com.google.common.collect.Lists;
import com.google.common.net.MediaType;
import io.vrap.rmf.raml.generic.generator.GeneratorHelper;
import io.vrap.rmf.raml.generic.generator.ImportGenModel;
import io.vrap.rmf.raml.generic.generator.PackageGenModel;
import io.vrap.rmf.raml.generic.generator.TypeGenModel;
import io.vrap.rmf.raml.model.resources.Method;
import io.vrap.rmf.raml.model.resources.Resource;
import io.vrap.rmf.raml.model.responses.Body;
import io.vrap.rmf.raml.model.responses.Response;
import io.vrap.rmf.raml.model.types.ArrayType;
import io.vrap.rmf.raml.model.types.BuiltinType;
import io.vrap.rmf.raml.model.types.FileType;
import io.vrap.rmf.raml.model.types.ObjectType;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RequestGenModel {
    static final String REQUEST = "Request";

    final private Method method;

    public RequestGenModel(final Method method) {
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

    public PackageGenModel getPackage()
    {
        return new PackageGenModel(REQUEST);
    }

    @Nullable
    public TypeGenModel getReturnType() {
        Response response = method.getResponses().stream().filter(response1 -> response1.getStatusCode().matches("^2[0-9]{2}$")).findFirst().orElse(null);
        if (response != null) {
            Body bodyType = response.getBodies().stream()
                    .filter(bodyType1 -> bodyType1.getContentTypes().size() == 0 || bodyType1.getContentMediaTypes().contains(MediaType.parse("application/json")))
                    .findFirst().orElse(null);
            if (bodyType != null && !BuiltinType.of(bodyType.getName()).isPresent()) {
                return new TypeGenModel(bodyType.getType());
            }
        }
        return null;
    }

    public String getName() {
        return GeneratorHelper.toRequestName(getAbsoluteUri(), method);
    }

    public Resource getResource() {
        return (Resource)method.eContainer();
    }

    public UriTemplate getAbsoluteUri()
    {
        return GeneratorHelper.absoluteUri(getResource());
    }

    @Nullable
    public Body getFirstBodyType() {
        return method.getBodies().stream().findFirst().orElse(null);
    }

    @Nullable
    public String getBodyType()
    {
        final Body firstBodyType = getFirstBodyType();
        if (firstBodyType != null && firstBodyType.getType() != null) {
            if (firstBodyType.getType() instanceof FileType) {
                return "UploadedFileInterface ";
            }
            if (!BuiltinType.of(firstBodyType.getType().getName()).isPresent()) {
                final String t = GeneratorHelper.getTypeNameVisitor().doSwitch(firstBodyType.getType());
                if (!Lists.newArrayList("mixed", "null", "bool", "string", "float", "int").contains(t)) {
                    return t + " ";
                }
            }
        }
        return null;
    }

    @Nullable
    public ImportGenModel getBodyImport() {
        final Body firstBodyType = getFirstBodyType();

        if (firstBodyType == null) {
            return null;
        }
        if (firstBodyType.getType() instanceof FileType) {
            return new ImportGenModel(null, "Psr\\Http\\Message\\UploadedFileInterface");
        }
        if (firstBodyType.getType() instanceof ObjectType || firstBodyType.getType() instanceof ArrayType) {
            return new TypeGenModel(firstBodyType.getType()).getImport();
        }
        return null;
    }

    @Nullable
    public Set<Map.Entry<String, String>> getAllParams() {
        Map<String, String> params = getAbsoluteUri().getComponents().stream()
                .filter(uriTemplatePart -> uriTemplatePart instanceof Expression)
                .flatMap(uriTemplatePart -> ((Expression)uriTemplatePart).getVarSpecs().stream())
                .collect(Collectors.toMap(VarSpec::getVariableName, o -> "%s"));
        if (params.size() > 0) {
            return params.entrySet();
        }
        return null;
    }
}

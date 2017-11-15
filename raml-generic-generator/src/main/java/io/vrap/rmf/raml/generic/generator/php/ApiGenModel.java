package io.vrap.rmf.raml.generic.generator.php;

import com.damnhandy.uri.template.UriTemplate;
import com.damnhandy.uri.template.impl.VarSpec;
import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.resources.UriParameter;
import io.vrap.rmf.raml.model.security.OAuth20Settings;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ApiGenModel {
    final private Api api;

    public ApiGenModel(Api api) {
        this.api = api;
    }

    public Api getApi() {
        return api;
    }

    public String getBaseUri() {
        return api.getBaseUri().getTemplate();
    }

    public String getAuthUri() {
        return api.getSecuritySchemes().stream()
                .filter(securityScheme -> securityScheme.getSettings() instanceof OAuth20Settings)
                .map(securityScheme -> ((OAuth20Settings)securityScheme.getSettings()).getAccessTokenUri())
                .findFirst().orElse("");
    }

    public String[] getBaseUriVariables() {
        return api.getBaseUri().getVariables();
    }

    public Boolean getHasBaseUriVariables() { return api.getBaseUri().getVariables().length > 0; }

    public List<UriParameter> getBaseUriParameters() {
        return api.getBaseUriParameters();
    }
}

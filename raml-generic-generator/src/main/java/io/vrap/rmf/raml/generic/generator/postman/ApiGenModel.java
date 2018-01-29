package io.vrap.rmf.raml.generic.generator.postman;

import com.google.common.collect.Lists;
import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.security.OAuth20Settings;

import java.util.List;
import java.util.stream.Collectors;

public class ApiGenModel {
    private final Api api;

    public ApiGenModel(final Api api) {
        this.api = api;
    }

    public String getBaseUri()
    {
        return api.getBaseUri().expand();
    }

    public OAuthGenModel getOAuth()
    {
        return new OAuthGenModel(
            api.getSecuritySchemes().stream()
                .filter(securityScheme -> securityScheme.getSettings() instanceof OAuth20Settings)
                .map(securityScheme -> ((OAuth20Settings)securityScheme.getSettings()))
                .findFirst().orElse(null));
    }

    public Api getApi() {
        return api;
    }

    public List<ResourceGenModel> getResources()
    {
        final List<ResourceGenModel> resources = Lists.newArrayList();
        resources.add(new ProjectGenModel(api.getResources().get(0)));
        resources.addAll(api.getResources().get(0).getResources().stream().map(ResourceGenModel::new).collect(Collectors.toList()));

        return resources;
    }
}

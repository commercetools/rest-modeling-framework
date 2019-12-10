package io.vrap.rmf.raml.generic.generator.php;

import io.vrap.rmf.raml.generic.generator.GeneratorHelper;

import java.util.List;

public class ReadmeGenModel {
    final private ApiGenModel api;

    public ReadmeGenModel(ApiGenModel api) {
        this.api = api;
    }

    public ApiGenModel getApi() {
        return api;
    }

    public List<ResourceGenModel> getResources() {
        return GeneratorHelper.flattenResources(api.getApi().getResources());
    }
}

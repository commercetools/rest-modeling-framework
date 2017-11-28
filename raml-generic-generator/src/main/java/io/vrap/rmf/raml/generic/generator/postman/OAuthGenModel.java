package io.vrap.rmf.raml.generic.generator.postman;

import io.vrap.rmf.raml.model.security.OAuth20Settings;

public class OAuthGenModel {

    private final OAuth20Settings settings;

    public OAuthGenModel(final OAuth20Settings settings) {
        this.settings = settings;
    }

    public UriGenModel getUri()
    {
        return new UriGenModel(settings.getAccessTokenUri());
    }
}

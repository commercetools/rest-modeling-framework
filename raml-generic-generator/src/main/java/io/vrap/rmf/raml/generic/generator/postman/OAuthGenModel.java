package io.vrap.rmf.raml.generic.generator.postman;

import com.google.common.collect.Lists;
import io.vrap.rmf.raml.model.security.OAuth20Settings;

import java.net.URI;
import java.util.List;

public class OAuthGenModel {

    private OAuth20Settings settings;

    public OAuthGenModel(OAuth20Settings settings) {
        this.settings = settings;
    }

    public UriGenModel getUri()
    {
        return new UriGenModel(settings.getAccessTokenUri());
    }
}

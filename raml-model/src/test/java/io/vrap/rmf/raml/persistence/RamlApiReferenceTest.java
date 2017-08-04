package io.vrap.rmf.raml.persistence;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;

import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

public class RamlApiReferenceTest implements ResourceFixtures {
    private final static URL apiURL = RamlTck2Test.class.getResource("/commercetools-api-reference-master/update-actions.raml");

    @Test
    public void apiParse()
    {
        final URI fileURI = URI.createURI(apiURL.getFile());
        final Resource resource = fromUri(fileURI);

        assertThat(resource).isInstanceOf(Resource.class);
    }
}

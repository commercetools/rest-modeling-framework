package io.vrap.rmf.raml.persistence;

import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class RamlApiReferenceTest implements ResourceFixtures {

    @Test
    public void loadUpdateActionsExtension() throws IOException {
        final Resource resource = fromClasspath("/commercetools-api-reference-master/update-actions.raml");

        assertThat(resource.getErrors()).isEmpty();
    }
}

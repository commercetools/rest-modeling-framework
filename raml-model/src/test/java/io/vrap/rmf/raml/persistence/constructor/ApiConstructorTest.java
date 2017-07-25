package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.persistence.RamlResourceSet;
import io.vrap.rmf.raml.persistence.ResourceFixtures;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import io.vrap.rmf.raml.persistence.antlr.RAMLParserFixtures;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ApiConstructorTest implements RAMLParserFixtures, ResourceFixtures {

    @Test
    public void simpleApi() throws IOException {
        final RAMLParser parser = parserFromClasspath("/api/simple-api.raml");
        final Resource resource = new RamlResourceSet().createResource(uriFromClasspath("/api/simple-api.raml"));
        final Scope resourceScope = Scope.of(resource);
        final ApiConstructor constructor = new ApiConstructor();
        final Api api = (Api) constructor.construct(parser, resourceScope);

        assertThat(api.getTitle()).isEqualTo("Simple API");
        assertThat(api.getProtocols()).isEqualTo(Arrays.asList("http", "https"));
    }
}

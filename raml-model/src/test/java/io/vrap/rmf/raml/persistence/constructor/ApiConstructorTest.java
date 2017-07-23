package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.persistence.ResourceFixtures;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import io.vrap.rmf.raml.persistence.antlr.RAMLParserFixtures;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ApiConstructorTest implements RAMLParserFixtures, ResourceFixtures {

    @Test
    public void simpleApi() throws IOException {
        final RAMLParser.ApiContext apiContext = parseFromClasspath("/api/simple-api.raml").api();
        final Api api = (Api) ApiConstructor.of(uriFromClasspath("/api/simple-api.raml")).visitApi(apiContext);

        assertThat(api.getTitle()).isEqualTo("Simple API");
        assertThat(api.getProtocols()).isEqualTo(Arrays.asList("http", "https"));
    }
}

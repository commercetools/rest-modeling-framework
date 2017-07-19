package io.vrap.rmf.raml.persistence.antlr;

import io.vrap.rmf.raml.model.modules.Api;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ApiConstructorTest implements RAMLParserFixtures {

    @Test
    public void simpleApi() throws IOException {
        final RAMLParser.ApiContext apiContext = fromClasspath("/api/simple-api.raml").api();
        final Api api = new ApiConstructor().visitApi(apiContext);

        assertThat(api.getTitle()).isEqualTo("Simple API");
        assertThat(api.getProtocols()).isEqualTo(Arrays.asList("http", "https"));
    }
}

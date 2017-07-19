package io.vrap.rmf.raml.persistence.antlr;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static io.vrap.rmf.raml.persistence.antlr.RAMLParser.*;
import static org.assertj.core.api.Assertions.assertThat;

public class RAMLParserTest implements RAMLParserFixtures {

    @Test
    public void simpleApi() throws IOException {
        final ApiContext api = parseFromClasspath("/api/simple-api.raml").api();

        assertThat(api.getChildCount()).isEqualTo(7);
        final List<Simple_api_facetContext> simpleApiFacets = api.simple_api_facet();
        assertThat(simpleApiFacets).hasSize(3);

        assertThat(simpleApiFacets.get(0).getChildCount()).isEqualTo(2);
        assertThat(simpleApiFacets.get(0).facet.getText()).isEqualTo("title");
        assertThat(simpleApiFacets.get(0).value.getText()).isEqualTo("Simple API");

        assertThat(simpleApiFacets.get(1).getChildCount()).isEqualTo(2);
        assertThat(simpleApiFacets.get(1).facet.getText()).isEqualTo("version");
        assertThat(simpleApiFacets.get(1).value.getText()).isEqualTo("v1");

        assertThat(simpleApiFacets.get(2).getChildCount()).isEqualTo(2);
        assertThat(simpleApiFacets.get(2).facet.getText()).isEqualTo("baseUri");
        assertThat(simpleApiFacets.get(2).value.getText()).isEqualTo("https://api.simple.com");

        final List<List_api_facetContext> listApiFacets = api.list_api_facet();
        assertThat(listApiFacets).hasSize(2);

        assertThat(listApiFacets.get(0).facet.getText()).isEqualTo("protocols");
        assertThat(listApiFacets.get(0).values).hasSize(2);
        assertThat(listApiFacets.get(0).values.get(0).getText()).isEqualTo("http");
        assertThat(listApiFacets.get(0).values.get(1).getText()).isEqualTo("https");

        assertThat(listApiFacets.get(1).facet.getText()).isEqualTo("mediaType");
        assertThat(listApiFacets.get(1).values).hasSize(1);
        assertThat(listApiFacets.get(1).values.get(0).getText()).isEqualTo("application/json");
    }

    @Test
    public void localeLibrary() throws IOException {
        final LibraryContext library = parseFromClasspath("/locale.raml").library();
        System.out.println();
    }
}
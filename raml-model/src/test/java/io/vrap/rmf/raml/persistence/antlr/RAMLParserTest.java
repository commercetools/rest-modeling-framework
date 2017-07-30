package io.vrap.rmf.raml.persistence.antlr;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static io.vrap.rmf.raml.persistence.antlr.RAMLParser.*;
import static org.assertj.core.api.Assertions.assertThat;

public class RAMLParserTest implements RAMLParserFixtures {

    @Test
    public void simpleApi() throws IOException {
        final ApiContext api = parserFromClasspath("/api/simple-api.raml").api();

        assertThat(api.getChildCount()).isEqualTo(7);
        final List<AttributeFacetContext> apiFacets = api.attributeFacet();
        assertThat(apiFacets).hasSize(4);

        assertThat(apiFacets.get(0).getChildCount()).isEqualTo(2);
        assertThat(apiFacets.get(0).facet.getText()).isEqualTo("title");
        assertThat(apiFacets.get(0).facetValue().value.getText()).isEqualTo("Simple API");

        assertThat(apiFacets.get(1).getChildCount()).isEqualTo(2);
        assertThat(apiFacets.get(1).facet.getText()).isEqualTo("version");
        assertThat(apiFacets.get(1).facetValue().value.getText()).isEqualTo("v1");

//        assertThat(apiFacets.get(2).getChildCount()).isEqualTo(2);
//        assertThat(apiFacets.get(2).facet.getText()).isEqualTo("baseUri");
//        assertThat(apiFacets.get(2).facetValue().value.getText()).isEqualTo("https://api.simple.com");

        assertThat(apiFacets.get(2).facet.getText()).isEqualTo("protocols");
        assertThat(apiFacets.get(2).facetValue().values).hasSize(2);
        assertThat(apiFacets.get(2).facetValue().values.get(0).getText()).isEqualTo("http");
        assertThat(apiFacets.get(2).facetValue().values.get(1).getText()).isEqualTo("https");

        assertThat(apiFacets.get(3).facet.getText()).isEqualTo("mediaType");
        assertThat(apiFacets.get(3).facetValue().value.getText()).isEqualTo("application/json");
    }

    @Test
    public void localeLibrary() throws IOException {
        final LibraryContext library = parserFromClasspath("/locale.raml").library();
        System.out.println();
    }

    @Test
    public void requiredProperties() throws IOException {
        final LibraryContext library = parserFromClasspath("/properties/required-properties.raml").library();

        final TypeDeclarationFacetContext typeDeclaration = library.typesFacet(0)
                .typeDeclarationFacet(0);
        final PropertiesFacetContext propertiesFacet = typeDeclaration
                .typeDeclarationMap().propertiesFacet(0);
        final String requiredPropertyName = propertiesFacet
                .propertyFacet(2)
                .propertyMap().name.getText();
        assertThat(requiredPropertyName).isEqualTo("required");
    }
}
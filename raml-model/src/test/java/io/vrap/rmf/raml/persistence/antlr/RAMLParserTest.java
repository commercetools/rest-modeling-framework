package io.vrap.rmf.raml.persistence.antlr;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static io.vrap.rmf.raml.persistence.antlr.RAMLParser.*;
import static org.assertj.core.api.Assertions.assertThat;

public class RAMLParserTest implements RAMLParserFixtures {

    @Test
    public void simpleApi() throws IOException {
        final ApiContext api = fromClasspath("/api/simple-api.raml").api();

        final List<Api_facetContext> apiFacets = api.api_facet();
        assertThat(apiFacets).hasSize(5);

        final Api_facetContext titleFacet = apiFacets.get(0);
        assertThat(titleFacet.getChildCount()).isEqualTo(2);
        assertThat(titleFacet.getChild(0).getText()).isEqualTo("title");
        assertThat(titleFacet.getChild(1).getText()).isEqualTo("Simple API");

        final Api_facetContext versionFacet = apiFacets.get(1);
        assertThat(versionFacet.getChildCount()).isEqualTo(2);
        assertThat(versionFacet.getChild(0).getText()).isEqualTo("version");
        assertThat(versionFacet.getChild(1).getText()).isEqualTo("v1");

        final Api_facetContext baseUriFacet = apiFacets.get(2);
        assertThat(baseUriFacet.getChildCount()).isEqualTo(2);
        assertThat(baseUriFacet.getChild(0).getText()).isEqualTo("baseUri");
        assertThat(baseUriFacet.getChild(1).getText()).isEqualTo("https://api.simple.com");

        final Api_facetContext protocolsFacet = apiFacets.get(3);
        assertThat(protocolsFacet.getChildCount()).isEqualTo(2);
        assertThat(protocolsFacet.getChild(0).getText()).isEqualTo("protocols");

        final ParseTree protocolsFacetChild = protocolsFacet.getChild(1);
        assertThat(protocolsFacetChild.getChildCount()).isEqualTo(4);
        assertThat(protocolsFacetChild.getChild(1).getText()).isEqualTo("http");
        assertThat(protocolsFacetChild.getChild(2).getText()).isEqualTo("https");

        final Api_facetContext mediaTypeFacet = apiFacets.get(4);
        assertThat(mediaTypeFacet.getChildCount()).isEqualTo(2);
        assertThat(mediaTypeFacet.getChild(0).getText()).isEqualTo("mediaType");
        assertThat(mediaTypeFacet.getChild(1).getChildCount()).isEqualTo(1);
        assertThat(mediaTypeFacet.getChild(1).getChild(0).getText()).isEqualTo("application/json");
    }

    @Test
    public void localeLibrary() throws IOException {
        final LibraryContext library = fromClasspath("/locale.raml").library();
        System.out.println();
    }
}
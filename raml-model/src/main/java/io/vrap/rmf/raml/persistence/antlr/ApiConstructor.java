package io.vrap.rmf.raml.persistence.antlr;

import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.persistence.constructor.Scope;

public class ApiConstructor extends AbstractConstructor {
    protected ApiConstructor(final Scope scope) {
        super(scope);
    }

    @Override
    public Object visitApi(final RAMLParser.ApiContext ctx) {
        final Api api = FACTORY.createApi();
        for (final RAMLParser.Simple_api_facetContext simpleApiFacet : ctx.simple_api_facet()) {
            constructAttribute(api, simpleApiFacet.facet, simpleApiFacet.value);
        }
        for (final RAMLParser.List_api_facetContext listApiFacet : ctx.list_api_facet()) {
            constructAttribute(api, listApiFacet.facet, listApiFacet.values);
        }
        return api;
    }
}

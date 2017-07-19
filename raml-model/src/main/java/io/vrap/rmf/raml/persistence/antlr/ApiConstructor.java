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
        for (final RAMLParser.ApiFacetContext apiFacet : ctx.apiFacet()) {
            if (apiFacet.facetValue().value != null) {
                constructAttribute(api, apiFacet.facet, apiFacet.facetValue().value);
            } else {
                constructAttribute(api, apiFacet.facet, apiFacet.facetValue().values);
            }
        }
        return api;
    }
}

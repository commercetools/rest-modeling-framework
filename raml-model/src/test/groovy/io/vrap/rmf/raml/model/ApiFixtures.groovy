package io.vrap.rmf.raml.model

import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.persistence.RamlResourceSet
import io.vrap.rmf.raml.persistence.antlr.RAMLParser
import io.vrap.rmf.raml.persistence.constructor.ApiConstructor
import io.vrap.rmf.raml.persistence.constructor.Scope
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.ResourceSet

/**
 * Provides test fixtures for constructing {@link Api} instances.
 */
trait ApiFixtures extends ParserFixtures {

    /**
     * Constructs an {@link Api} instances from the given input.
     *
     * @param input the input
     * @return the api instance
     */
    Api constructApi(String input) {
        URI uri = URI.createURI("api.raml");
        ResourceSet resourceSet = new RamlResourceSet()
        RAMLParser parser = parser(input, uri, resourceSet.getURIConverter())
        def apiConstructor = new ApiConstructor()
        Scope scope = Scope.of(resourceSet.createResource(uri))
        return apiConstructor.construct(parser, scope)
    }
}

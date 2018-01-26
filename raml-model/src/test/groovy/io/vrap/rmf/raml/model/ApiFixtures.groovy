package io.vrap.rmf.raml.model

import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.persistence.RamlResourceSet
import io.vrap.rmf.raml.persistence.antlr.RAMLCustomLexer
import io.vrap.rmf.raml.persistence.antlr.RAMLParser
import io.vrap.rmf.raml.persistence.constructor.ApiConstructor
import io.vrap.rmf.raml.persistence.constructor.Scope
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.TokenStream
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.URIConverter

/**
 * Provides test fixtures for constructing {@link Api} instances.
 */
trait ApiFixtures {
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

    private RAMLParser parser(String input, URI uri, URIConverter uriConverter) {
        def strippedInput = input.stripIndent()
        final RAMLCustomLexer lexer = new RAMLCustomLexer(strippedInput, uri, uriConverter);
        final TokenStream tokenStream = new CommonTokenStream(lexer);
        new RAMLParser(tokenStream)
    }
}

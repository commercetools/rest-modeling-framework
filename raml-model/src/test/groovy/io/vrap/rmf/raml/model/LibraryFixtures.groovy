package io.vrap.rmf.raml.model

import io.vrap.rmf.raml.model.modules.Library
import io.vrap.rmf.raml.persistence.RamlResourceSet
import io.vrap.rmf.raml.persistence.antlr.RAMLParser
import io.vrap.rmf.raml.persistence.constructor.LibraryConstructor
import io.vrap.rmf.raml.persistence.constructor.Scope
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.ResourceSet

/**
 * Provides test fixtures for constructing {@link Library} instances.
 */
trait LibraryFixtures extends ParserFixtures {

    /**
     * Constructs an {@link Library} instances from the given input.
     *
     * @param input the input
     * @return the library instance
     */
    Library constructLibrary(String input) {
        URI uri = URI.createURI("api.raml");
        ResourceSet resourceSet = new RamlResourceSet()
        RAMLParser parser = parser(input, uri, resourceSet.getURIConverter())
        def libraryConstructor = new LibraryConstructor()
        Scope scope = Scope.of(resourceSet.createResource(uri))
        return libraryConstructor.construct(parser, scope)
    }
}
package io.vrap.rmf.raml.model

import io.vrap.rmf.raml.model.types.AnyType
import io.vrap.rmf.raml.model.types.BuiltinType
import io.vrap.rmf.raml.persistence.RamlResourceSet
import io.vrap.rmf.raml.persistence.antlr.RAMLParser
import io.vrap.rmf.raml.persistence.constructor.Scope
import io.vrap.rmf.raml.persistence.constructor.TypeDeclarationFragmentConstructor
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.ResourceSet

import static io.vrap.rmf.raml.model.modules.ModulesPackage.Literals.TYPE_CONTAINER__TYPES

trait TypeFixtures extends ParserFixtures {

    /**
     * Creates an anonymous type from the input.
     * @param input the input
     * @return the type
     */
    AnyType constructType(String input) {
        ResourceSet resourceSet = new RamlResourceSet()
                .getResource(BuiltinType.RESOURCE_URI, true)
                .getResourceSet()
        URI uri = URI.createURI("type.raml");
        RAMLParser parser = parser(input, uri, resourceSet.getURIConverter())
        def constructor = new TypeDeclarationFragmentConstructor(TYPE_CONTAINER__TYPES)
        Scope scope = Scope.of(resourceSet.createResource(uri))
        return constructor.construct(parser, scope)
    }
}
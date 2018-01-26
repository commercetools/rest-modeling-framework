package io.vrap.rmf.raml.model

import io.vrap.rmf.raml.model.types.*
import io.vrap.rmf.raml.persistence.RamlResourceSet
import io.vrap.rmf.raml.persistence.antlr.RAMLCustomLexer
import io.vrap.rmf.raml.persistence.antlr.RAMLParser
import io.vrap.rmf.raml.persistence.constructor.InstanceConstructor
import io.vrap.rmf.raml.persistence.constructor.Scope
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.TokenStream
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.URIConverter

/**
 * Provides test fixtures for constructing {@link Instance} instances.
 */
trait InstanceFixtures {
    /**
     * Creates an {@link Instance} with the given value and whose type corresponds to the given value.
     * @param value the instance value {@link Instance#getValue()}
     * @return the instance
     */
    def createInstance(Object value) {
        Instance instance
        switch (value) {
            case Integer:
                IntegerInstance integerInstance = TypesFactory.eINSTANCE.createIntegerInstance()
                integerInstance.value = value
                instance = integerInstance
                break
            case String:
                StringInstance stringInstance = TypesFactory.eINSTANCE.createStringInstance()
                stringInstance.value = value
                instance = stringInstance
                break
            case Boolean:
                BooleanInstance booleanInstance = TypesFactory.eINSTANCE.createBooleanInstance()
                booleanInstance.value = value
                instance = booleanInstance
                break
            case BigDecimal:
                NumberInstance numberInstance = TypesFactory.eINSTANCE.createNumberInstance()
                numberInstance.value = value
                instance = numberInstance
            default:
                true == false
        }
        instance
    }

    /**
     * Constructs an {@link Instance} instances from the given input.
     *
     * @param input the input
     * @return the instance
     */
    Instance constructInstance(String input) {
        URI uri = URI.createURI("instance.raml");
        ResourceSet resourceSet = new RamlResourceSet()
        RAMLParser parser = parser(input, uri, resourceSet.getURIConverter())
        def constructor = new InstanceConstructor()
        Scope scope = Scope.of(resourceSet.createResource(uri))
        return constructor.construct(parser, scope)
    }

    private RAMLParser parser(String input, URI uri, URIConverter uriConverter) {
        def strippedInput = input.stripIndent()
        final RAMLCustomLexer lexer = new RAMLCustomLexer(strippedInput, uri, uriConverter);
        final TokenStream tokenStream = new CommonTokenStream(lexer);
        new RAMLParser(tokenStream)
    }
}
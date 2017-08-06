package io.vrap.rmf.raml.persistence.constructor

import io.vrap.rmf.raml.model.facets.ArrayInstance
import io.vrap.rmf.raml.model.facets.Instance
import io.vrap.rmf.raml.model.facets.ObjectInstance
import io.vrap.rmf.raml.model.facets.StringInstance
import io.vrap.rmf.raml.persistence.RamlResourceSet
import io.vrap.rmf.raml.persistence.antlr.RAMLCustomLexer
import io.vrap.rmf.raml.persistence.antlr.RAMLParser
import org.antlr.v4.runtime.CommonTokenFactory
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.TokenStream
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.URIConverter
import spock.lang.Shared
import spock.lang.Specification

/**
 * Unit tests for {@link InstanceConstructor}.
 */
class InstanceConstructorTest extends Specification {
    @Shared
    ResourceSet resourceSet = new RamlResourceSet()
    @Shared
    URI uri = URI.createURI("test.raml");

    def "string instance"() {
        when:
        Instance instance = constructInstance(
                '''\
        value
        ''')
        then:
        instance instanceof StringInstance
        StringInstance stringInstance = instance
        stringInstance.value == 'value'
    }

    def "object instance"() {
        when:
        Instance instance = constructInstance(
                '''\
        name: Name
        ''')

        then:
        instance instanceof ObjectInstance
        ObjectInstance objectInstance = instance
        objectInstance.propertyValues.size() == 1
        objectInstance.propertyValues[0].name == 'name'
        objectInstance.propertyValues[0].value instanceof StringInstance
    }

    def "object instance with array"() {
        when:
        Instance instance = constructInstance(
                '''\
        names: 
            - Name1
            - Name2
        ''')

        then:
        instance instanceof ObjectInstance
        ObjectInstance objectInstance = instance
        objectInstance.propertyValues.size() == 1
        objectInstance.propertyValues[0].name == 'names'
        objectInstance.propertyValues[0].value instanceof ArrayInstance
        ArrayInstance arrayInstance = objectInstance.propertyValues[0].value
        arrayInstance.values.size() == 2
        arrayInstance.values[0] instanceof StringInstance
        StringInstance value1 = arrayInstance.values[0]
        value1.value == 'Name1'
        StringInstance value2 = arrayInstance.values[1]
        value2.value == 'Name2'
    }

    def "object instance with object instance"() {
        when:
        Instance instance = constructInstance(
                '''\
        names: 
            firstName: John
            lastName: Doe
            age: 42
        ''')

        then:
        instance instanceof ObjectInstance
        ObjectInstance objectInstance = instance
        objectInstance.propertyValues.size() == 1
        objectInstance.propertyValues[0].value instanceof ObjectInstance
        ObjectInstance nestedObjectInstance = objectInstance.propertyValues[0].value
        nestedObjectInstance.propertyValues.size() == 3
    }

    def "array instance"() {
        when:
        Instance instance = constructInstance(
                '''\
        - 1
        - 2
        ''')

        then:
        instance instanceof ArrayInstance
        ArrayInstance arrayInstance = instance
        arrayInstance.values.size() == 2
        arrayInstance.values[0] instanceof StringInstance
        StringInstance value1 = arrayInstance.values[0]
        value1.value == '1'
        StringInstance value2 = arrayInstance.values[1]
        value2.value == '2'
    }

    Instance constructInstance(String input) {
        RAMLParser parser = parser(input)
        def constructor = new InstanceConstructor()
        Scope scope = Scope.of(resourceSet.createResource(uri))
        return constructor.construct(parser, scope)
    }

    RAMLParser parser(String input) {
        final URIConverter uriConverter = resourceSet.getURIConverter();
        def strippedInput = input.stripIndent()
        final RAMLCustomLexer lexer = new RAMLCustomLexer(strippedInput, uri, uriConverter);
        final TokenStream tokenStream = new CommonTokenStream(lexer);
        lexer.setTokenFactory(CommonTokenFactory.DEFAULT);
        new RAMLParser(tokenStream)
    }
}

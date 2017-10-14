package io.vrap.rmf.raml.persistence.constructor

import io.vrap.rmf.raml.model.facets.StringInstance
import io.vrap.rmf.raml.model.types.*
import io.vrap.rmf.raml.persistence.RamlResourceSet
import io.vrap.rmf.raml.persistence.antlr.RAMLCustomLexer
import io.vrap.rmf.raml.persistence.antlr.RAMLParser
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.TokenStream
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.URIConverter
import spock.lang.Shared
import spock.lang.Specification

import static io.vrap.rmf.raml.model.modules.ModulesPackage.Literals.TYPE_CONTAINER__TYPES

/**
 * Unit tests for {@link TypeDeclarationFragmentConstructor}.
 */
class TypeDeclarationFragmentConstructorTest extends Specification {
    ResourceSet resourceSet
    @Shared
    URI uri = URI.createURI("test.raml");

    def setup() {
        resourceSet = new RamlResourceSet()
                .getResource(BuiltinType.RESOURCE_URI, true)
                .getResourceSet()
    }

    def "simple attributes"() {
        when:
        AnyType anyType = constructType(
                '''\
        displayName: Simple
        ''')
        then:
        anyType.displayName == 'Simple'
    }

    def "type with example"() {
        when:
        AnyType type = constructType(
                '''\
        displayName: Simple
        example: Test
        ''')
        then:
        type.name == null
        StringType stringType = BuiltinType.STRING.getEObject(resourceSet)
        type != stringType
        type.displayName == 'Simple'
        type.examples.size() == 1
        type.examples[0].value instanceof StringInstance
    }

    def "type with property and example"() {
        when:
        AnyType type = constructType(
                '''\
        displayName: WithProperties
        properties:
            name:
                example: Test
        ''')
        then:
        type instanceof ObjectType
        ObjectType objectType = type
        objectType.properties.size() == 1
        objectType.properties[0].name == 'name'
        objectType.properties[0].type instanceof StringType
        StringType namePropertyType = objectType.properties[0].type

        StringType stringType = BuiltinType.STRING.getEObject(resourceSet)
        namePropertyType != stringType
        namePropertyType.examples.size() == 1
        namePropertyType.examples[0].value instanceof StringInstance
    }

    def "type with property and default"() {
        when:
        AnyType type = constructType(
                '''\
        displayName: WithProperties
        properties:
            name:
                default: Test
        ''')
        then:
        type instanceof ObjectType
        ObjectType objectType = type
        objectType.properties.size() == 1
        objectType.properties[0].name == 'name'
        objectType.properties[0].type instanceof StringType
        StringType namePropertyType = objectType.properties[0].type

        StringType stringType = BuiltinType.STRING.getEObject(resourceSet)
        namePropertyType != stringType
        namePropertyType.default instanceof StringInstance
    }

    def "attribute-enum-type.rmal"() {
        when:
        AnyType type = constructType(
                '''\
        displayName: AttributeEnumType
        discriminatorValue: enum
        properties:
            values:
                type: string[]
        ''')
        then:
        type instanceof  ObjectType
        ObjectType objectType = type
        objectType.discriminatorValue == 'enum'
        objectType.properties.size() == 1
        objectType.properties[0].type instanceof ArrayType
        ArrayType arrayType = objectType.properties[0].type
        arrayType.items instanceof StringType
    }

    def "reference-type-id.raml"() {
        when:
        AnyType type = constructType(
                '''\
        displayName: ReferenceTypeId
        type: string
        enum:
        - type
        - zone
        - inventory-entry
        ''')
        then:
        type.enum.size() == 3
    }

    def "array type"() {
        when:
        AnyType type = constructType(
                '''\
        type: array
        items: string
        minItems: 1
        uniqueItems: true
        ''')
        then:
        type instanceof ArrayType
        ArrayType arrayType = type
        arrayType.items instanceof StringType
        arrayType.minItems == 1
        arrayType.uniqueItems == true
    }

    def "array type with items declaration"() {
        when:
        AnyType type = constructType(
                '''\
        type: array
        items:
            type: string
        minItems: 1
        uniqueItems: true
        ''')
        then:
        type instanceof ArrayType
        ArrayType arrayType = type
        arrayType.items instanceof StringType
        arrayType.minItems == 1
        arrayType.uniqueItems == true
    }

    def "array type with inline items declaration"() {
        when:
        AnyType type = constructType(
                '''\
        type: array
        items:
            type: string
            minLength: 12
        minItems: 1
        uniqueItems: true
        ''')
        then:
        type instanceof ArrayType
        ArrayType arrayType = type
        arrayType.minItems == 1
        arrayType.uniqueItems == true
        arrayType.items instanceof StringType
        StringType itemsType = arrayType.items
        itemsType.name == null
        itemsType.minLength == 12
    }

    AnyType constructType(String input) {
        RAMLParser parser = parser(input)
        def constructor = new TypeDeclarationFragmentConstructor(TYPE_CONTAINER__TYPES)
        Scope scope = Scope.of(resourceSet.createResource(uri))
        return constructor.construct(parser, scope)
    }

    RAMLParser parser(String input) {
        final URIConverter uriConverter = resourceSet.getURIConverter();
        def strippedInput = input.stripIndent()
        final RAMLCustomLexer lexer = new RAMLCustomLexer(strippedInput, uri, uriConverter);
        final TokenStream tokenStream = new CommonTokenStream(lexer);
        new RAMLParser(tokenStream)
    }
}

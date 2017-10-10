package io.vrap.rmf.raml.generic.generator.php

import com.google.common.io.Resources
import io.vrap.raml.generic.generator.ResourceFixtures
import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.types.AnyType
import io.vrap.rmf.raml.persistence.RamlResourceSet
import io.vrap.rmf.raml.persistence.antlr.RAMLCustomLexer
import io.vrap.rmf.raml.persistence.antlr.RAMLParser
import io.vrap.rmf.raml.persistence.constructor.ApiConstructor
import io.vrap.rmf.raml.persistence.constructor.Scope
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.TokenStream
import org.apache.commons.io.FileUtils
import org.apache.commons.io.filefilter.TrueFileFilter
import org.assertj.core.util.Files
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.URIConverter
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.nio.charset.StandardCharsets
import static org.apache.commons.lang3.StringUtils.capitalize;

//@Ignore
class PHPGeneratorTest extends Specification implements ResourceFixtures {
    @Shared
    ResourceSet resourceSet = new RamlResourceSet()
    @Shared
    URI uri = URI.createURI("test.raml");

    @Shared
    File resourcePath = new File(Resources.getResource("templates/php/statics/").getFile());
    @Shared
    Collection<File> files = FileUtils.listFiles(resourcePath, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);

    def "generate simple interface"() {
        when:
        Api api = constructApi(
                '''\
        types:
            Person:
                properties:
                    name: string
        ''')
        then:
        String result = generate(TypesGenerator.TYPE_INTERFACE, api.types.get(0));
        result == fileContent("Person.php")
    }

    def "generate serializer"() {
        when:
        Api api = constructApi(
                '''\
        types:
            Document:
                properties:
                    createdAt: datetime
        ''')
        then:
        String result = generate(TypesGenerator.TYPE_MODEL, api.types.get(0));
        result == fileContent("Document.php")
    }


    def "generate extended interface"() {
        when:
        Api api = constructApi(
                '''\
        types:
            Person:
                properties:
                    name: string
            User:
                type: Person
                properties:
                    role: string
        ''')
        then:
        String result = generate(TypesGenerator.TYPE_INTERFACE, api.types.get(1));
        result == fileContent("User.php")
    }

    def "generate simple model"() {
        when:
        Api api = constructApi(
                '''\
        types:
            Person:
                properties:
                    name: string
            User:
                type: Person
                properties:
                    role: string
        ''')
        then:
        String result = generate(TypesGenerator.TYPE_MODEL, api.types.get(0));
        result == fileContent("PersonModel.php")
    }

    def "generate extended model"() {
        when:
        Api api = constructApi(
                '''\
        types:
            Person:
                properties:
                    name: string
            User:
                type: Person
                properties:
                    role: string
        ''')
        then:
        String result = generate(TypesGenerator.TYPE_MODEL, api.types.get(1));
        result == fileContent("UserModel.php")
    }

    def "generate interface getter"() {
        when:
        Api api = constructApi(
                '''\
        types:
            Customer:
                properties:
                    address: Address
            Address:
                properties:
                    street: string
        ''')
        then:
        String result = generate(TypesGenerator.TYPE_INTERFACE, api.types.get(0));
        result == fileContent("Customer.php")

        String address = generate(TypesGenerator.TYPE_INTERFACE, api.types.get(1));
        address == fileContent("Address.php")
    }

    def "generate model getter"() {
        when:
        Api api = constructApi(
                '''\
        types:
            Customer:
                properties:
                    address: Address
            Address:
                properties:
                    street: string
        ''')
        then:
        String result = generate(TypesGenerator.TYPE_MODEL, api.types.get(0));
        result == fileContent("CustomerModel.php")
    }

    def "generate model class map"() {
        when:
        Api api = constructApi(
                '''\
        types:
            Customer:
                properties:
                    address: Address
            Address:
                properties:
                    street: string
        ''')
        then:
        TypesGenerator generator = new TypesGenerator("Test")
        String result = generator.generateMap(api.types);
        result == fileContent("ModelClassMap.php")
    }

    def "generate simple discriminator"() {
        when:
        Api api = constructApi(
                '''\
        types:
            Animal:
                discriminator: kind
                properties:
                    kind: string
            Cat:
                type: Animal
                discriminatorValue: cat
        ''')
        then:
        String baseInterface = generate(TypesGenerator.TYPE_INTERFACE, api.types.get(0));
        baseInterface == fileContent("Animal.php")

        String baseClass = generate(TypesGenerator.TYPE_MODEL, api.types.get(0));
        baseClass == fileContent("AnimalModel.php")

        String kindInterface = generate(TypesGenerator.TYPE_INTERFACE, api.types.get(1));
        kindInterface == fileContent("Cat.php")

        String kindClass = generate(TypesGenerator.TYPE_MODEL, api.types.get(1));
        kindClass == fileContent("CatModel.php")
    }

    def "generate property as type getter"() {
        when:
        Api api = constructApi(
                '''\
        types:
            Attribute:
                properties:
                    name: string
                    value: string | Money | Enum
            Money:
                type: object
                properties:
                    centAmount: string
                    currencyCode: string
            Enum:
                type: object
                properties:
                    key: string
                    label: string
        ''')
        then:
        String attributeInterface = generate(TypesGenerator.TYPE_INTERFACE, api.types.get(0));
        attributeInterface == fileContent("Attribute.php")

        String attributeClass = generate(TypesGenerator.TYPE_MODEL, api.types.get(0));
        attributeClass == fileContent("AttributeModel.php")

        String enumInterface = generate(TypesGenerator.TYPE_INTERFACE, api.types.get(1));
        enumInterface == fileContent("Money.php")

        String enumClass = generate(TypesGenerator.TYPE_MODEL, api.types.get(1));
        enumClass == fileContent("MoneyModel.php")

        String referenceInterface = generate(TypesGenerator.TYPE_INTERFACE, api.types.get(2));
        referenceInterface == fileContent("Enum.php")

        String referenceClass = generate(TypesGenerator.TYPE_MODEL, api.types.get(2));
        referenceClass == fileContent("EnumModel.php")
    }

    def "generate map as type getter"() {
        when:
        Api api = constructApi(
                '''\
        types:
            Container:
                properties:
                    //: string | Money | Enum
        ''')
        then:
        String attributeInterface = generate(TypesGenerator.TYPE_INTERFACE, api.types.get(0));
        attributeInterface == fileContent("Container.php")

        String attributeClass = generate(TypesGenerator.TYPE_MODEL, api.types.get(0));
        attributeClass == fileContent("ContainerModel.php")
    }

    @Unroll
    def "test base class files"(File file) {
        given:
        expect:
        Api api = constructApi(
                '''\
        title: Test
        baseUri: https://api.example.com
        securitySchemes:
            oauth_2_0:
                type: OAuth 2.0
                settings:
                    accessTokenUri: https://auth.example.com/oauth/token
        ''')
        StaticGenerator generator = new StaticGenerator( "Test")
        String result = generator.generateContent(file, api);
        result == fileContent(file.getName().replace(".stg", ""))

        where:
        file << files
    }

    String generate(final String generateType, final AnyType type) {
        TypesGenerator generator = new TypesGenerator("Test")
        return generator.generateType(generator.createVisitor(generateType), type);
    }

    Api constructApi(final String input) {
        RAMLParser parser = parser(input)
        def apiConstructor = new ApiConstructor()
        Scope scope = Scope.of(resourceSet.createResource(uri))
        return (Api)apiConstructor.construct(parser, scope)
    }

    RAMLParser parser(final String input) {
        final URIConverter uriConverter = resourceSet.getURIConverter();
        def strippedInput = input.stripIndent()
        final RAMLCustomLexer lexer = new RAMLCustomLexer(strippedInput, uri, uriConverter);
        final TokenStream tokenStream = new CommonTokenStream(lexer);
        return new RAMLParser(tokenStream)
    }

    String fileContent(final String fileName) {
        return Files.contentOf(new File(Resources.getResource(fileName).toURI()), StandardCharsets.UTF_8)
    }
}

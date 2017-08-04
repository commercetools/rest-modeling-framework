package io.vrap.rmf.raml.persistence.constructor

import io.vrap.rmf.raml.model.modules.Library
import io.vrap.rmf.raml.model.security.OAuth20Settings
import io.vrap.rmf.raml.model.types.AnnotationTarget
import io.vrap.rmf.raml.model.types.ObjectType
import io.vrap.rmf.raml.model.types.StringAnnotationType
import io.vrap.rmf.raml.model.types.StringType
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
 * Unit tests for {@link ApiConstructor}
 */
class LibraryConstructorTest extends Specification {
    @Shared
    ResourceSet resourceSet = new RamlResourceSet()
    @Shared
    URI uri = URI.createURI("test.raml");

    def "library"() {
        when:
        Library library = constructLibrary(
                '''\
        usage: Test

        types:
            StringType:
                type: string
                minLength: 10
            EmptyType:
            WithProperties:
                type: object
                properties:
                    name?: string
                    super:
                        type: SuperType
            SuperType: object
            SubType:
                type: SuperType
            Enum:
                type: string
                enum:
                    - v1
                    - v2
        ''')

        then:
        library.usage == 'Test'
        library.types.size() == 6
        library.types[0].name == 'StringType'
        library.types[0].type instanceof StringType
        StringType stringType = library.types[0]
        stringType.minLength == 10

        library.types[1].name == 'EmptyType'
        library.types[1] instanceof StringType

        library.types[3].name == 'SuperType'
        library.types[3].type instanceof ObjectType
        ObjectType superType = library.types[3]

        library.types[2].name == 'WithProperties'
        library.types[2].type instanceof ObjectType
        ObjectType objectType = library.types[2]
        objectType.properties.size() == 2
        objectType.getProperty('super') != null
        objectType.getProperty('super').type == superType

        library.types[4].name == 'SubType'
        library.types[4].type instanceof ObjectType
        library.types[4].type == superType

        library.types[5].name == 'Enum'
        library.types[5].type instanceof StringType
        library.types[5].enum == [ 'v1', 'v2' ]
    }

    def "library with annotation type"() {
        when:
        Library library = constructLibrary(
                '''\
        usage: Defines an annotation.

        annotationTypes:
            package:
                type: string
                allowedTargets: Library
        ''')
        then:
        library.usage == 'Defines an annotation.'

        library.annotationTypes.size() == 1
        library.annotationTypes[0] instanceof StringAnnotationType
        library.annotationTypes[0].name == 'package'
        library.annotationTypes[0].allowedTargets == [ AnnotationTarget.LIBRARY ]
    }

    def "library with inline type declaration"() {
        when:
        Library library = constructLibrary(
                '''\
        usage: Type with a property that has a inline type declaration

        types:
            WithProperties:
                type: object
                properties:
                    name:
                        type: string
                        minLength: 10
        ''')

        then:
        library.types.size() == 1

        library.types[0] instanceof ObjectType
        ObjectType objectType = library.types[0]
        objectType.getProperty('name') != null
        objectType.getProperty('name').type instanceof StringType
        StringType inlineStringType = objectType.getProperty('name').type
        inlineStringType.name == null
        inlineStringType.minLength == 10
    }
    def "security scheme"() {
        when:
        Library library = constructLibrary(
                '''\
        securitySchemes:
            oauth_2_0:
                type: OAuth 2.0
                settings:
                    accessTokenUri: https://api.example.com/1/oauth2/token
                    authorizationGrants: [ authorization_code, implicit ]
                    authorizationUri: https://www.example.com/1/oauth2/authorize
        ''')

        then:
        library.securitySchemes.size() == 1
        library.securitySchemes[0].name == 'oauth_2_0'
        library.securitySchemes[0].type.literal == 'OAuth 2.0'
        library.securitySchemes[0].settings instanceof OAuth20Settings
        OAuth20Settings oauth20Settings = library.securitySchemes[0].settings
        oauth20Settings.accessTokenUri == 'https://api.example.com/1/oauth2/token'
        oauth20Settings.authorizationGrants == ['authorization_code', 'implicit']
        oauth20Settings.authorizationUri == 'https://www.example.com/1/oauth2/authorize'
    }

    Library constructLibrary(String input) {
        RAMLParser parser = parser(input)
        def libraryConstructor = new LibraryConstructor()
        Scope scope = Scope.of(resourceSet.createResource(uri))
        return libraryConstructor.construct(parser, scope)
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

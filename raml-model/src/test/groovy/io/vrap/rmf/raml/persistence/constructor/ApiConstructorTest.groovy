package io.vrap.rmf.raml.persistence.constructor

import io.vrap.rmf.raml.model.facets.ArrayInstance
import io.vrap.rmf.raml.model.facets.ObjectInstance
import io.vrap.rmf.raml.model.facets.Protocol
import io.vrap.rmf.raml.model.facets.StringInstance
import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.resources.HttpMethod
import io.vrap.rmf.raml.model.resources.Resource
import io.vrap.rmf.raml.model.resources.UriTemplateExpression
import io.vrap.rmf.raml.model.resources.UriTemplateLiteral
import io.vrap.rmf.raml.model.responses.BodyType
import io.vrap.rmf.raml.model.security.OAuth20Settings
import io.vrap.rmf.raml.model.types.IntegerType
import io.vrap.rmf.raml.model.types.ObjectType
import io.vrap.rmf.raml.model.types.StringType
import io.vrap.rmf.raml.model.types.TypeTemplate
import io.vrap.rmf.raml.persistence.RamlResourceSet
import io.vrap.rmf.raml.persistence.antlr.RAMLCustomLexer
import io.vrap.rmf.raml.persistence.antlr.RAMLParser
import io.vrap.rmf.raml.persistence.antlr.RamlTokenFactory
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
class ApiConstructorTest extends Specification {
    @Shared
    ResourceSet resourceSet = new RamlResourceSet()
    @Shared
    URI uri = URI.createURI("test.raml");

    def "resource type with type template and transformations"() {
        when:
        Api api = constructApi(
                '''\
        resourceTypes:
            collection:
                post?:
                    body:
                        application/json:
                            type: Post<<resourcePathName | !singularize | !uppercamelcase>>
        ''')
        then:
        api.resourceTypes.size() == 1
        api.resourceTypes[0].methods.size() == 1
        api.resourceTypes[0].methods[0].bodies.size() == 1
        api.resourceTypes[0].methods[0].bodies[0].type instanceof TypeTemplate
        api.resourceTypes[0].methods[0].bodies[0].inlineTypes == [api.resourceTypes[0].methods[0].bodies[0].type]
    }

    def "type with example"() {
        when:
        Api api = constructApi(
                '''\
        types:
            Person:
                properties:
                    name:
                example:
                    name: Mr. X
            Name:
                type: string
                example: John Doe        
        ''')
        then:
        api.types.size() == 2
        api.types[0].example != null
        api.types[0].example.name == null
        api.types[0].example.value instanceof ObjectInstance
        ObjectInstance example = api.types[0].example.value
        example.propertyValues.size() == 1
        example.propertyValues[0].name == 'name'
        example.propertyValues[0].value instanceof StringInstance
        StringInstance stringInstance = example.propertyValues[0].value
        stringInstance.value == 'Mr. X'


        api.types[1].example != null
        api.types[1].example.value instanceof StringInstance
        StringInstance stringInstance1 = api.types[1].example.value
        stringInstance1.value == 'John Doe'
    }

    def "api with documentation"() {
        when:
        Api api = constructApi(
                '''\
        documentation:
            - title: Home
              content: This is the API homepage.
            - title: Legal
              content: It's legal to use this API.
        ''')
        then:
        api.documentation.size() == 2
        api.documentation[0].title == 'Home'
        api.documentation[0].content == 'This is the API homepage.'
        api.documentation[1].title == 'Legal'
        api.documentation[1].content == 'It\'s legal to use this API.'
    }

    def "type with examples"() {
        when:
        Api api = constructApi(
                '''\
        types:
            Person:
                properties:
                    name:
                examples:
                    Mr. X:
                        name: Mr. X
                    Mrs. Y:
                        name: Mrs. Y
        ''')
        then:
        api.types.size() == 1
        api.types[0].examples.size() == 2
        api.types[0].examples[0].name == 'Mr. X'
        api.types[0].examples[0].value instanceof ObjectInstance
        ObjectInstance exampleInstance1 = api.types[0].examples[0].value
        exampleInstance1.propertyValues.size() == 1
        exampleInstance1.propertyValues[0].name == 'name'
        exampleInstance1.propertyValues[0].value instanceof StringInstance
        StringInstance exampleValue1 = exampleInstance1.propertyValues[0].value
        exampleValue1.value == 'Mr. X'

        api.types[0].examples[1].name == 'Mrs. Y'
        api.types[0].examples[1].value instanceof ObjectInstance
        ObjectInstance exampleInstance2 = api.types[0].examples[1].value
        exampleInstance2.propertyValues.size() == 1
        exampleInstance2.propertyValues[0].name == 'name'
        exampleInstance2.propertyValues[0].value instanceof StringInstance
        StringInstance exampleValue2 = exampleInstance2.propertyValues[0].value
        exampleValue2.value == 'Mrs. Y'
    }

    def "types with default"() {
        when:
        Api api = constructApi(
                '''\
        types:
            Person:
                properties:
                    name:
                default:
                    name: Mr. X
            Name:
                type: string
                default: John Doe
        ''')
        then:
        api.types.size() == 2

        api.types[0].default instanceof ObjectInstance
        ObjectInstance default_ = api.types[0].default
        default_.propertyValues.size() == 1
        default_.propertyValues[0].name == 'name'
        default_.propertyValues[0].value instanceof StringInstance
        StringInstance stringInstance = default_.propertyValues[0].value
        stringInstance.value == 'Mr. X'

        api.types[1].default instanceof StringInstance
        StringInstance stringInstance1 = api.types[1].default
        stringInstance1.value == 'John Doe'
    }

    def "secured by null"() {
        when:
        Api api = constructApi(
                '''\
        securedBy: [ null ]
        /user:
            get:
                securedBy: [ null ]
        ''')
        then:
        api.securedBy.size() == 1
        api.securedBy[0].scheme.name == 'null'
        api.resources.size() == 1
        api.resources[0].methods.size() == 1
        api.resources[0].methods[0].securedBy.size() == 1
        api.resources[0].methods[0].securedBy[0].scheme.name == 'null'
    }

    def "resource with type"() {
        when:
        Api api = constructApi(
                '''\
        resourceTypes:
            collection:
                get?:
        
        /users:
            type: 
                collection:
                    arg1: value1
        ''')
        then:
        api.resourceTypes.size() == 1
        api.resourceTypes[0].name == 'collection'
        api.resourceTypes[0].methods.size() == 1
        api.resourceTypes[0].methods[0].method == HttpMethod.GET
        api.resources.size() == 1
        api.resources[0].type.type == api.resourceTypes[0]
        api.resources[0].type.arguments.size() == 1
        api.resources[0].type.arguments[0].name == 'arg1'
        api.resources[0].type.arguments[0].value instanceof StringInstance
    }

    def "security scheme"() {
        when:
        Api api = constructApi(
                '''\
        securitySchemes:
            oauth_2_0:
                description: OAuth 2.0 security scheme
                type: OAuth 2.0
                settings:
                    accessTokenUri: https://api.example.com/1/oauth2/token
                    authorizationGrants: [ authorization_code, implicit ]
                    authorizationUri: https://www.example.com/1/oauth2/authorize
                    scopes:
                        - manage
                        - update
                describedBy:
                    headers:
                        Authorization:
                            description: |
                                On successful completion of an authorization flow,
                                a client will be given an `access_token`, which they need to include in requests
                                to authorized service endpoints via the HTTP `Authorization` header like this:

                                Authorization: Bearer {access_token}
                            type: string
                    responses:
                        401:
                            description: Unauthorized
        securedBy: [ oauth_2_0: { scopes: [ manage ] } ]
        ''')

        then:
        api.securitySchemes.size() == 1
        api.securitySchemes[0].name == 'oauth_2_0'
        api.securitySchemes[0].description == 'OAuth 2.0 security scheme'
        api.securitySchemes[0].type.literal == 'OAuth 2.0'

        api.securitySchemes[0].describedBy != null
        api.securitySchemes[0].describedBy.headers.size() == 1
        api.securitySchemes[0].describedBy.headers[0].name == 'Authorization'
        api.securitySchemes[0].describedBy.headers[0].type instanceof StringType
        api.securitySchemes[0].describedBy.headers[0].type.name == null
        api.securitySchemes[0].describedBy.responses.size() == 1
        api.securitySchemes[0].describedBy.responses[0].statusCode == '401'
        api.securitySchemes[0].describedBy.responses[0].description == 'Unauthorized'

        api.securitySchemes[0].settings instanceof OAuth20Settings
        OAuth20Settings oauth20Settings = api.securitySchemes[0].settings
        oauth20Settings.accessTokenUri == 'https://api.example.com/1/oauth2/token'
        oauth20Settings.authorizationGrants == ['authorization_code', 'implicit']
        oauth20Settings.authorizationUri == 'https://www.example.com/1/oauth2/authorize'
        oauth20Settings.scopes == [ 'manage', 'update' ]

        api.securedBy.size() == 1
        api.securedBy[0].scheme == api.securitySchemes[0]
        api.securedBy[0].parameters.propertyValues.size() == 1
        api.securedBy[0].parameters.propertyValues[0].name == 'scopes'
        api.securedBy[0].parameters.propertyValues[0].value instanceof ArrayInstance
        ArrayInstance scopes = api.securedBy[0].parameters.propertyValues[0].value
        scopes.values.size() == 1
        scopes.values[0] instanceof StringInstance
        StringInstance scope = scopes.values[0]
        scope.value == 'manage'
    }

    def "traits"() {
        when:
        Api api = constructApi(
                '''\
        traits:
            secured:
                usage: Apply this to any method that needs to be secured
                description: Some requests require authentication.
                displayName: Secured Method
                headers:
                    access_token:
                        type: string
                queryParameters:
                    clientId:
                        type: string
                responses:
                    409:
                        description: Conflict
                        body:
                            application/json:
                                type: string
        /user:
            get:
                is: [ secured ]
        ''')
        then:
        api.traits.size() == 1
        api.traits[0].name == 'secured'
        api.traits[0].usage == 'Apply this to any method that needs to be secured'
        api.traits[0].description == 'Some requests require authentication.'
        api.traits[0].displayName == 'Secured Method'
        api.traits[0].headers.size() == 1
        api.traits[0].headers[0].name == 'access_token'
        api.traits[0].queryParameters.size() == 1
        api.traits[0].queryParameters[0].name == 'clientId'
        api.traits[0].responses.size() == 1
        api.traits[0].responses[0].statusCode == '409'
        api.traits[0].responses[0].description == 'Conflict'
        api.traits[0].responses[0].bodies.size() == 1
        api.traits[0].responses[0].bodies[0].contentTypes == [ 'application/json' ]

        api.resources.size() == 1
        api.resources[0].methods.size() == 1
        api.resources[0].methods[0].is.size() == 1
        api.resources[0].methods[0].is[0].trait == api.traits[0]
    }

    def "trait application with argument"() {
        when:
        Api api = constructApi(
                '''\
        traits:
            secured:
                headers:
                    <<access_token>>:
        /user:
            get:
                is:
                    - secured:
                        access_token: secret
        ''')
        then:
        api.traits.size() == 1
        api.traits[0].name == 'secured'
        api.traits[0].headers.size() == 1
        api.traits[0].headers[0].name == '<<access_token>>'

        api.resources.size() == 1
        api.resources[0].methods.size() == 1
        api.resources[0].methods[0].is.size() == 1
        api.resources[0].methods[0].is[0].trait == api.traits[0]
        api.resources[0].methods[0].is[0].arguments.size() == 1
        api.resources[0].methods[0].is[0].arguments[0].name == 'access_token'
        api.resources[0].methods[0].is[0].arguments[0].value instanceof StringInstance
        StringInstance stringInstance = api.resources[0].methods[0].is[0].arguments[0].value
        stringInstance.value == 'secret'
    }

    def "simple api attributes"() {
        when:
        Api api = constructApi(
                '''\
        title: Simple API
        version: v1
        protocols:
            - http
            - https
        mediaType: application/json
        ''')

        then:
        api.title == 'Simple API'
        api.protocols == [ Protocol.HTTP, Protocol.HTTPS ]
        api.mediaType == ['application/json']
    }

    def "base uri and base uri parameters"() {
        when:
        Api api = constructApi(
                '''\
        baseUri: https://api.simple.com/{version}/api/{userId}
        baseUriParameters:
            userId: integer
        ''')

        then:
        api.baseUri.parts.size() == 4
        api.baseUri.parts[0] instanceof UriTemplateLiteral
        UriTemplateLiteral uriTemplateLiteral = api.baseUri.parts[0]
        uriTemplateLiteral.literal == 'https://api.simple.com/'

        api.baseUri.parts[1] instanceof UriTemplateExpression
        UriTemplateExpression versionTemplateExpression = api.baseUri.parts[1]
        versionTemplateExpression.variables.size() == 1
        versionTemplateExpression.variables[0] == 'version'

        api.baseUri.parts[2] instanceof UriTemplateLiteral

        api.baseUri.parts[3] instanceof UriTemplateExpression
        UriTemplateExpression userIdTemplateExpression = api.baseUri.parts[3]
        userIdTemplateExpression.variables.size() == 1
        userIdTemplateExpression.variables[0] == 'userId'

        api.baseUriParameters.size() == 1
        api.baseUriParameters[0].name == 'userId'
        api.baseUriParameters[0].type.name == 'integer'
    }

    def "simple resource attributes"() {
        when:
        Api api = constructApi(
                '''\
        securitySchemes:
            basic_auth:
                type: Basic Authentication
        /user:
            description: User endpoint
            displayName: Users
            securedBy: [ basic_auth ]
        ''')

        then:
        api.resources.size() == 1
        api.securitySchemes.size() == 1
        Resource resource = api.resources[0]
        resource.relativeUri.parts.size() == 1
        resource.relativeUri.parts[0] instanceof UriTemplateLiteral
        resource.description == 'User endpoint'
        resource.displayName == 'Users'
        resource.securedBy.size() == 1
        resource.securedBy[0].scheme == api.securitySchemes[0]
    }

    def "simple uri parameters"() {
        when:
        Api api = constructApi(
                '''\
        /user/{userId}:
            uriParameters:
                userId: integer
        ''')

        then:
        api.resources.size() == 1
        Resource resource = api.resources[0]
        resource.relativeUri.parts.size() == 2
        resource.relativeUri.parts[0] instanceof UriTemplateLiteral
        resource.relativeUri.parts[1] instanceof UriTemplateExpression
        resource.uriParameters.size() == 1
        resource.uriParameters[0].name == 'userId'
        resource.uriParameters[0].type.name == 'integer'
    }

    def "simple sub resources"() {
        when:
        Api api = constructApi(
                '''\
        securitySchemes:
            basic_auth:
                type: Basic Authentication
        /user:
            /{userId}:
                uriParameters:
                    userId: integer
                securedBy: [ basic_auth ]
        ''')

        then:
        api.resources.size() == 1
        api.securitySchemes.size() == 1
        Resource resource = api.resources[0]
        resource.relativeUri.parts.size() == 1
        resource.relativeUri.parts[0] instanceof UriTemplateLiteral
        resource.resources.size() == 1
        Resource subResource = resource.resources[0]
        subResource.relativeUri.parts.size() == 2
        subResource.relativeUri.parts[0] instanceof UriTemplateLiteral
        subResource.relativeUri.parts[1] instanceof UriTemplateExpression
        subResource.uriParameters.size() == 1
        subResource.uriParameters[0].name == 'userId'
        subResource.uriParameters[0].type.name == 'integer'
        subResource.securedBy.size() == 1
        subResource.securedBy[0].scheme == api.securitySchemes[0]
    }

    def "simple.raml (TCK)"() {
        when:
        Api api = constructApi(
                '''\
        title: hola
        /top:
            get:
                description: "get something"
            post:
            /child:
        ''')
        then:
        api.resources.size() == 1
        api.resources[0].methods.size() == 2
        api.resources[0].methods[0].method == HttpMethod.GET
        api.resources[0].methods[0].description == 'get something'
        api.resources[0].methods[1].method == HttpMethod.POST
        api.resources[0].resources.size() == 1
        api.resources[0].resources[0].relativeUri.parts.size() == 1
        api.resources[0].resources[0].relativeUri.parts[0] instanceof UriTemplateLiteral
        UriTemplateLiteral uriTemplateLiteral = api.resources[0].resources[0].relativeUri.parts[0]
        uriTemplateLiteral.literal == '/child'
    }

    def "resource and method with responses"() {
        when:
        Api api = constructApi(
                '''\
        /user:
            get:
                responses: 
                    200:
                        body: 
                            application/json:
                                type: object
                    401:
                        body: 
                            application/json:
                                type: string
        ''')
        then:
        api.resources.size() == 1
        api.resources[0].methods.size() == 1
        api.resources[0].methods[0].method == HttpMethod.GET

        api.resources[0].methods[0].responses.size() == 2

        api.resources[0].methods[0].responses[0].statusCode == '200'
        api.resources[0].methods[0].responses[0].bodies.size() == 1
        api.resources[0].methods[0].responses[0].bodies[0].contentTypes == [ 'application/json' ]
        api.resources[0].methods[0].responses[0].bodies[0].type instanceof ObjectType
        api.resources[0].methods[0].responses[0].bodies[0].type.name == 'object'

        api.resources[0].methods[0].responses[1].statusCode == '401'
        api.resources[0].methods[0].responses[1].bodies.size() == 1
        api.resources[0].methods[0].responses[1].bodies[0].contentTypes == [ 'application/json' ]
        api.resources[0].methods[0].responses[1].bodies[0].type instanceof StringType
        api.resources[0].methods[0].responses[1].bodies[0].type.name == 'string'
    }

    def "resource with method"() {
        when:
        Api api = constructApi(
                '''\
        securitySchemes:
            basic_auth:
                type: Basic Authentication
        /user:
            get:
                displayName: Get users
                description: This method retrieves all users.
                protocols: [https]
                securedBy: [ basic_auth ]
        ''')

        then:
        api.resources.size() == 1
        api.securitySchemes.size() == 1
        Resource resource = api.resources[0]
        resource.methods.size() == 1
        resource.methods[0].method == HttpMethod.GET
        resource.methods[0].displayName == 'Get users'
        resource.methods[0].description == 'This method retrieves all users.'
        resource.methods[0].protocols == [ Protocol.HTTPS ]
        resource.methods[0].securedBy.size() == 1
        resource.methods[0].securedBy[0].scheme == api.securitySchemes[0]
    }

    def "resource with method and headers"() {
        when:
        Api api = constructApi(
                '''\
        /user:
            get:
                headers:
                    X-Correlation-Id: string
        ''')

        then:
        api.resources.size() == 1
        Resource resource = api.resources[0]
        resource.methods.size() == 1
        resource.methods[0].method == HttpMethod.GET
        resource.methods[0].headers.size() == 1
        resource.methods[0].headers[0].name == 'X-Correlation-Id'
        resource.methods[0].headers[0].type.name == 'string'
    }

    def "resource with method and query parameters"() {
        when:
        Api api = constructApi(
                '''\
        /user:
            get:
                queryParameters:
                    userId: string
                    expand?: boolean
        ''')

        then:
        api.resources.size() == 1
        Resource resource = api.resources[0]
        resource.methods.size() == 1
        resource.methods[0].method == HttpMethod.GET
        resource.methods[0].queryParameters.size() == 2
        resource.methods[0].queryParameters[0].name == 'userId'
        resource.methods[0].queryParameters[0].type.name == 'string'
        resource.methods[0].queryParameters[1].name == 'expand'
        resource.methods[0].queryParameters[1].required == false
        resource.methods[0].queryParameters[1].type.name == 'boolean'
    }

    def "resource with methods, body and content type"() {
        when:
        Api api = constructApi(
                '''\
        /name:
            get:
                body:
                    application/json:
                        type: string
            post:
                body:
                    application/xml:
                        type: integer
                        maximum: 32
        ''')
        then:
        api.resources.size() == 1
        api.resources[0].methods.size() == 2
        api.resources[0].methods[0].bodies.size() == 1
        api.resources[0].methods[0].bodies[0].contentTypes == [ 'application/json' ]
        api.resources[0].methods[0].bodies[0].type instanceof StringType
        api.resources[0].methods[1].bodies.size() == 1
        api.resources[0].methods[1].bodies[0].contentTypes == [ 'application/xml' ]
        api.resources[0].methods[1].bodies[0].name == null
        api.resources[0].methods[1].bodies[0].type instanceof IntegerType
        IntegerType integerType = api.resources[0].methods[1].bodies[0].type
        integerType.maximum == 32
    }

    def "resource with method and bodies"() {
        when:
        Api api = constructApi(
                '''\
        /name:
            get:
                body:
                    application/json:
                        type: string
                    application/xml:
                        type: integer
                        maximum: 32
        ''')
        then:
        api.resources.size() == 1
        api.resources[0].methods.size() == 1
        api.resources[0].methods[0].bodies.size() == 2
        api.resources[0].methods[0].bodies[0].contentTypes == [ 'application/json' ]
        api.resources[0].methods[0].bodies[0].type instanceof StringType
        api.resources[0].methods[0].bodies[1].contentTypes == [ 'application/xml' ]
        api.resources[0].methods[0].bodies[1].name == null
        api.resources[0].methods[0].bodies[1].type instanceof IntegerType
        IntegerType integerType = api.resources[0].methods[0].bodies[1].type
        integerType.maximum == 32
    }

    def "resource with methods and body"() {
        when:
        Api api = constructApi(
                '''\
        /name:
            get:
                body:
                    type: string
            post:
                body:
                    type: integer
                    maximum: 32
        ''')
        then:
        api.resources.size() == 1
        api.resources[0].methods.size() == 2
        api.resources[0].methods[0].bodies.size() == 1
        api.resources[0].methods[0].bodies[0].contentTypes.size() == 0
        api.resources[0].methods[0].bodies[0].type instanceof StringType
        api.resources[0].methods[1].bodies.size() == 1
        api.resources[0].methods[1].bodies[0].contentTypes.size() == 0
        api.resources[0].methods[1].bodies[0].name == null
        api.resources[0].methods[1].bodies[0].type instanceof IntegerType
        IntegerType integerType = api.resources[0].methods[1].bodies[0].type
        integerType.maximum == 32
    }

    def "resource with method, body and properties"() {
        when:
        Api api = constructApi(
                '''\
        /user:
            get:
                body:
                    properties:
                        name?: string
        ''')
        then:
        api.resources.size() == 1
        api.resources[0].methods.size() == 1
        api.resources[0].methods[0].bodies.size() == 1
        BodyType bodyType = api.resources[0].methods[0].bodies[0]
        bodyType.type instanceof ObjectType
        ObjectType objectType = bodyType.type
        objectType.properties.size() == 1
        objectType.getProperty('name') != null
        objectType.getProperty('name').type instanceof StringType
    }

    def "resource with method, body, content type and properties"() {
        when:
        Api api = constructApi(
                '''\
        /user:
            get:
                body:
                    application/json:
                        properties:
                            name?: string
        ''')
        then:
        api.resources.size() == 1
        api.resources[0].methods.size() == 1
        api.resources[0].methods[0].bodies.size() == 1
        BodyType bodyType = api.resources[0].methods[0].bodies[0]
        bodyType.contentTypes == [ 'application/json' ]
        bodyType.type instanceof ObjectType
        ObjectType objectType = bodyType.type
        objectType.properties.size() == 1
        objectType.getProperty('name') != null
        objectType.getProperty('name').type instanceof StringType
    }

    def "default-type-string.raml (TCK)"() {
        when:
        Api api = constructApi(
                '''\
        #%RAML 1.0
        title: Default type string

        types:
            Person:
                properties:
                    name: # no type or schema necessary since the default type is `string`''')
        then:
        api.types.size() == 1
        api.types[0] instanceof ObjectType
        ObjectType objectType = api.types[0]
        objectType.properties.size() == 1
        objectType.properties[0].type instanceof StringType
    }

    Api constructApi(String input) {
        RAMLParser parser = parser(input)
        def apiConstructor = new ApiConstructor()
        Scope scope = Scope.of(resourceSet.createResource(uri))
        return apiConstructor.construct(parser, scope)
    }

    RAMLParser parser(String input) {
        final URIConverter uriConverter = resourceSet.getURIConverter();
        def strippedInput = input.stripIndent()
        final RAMLCustomLexer lexer = new RAMLCustomLexer(strippedInput, uri, uriConverter);
        final TokenStream tokenStream = new CommonTokenStream(lexer);
        new RAMLParser(tokenStream)
    }
}

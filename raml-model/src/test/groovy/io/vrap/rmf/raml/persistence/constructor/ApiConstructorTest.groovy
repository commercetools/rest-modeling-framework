package io.vrap.rmf.raml.persistence.constructor

import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.modules.OAuth20Settings
import io.vrap.rmf.raml.model.resources.HttpMethod
import io.vrap.rmf.raml.model.resources.Resource
import io.vrap.rmf.raml.model.resources.UriTemplateExpression
import io.vrap.rmf.raml.model.resources.UriTemplateLiteral
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
class ApiConstructorTest extends Specification {
    @Shared
    ResourceSet resourceSet = new RamlResourceSet()
    @Shared
    URI uri = URI.createURI("test.raml");

    def "security scheme"() {
        when:
        Api api = constructApi(
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
        api.securitySchemes.size() == 1
        api.securitySchemes[0].name == 'oauth_2_0'
        api.securitySchemes[0].type.literal == 'OAuth 2.0'
        api.securitySchemes[0].settings instanceof OAuth20Settings
        OAuth20Settings oauth20Settings = api.securitySchemes[0].settings
        oauth20Settings.accessTokenUri == 'https://api.example.com/1/oauth2/token'
        oauth20Settings.authorizationGrants == ['authorization_code', 'implicit']
        oauth20Settings.authorizationUri == 'https://www.example.com/1/oauth2/authorize'
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
        api.protocols == ['http', 'https']
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
        /user:
            description: User endpoint
            displayName: Users
        ''')

        then:
        api.resources.size() == 1
        Resource resource = api.resources[0]
        resource.relativeUri.parts.size() == 1
        resource.relativeUri.parts[0] instanceof UriTemplateLiteral
        resource.description == 'User endpoint'
        resource.displayName == 'Users'
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
        /user:
            /{userId}:
                uriParameters:
                    userId: integer
        ''')

        then:
        api.resources.size() == 1
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
    }

    def "resource with method"() {
        when:
        Api api = constructApi(
                '''\
        /user:
            get:
                displayName: Get users
                description: This method retrieves all users.
                protocols: [https]
        ''')

        then:
        api.resources.size() == 1
        Resource resource = api.resources[0]
        resource.methods.size() == 1
        resource.methods[0].method == HttpMethod.GET
        resource.methods[0].displayName == 'Get users'
        resource.methods[0].description == 'This method retrieves all users.'
        resource.methods[0].protocols == ['https']
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
        lexer.setTokenFactory(CommonTokenFactory.DEFAULT);
        new RAMLParser(tokenStream)
    }
}

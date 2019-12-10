package io.vrap.rmf.raml.regression

import io.vrap.rmf.raml.model.RamlModelResult
import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.resources.HttpMethod
import io.vrap.rmf.raml.model.types.AnyType
import io.vrap.rmf.raml.model.types.NumberType
import io.vrap.rmf.raml.model.types.ObjectInstance
import io.vrap.rmf.raml.model.types.StringType
import spock.lang.Ignore

class ResourceTest extends RegressionTest {
    def "test-response-type"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Test
        types:
            Cart: object
            Category: object
        baseUri: http://example.com/
        mediaType: application/json
        resourceTypes:
            base:
                get:
                    responses:
                        200:
                            body:
                                application/json:
                                    type: <<resourceType>>
        /{test}:
            get:
            /carts:
                type:
                    base:
                        resourceType: Cart
            /categories:
                type:
                    base:
                        resourceType: Category
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
        ramlModelResult.rootObject.resources[0].resources[0].relativeUri.template == "/carts"
        ramlModelResult.rootObject.resources[0].resources[0].getMethod(HttpMethod.GET).responses[0].getBody("application/json").type.name == "Cart"
        ramlModelResult.rootObject.resources[0].resources[1].relativeUri.template == "/categories"
        ramlModelResult.rootObject.resources[0].resources[1].getMethod(HttpMethod.GET).responses[0].getBody("application/json").type.name == "Category"
    }

    def "test-base-resource"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Test
        baseUri: http://example.com
        mediaType: application/json
        /:
            get:
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
        ramlModelResult.rootObject.resources.size() == 1
        ramlModelResult.rootObject.resources[0].relativeUri.template == '/'
    }

    def "test-base-resource-fullUri"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Test
        baseUri: http://example.com
        mediaType: application/json
        /:
            get:
            /categories:
                get:
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
        ramlModelResult.rootObject.resources.size() == 1
        ramlModelResult.rootObject.resources[0].fullUri.template == '/'
        ramlModelResult.rootObject.resources[0].resources[0].fullUri.template == '/categories'
    }

    def "test-base-resource-fullUri-collapse"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Test
        mediaType: application/json
        baseUri: //api.test.com//common//
        /:
            /users/:
                /groups//:
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
        ramlModelResult.rootObject.resources.size() == 1
        ramlModelResult.rootObject.resources[0].fullUri.template == '/'
        ramlModelResult.rootObject.resources[0].resources[0].fullUri.template == '/users/'
        ramlModelResult.rootObject.resources[0].resources[0].resources[0].fullUri.template == '/users//groups//'
    }

    def "test-base-resource-fullUri-collapse-multiple-roots"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Test
        mediaType: application/json
        baseUri: //api.test.com//common//
        /:
        /users/:
            /groups//:
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
        ramlModelResult.rootObject.resources.size() == 2
        ramlModelResult.rootObject.resources[0].fullUri.template == '/'
        ramlModelResult.rootObject.resources[1].fullUri.template == '/users/'
        ramlModelResult.rootObject.resources[1].resources[0].fullUri.template == '/users//groups//'
    }

    def "test-default-uri-parameter"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Test
        baseUri: http://example.com
        mediaType: application/json
        /api-{projectKey}-{number}:
            uriParameters:
                number:
                    type: number
            get:
        /import-{projectKey}:
            get:
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
        ramlModelResult.rootObject.resources.size() == 2
        ramlModelResult.rootObject.resources.find { it.relativeUri.template.startsWith("/api") }.relativeUriParameters.size() == 2
        ramlModelResult.rootObject.resources.find { it.relativeUri.template.startsWith("/api") }.relativeUriParameters.find { it.name == "number" }.type instanceof NumberType
        ramlModelResult.rootObject.resources.find { it.relativeUri.template.startsWith("/api") }.relativeUriParameters.find { it.name == "projectKey" }.type instanceof StringType

        ramlModelResult.rootObject.resources.find { it.relativeUri.template.startsWith("/import") }.relativeUriParameters.size() == 1
        ramlModelResult.rootObject.resources.find { it.relativeUri.template.startsWith("/import") }.relativeUriParameters.find { it.name == "projectKey" }.type instanceof StringType
    }

    def "test-resource-list-sub"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Test
        baseUri: http://example.com
        mediaType: application/json
        /:
            get:
            /categories:
                get:
            /cart:
                get:
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
    }

    def "test-resource-list"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Test
        baseUri: http://example.com
        mediaType: application/json
        /categories:
            get:
        /cart:
            get:
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
    }

    def "test-examples"() {
        when:
        writeFile(
                "example.json",
                '''{ "foo": "bar" }''')
        RamlModelResult<Api> ramlModelResult = constructApi(
                Arrays.asList("example.json"),
                '''\
        #%RAML 1.0
        title: Test
        types:
            Cart: object
        baseUri: http://example.com/
        mediaType: application/json
        /{test}:
            get:
            /carts:
                post:
                    body:
                        application/json:
                            type: Cart
                            example: !include example.json
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
        ramlModelResult.rootObject.resources[0].resources[0].relativeUri.template == "/carts"
        AnyType cart = ramlModelResult.rootObject.resources[0].resources[0].getMethod(HttpMethod.POST).getBody("application/json").type
        cart.name == "Cart"
        ObjectInstance example = cart.examples[0].value
        example.getValue("foo").value == "bar"
    }

    @Ignore
    def "test-resource-examples"() {
        when:
        writeFile(
                "example.json",
                '''{ "foo": "bar" }''')
        RamlModelResult<Api> ramlModelResult = constructApi(
                Arrays.asList("example.json"),
                '''\
        #%RAML 1.0
        title: Test
        types:
            Cart: object
            Category: object
        baseUri: http://example.com/
        mediaType: application/json
        resourceTypes:
            base:
                post:
                    body:
                        application/json:
                            example: <<resourceExample>>
                            type: <<resourceType>>
        /{test}:
            get:
            /carts:
                type:
                    base:
                        resourceType: Cart
                        resourceExample: !include example.json
            /categories:
                type:
                    base:
                        resourceType: Category
                        resourceExample: !include example.json
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
        ramlModelResult.rootObject.resources[0].resources[0].relativeUri.template == "/carts"
        AnyType cart = ramlModelResult.rootObject.resources[0].resources[0].getMethod(HttpMethod.POST).getBody("application/json").type
        cart.name == "Cart"

        ObjectInstance cartExample = cart.examples[0].value
        cartExample.getValue("foo").value == "bar"


        ramlModelResult.rootObject.resources[0].resources[1].relativeUri.template == "/categories"
        AnyType category = ramlModelResult.rootObject.resources[0].resources[0].getMethod(HttpMethod.POST).getBody("application/json").type
        category.name == "Category"

        ObjectInstance categoryExample = category.examples[0].value
        categoryExample.getValue("foo").value == "bar"
    }
}

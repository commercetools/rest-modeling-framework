package io.vrap.rmf.raml.regression

import io.vrap.rmf.raml.model.RamlModelResult
import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.resources.HttpMethod

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
}

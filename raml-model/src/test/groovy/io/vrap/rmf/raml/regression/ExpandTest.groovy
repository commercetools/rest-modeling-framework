package io.vrap.rmf.raml.regression

import io.vrap.rmf.raml.model.RamlModelResult
import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.resources.HttpMethod
import io.vrap.rmf.raml.model.resources.Method
import io.vrap.rmf.raml.model.resources.Resource
import io.vrap.rmf.raml.model.responses.Response
import io.vrap.rmf.raml.model.types.AnyType

class ExpandTest extends RegressionTest {

    def "expand-traits-with-resource-type" () {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Some API
        traits:
            versioned:
                queryParameters:
                    version:
                        type: number
                        minimum: 10
                        maximum: 20
        resourceTypes:
            base:
                delete:
                    is:
                        - versioned
        /category:
            type: base
            delete:
                queryParameters:
                    version:
                        type: number 
                        minimum: 3
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
        ramlModelResult.rootObject.resources.get(0).methods.get(0).queryParameters.size() == 1
        ramlModelResult.rootObject.resources.get(0).methods.get(0).queryParameters[0].type.minimum == 3
        ramlModelResult.rootObject.resources.get(0).methods.get(0).queryParameters[0].type.maximum == 20
    }

    def "expand-traits-without-resource-type" () {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Some API
        traits:
            versioned:
                queryParameters:
                    version:
                        type: number
        /category:
            delete:
                is:
                    - versioned
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
        ramlModelResult.rootObject.resources.get(0).methods.get(0).queryParameters.size() == 1
    }

    def "traits using predefined params" () {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Some API
        traits:
            autodoc:
               description: The <<methodName>> operation on <<resourcePathName>> is available at <<resourcePath>>
        /category:
            delete:
                is:
                   - autodoc
            /kind:
                get:
                    is:
                       - autodoc
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
        with(ramlModelResult.rootObject) {
            resources.size() == 1
            with(resources[0]) {
                methods.size() == 1
                methods[0].description.value == 'The delete operation on category is available at /category'
                resources.size == 1
                resources[0].methods[0].description.value == 'The get operation on kind is available at /category/kind'
            }
        }
    }

    def "trait with usage" () {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Some API
        traits:
            info:
                usage: This trait adds an info query parameter
                queryParameters:
                    info:
        /category:
            get:
                is: [info]
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
        ramlModelResult.rootObject.resources.size() == 1
        with(ramlModelResult.rootObject.resources[0]) {
            methods.size() == 1
            with(methods[0]) {
                queryParameters.size() == 1
            }
        }
    }

    def "expand-traits-with-resource-type-in-resource" () {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Some API
        traits:
            versioned:
                queryParameters:
                    version:
                        type: number
            stageable:
                queryParameters:
                    staged:
                        type: boolean
        resourceTypes:
            base:
                delete:
                    is:
                        - stageable
        /category:
            type: base
            delete:
                is:
                    - versioned
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
        ramlModelResult.rootObject.resources.get(0).methods.get(0).queryParameters.size() == 2
    }

    def "resource type inheritance" () {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Some API
        resourceTypes:
            base-methods:
                get:
            full-methods:
                type: base-methods
                delete:
        /category:
            type: full-methods
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
        ramlModelResult.rootObject.resources.size() == 1
        with(ramlModelResult.rootObject.resources[0]) {
            methods.size() == 2
            methods[0].method == HttpMethod.DELETE
            methods[1].method == HttpMethod.GET
        }
    }

    def "resource type templates" () {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Some API
        traits:
          errorable:
        resourceTypes:
            baseResource:
                get:
                    is:
                        - errorable
                    displayName: Get <<resourceType>> by <<testName>>
                    description: Get <<resourceType>> by <<testName | !lowercamelcase>>
        /category:
            type:
                baseResource:
                   resourceType: Bar
                   testName: FOO
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
        ramlModelResult.rootObject.resources.size() == 1
        with(ramlModelResult.rootObject.resources[0]) {
            methods.size() == 1
            methods[0].displayName.value == "Get Bar by FOO"
            methods[0].description.value == "Get Bar by foo"
        }
    }

    def "resource type with usage" () {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Some API
        resourceTypes:
            base-methods:
                usage: Use this to add get and delete methods.
                get:
                delete:
        /category:
            type: base-methods
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
        ramlModelResult.rootObject.resources.size() == 1
        with(ramlModelResult.rootObject.resources[0]) {
            methods.size() == 2
        }
    }

    def "ResourceType merge issue"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        ---
        title: Test
        baseUri: https://example.com
        version: v1
        mediaType: application/json
        types:
            PagedQueryResponse: object
        resourceTypes:
            domainTemplate:
                get:
                    responses:
                        200:
                            body:
                                type: <<templateType>>
        /categories:
            type: { domainTemplate: { templateType: PagedQueryResponse } }
            displayName: Categories
            description: Categories are used to organize products in a hierarchical structure.
            get:
                description: Query Categories
                responses:
                200:
                    body:
                        application/json:
                            example: {}        
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
        Api api = ramlModelResult.rootObject
        AnyType pagedQueryResponse = api.getType('PagedQueryResponse')
        pagedQueryResponse != null
        api.resources.size() == 1
        Resource categoriesResource = api.resources[0]
        Method getMethod = categoriesResource.getMethod(HttpMethod.GET)
        getMethod != null
        getMethod.responses.size() == 1
        Response response = getMethod.responses[0]
        response.bodies.size() == 1
        response.bodies[0].type == pagedQueryResponse
    }
}

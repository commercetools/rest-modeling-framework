package io.vrap.rmf.raml.regression

import io.vrap.rmf.raml.model.RamlModelResult
import io.vrap.rmf.raml.model.modules.Api

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
}

package io.vrap.rmf.raml.regression

import io.vrap.rmf.raml.model.RamlModelResult
import io.vrap.rmf.raml.model.modules.Api
import spock.lang.Ignore

class ExpandTest extends RegressionTest {

    @Ignore
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

    @Ignore
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

    @Ignore
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

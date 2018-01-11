package io.vrap.rmf.raml.regression

import io.vrap.rmf.raml.model.RamlModelResult
import io.vrap.rmf.raml.model.modules.Api

class MultipleOfTest extends RegressionTest {
    def "number-multipleof"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Some API
        types:
            MyCustomType:
                type: number
                multipleOf: 3
                example: 6
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
    }
}

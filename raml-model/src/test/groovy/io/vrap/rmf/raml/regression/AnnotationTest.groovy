package io.vrap.rmf.raml.regression

import io.vrap.rmf.raml.model.RamlModelResult
import io.vrap.rmf.raml.model.modules.Api

class AnnotationTest extends RegressionTest {
    def "validation-of-annotation-value"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''
        #%RAML 1.0 Library
        usage: Defines annotations.
        
        annotationTypes:
            ReferenceType:
                type: string
                enum:
                - category
        types:
            MyType:
                (ReferenceType): product
                ''')
        then:
        ramlModelResult.validationResults.size() == 1
        ramlModelResult.validationResults[0].message == "Value 'product' is not defined in enum facet '[category]'"
    }
}

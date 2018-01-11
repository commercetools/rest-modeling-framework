package io.vrap.rmf.raml.regression

import io.vrap.rmf.raml.model.RamlModelResult
import io.vrap.rmf.raml.model.modules.Api

class DiscriminatorTest extends RegressionTest {
    def "nested-discriminator-example-validation"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Some API
        types:
            UpdateAction:
                type: object
                discriminator: action
                properties:
                    action:
                        type: string
            CartDiscountChangeTargetAction:
                type: UpdateAction
                discriminatorValue: changeTarget
                example:
                    {
                        "action": "changeTarget",
                        "target": {
                            "type": "lineItems",
                            "predicate": "sku = \\"mySKU\\""
                        }
                    }
                properties:
                    target:
                        type: CartDiscountTarget
            CartDiscountTarget:
                type: object
                discriminator: type
                properties:
                    type:
                        type: string
            CartDiscountLineItemsTarget:
                type: CartDiscountTarget
                discriminatorValue: lineItems
                properties:
                    predicate:
                        type: string
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
    }
}

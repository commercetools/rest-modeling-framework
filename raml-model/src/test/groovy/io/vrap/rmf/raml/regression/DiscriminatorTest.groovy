package io.vrap.rmf.raml.regression

import io.vrap.rmf.raml.model.RamlModelResult
import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.resources.HttpMethod
import io.vrap.rmf.raml.model.types.AnyType
import io.vrap.rmf.raml.model.types.Instance
import io.vrap.rmf.raml.model.types.StringInstance
import io.vrap.rmf.raml.model.types.TypesFactory
import io.vrap.rmf.raml.model.util.InstanceHelper
import io.vrap.rmf.raml.validation.InstanceValidator

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
        AnyType type = ramlModelResult.rootObject.getType('CartDiscountChangeTargetAction')

        Instance instance = InstanceHelper.parseJson('''\
                    {
                        "action": "changeTarget",
                        "target": {
                            "type": "lineItems",
                            "predicate": "sku = \\"mySKU\\""
                        }
                    }''')
        new InstanceValidator().validate(instance, type).size() == 0
    }
}

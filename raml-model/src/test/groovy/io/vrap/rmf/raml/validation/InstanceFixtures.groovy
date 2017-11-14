package io.vrap.rmf.raml.validation

import io.vrap.rmf.raml.model.values.*

trait InstanceFixtures {
    def createInstance(Object value) {
        Instance instance
        switch (value) {
            case Integer:
                IntegerInstance integerInstance = ValuesFactory.eINSTANCE.createIntegerInstance()
                integerInstance.value = value
                instance = integerInstance
                break
            case String:
                StringInstance stringInstance = ValuesFactory.eINSTANCE.createStringInstance()
                stringInstance.value = value
                instance = stringInstance
                break
            case Boolean:
                BooleanInstance booleanInstance = ValuesFactory.eINSTANCE.createBooleanInstance()
                booleanInstance.value = value
                instance = booleanInstance
                break
            case BigDecimal:
                NumberInstance numberInstance = ValuesFactory.eINSTANCE.createNumberInstance()
                numberInstance.value = value
                instance = numberInstance
            default:
                true == false
        }
        instance
    }
}
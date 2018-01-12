package io.vrap.rmf.raml.validation

import io.vrap.rmf.raml.model.types.*

trait InstanceFixtures {
    def createInstance(Object value) {
        Instance instance
        switch (value) {
            case Integer:
                IntegerInstance integerInstance = TypesFactory.eINSTANCE.createIntegerInstance()
                integerInstance.value = value
                instance = integerInstance
                break
            case String:
                StringInstance stringInstance = TypesFactory.eINSTANCE.createStringInstance()
                stringInstance.value = value
                instance = stringInstance
                break
            case Boolean:
                BooleanInstance booleanInstance = TypesFactory.eINSTANCE.createBooleanInstance()
                booleanInstance.value = value
                instance = booleanInstance
                break
            case BigDecimal:
                NumberInstance numberInstance = TypesFactory.eINSTANCE.createNumberInstance()
                numberInstance.value = value
                instance = numberInstance
            default:
                true == false
        }
        instance
    }
}
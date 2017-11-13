package io.vrap.rmf.raml.validation

import io.vrap.rmf.raml.model.facets.*

trait InstanceFixtures {
    def createInstance(Object value) {
        Instance instance
        switch (value) {
            case Integer:
                IntegerInstance integerInstance = FacetsFactory.eINSTANCE.createIntegerInstance()
                integerInstance.value = value
                instance = integerInstance
                break
            case String:
                StringInstance stringInstance = FacetsFactory.eINSTANCE.createStringInstance()
                stringInstance.value = value
                instance = stringInstance
                break
            case Boolean:
                BooleanInstance booleanInstance = FacetsFactory.eINSTANCE.createBooleanInstance()
                booleanInstance.value = value
                instance = booleanInstance
                break
            case BigDecimal:
                NumberInstance numberInstance = FacetsFactory.eINSTANCE.createNumberInstance()
                numberInstance.value = value
                instance = numberInstance
            default:
                true == false
        }
        instance
    }
}
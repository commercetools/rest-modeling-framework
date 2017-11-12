package io.vrap.rmf.raml.validation

import io.vrap.rmf.raml.model.facets.FacetsFactory
import io.vrap.rmf.raml.model.facets.Instance
import io.vrap.rmf.raml.model.facets.IntegerInstance
import io.vrap.rmf.raml.model.facets.StringInstance

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
            default:
                true == false
        }
        instance
    }
}
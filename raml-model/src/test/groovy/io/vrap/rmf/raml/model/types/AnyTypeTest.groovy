package io.vrap.rmf.raml.model.types

import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.modules.ModulesFactory
import spock.lang.Specification

/**
 * Unit tests for {@link AnyType}.
 */
class AnyTypeTest extends Specification {

    def "isInlineType() works correctly"() {
        when:
        Api api = ModulesFactory.eINSTANCE.createApi()
        AnyType inlineType = TypesFactory.eINSTANCE.createAnyType()
        api.inlineTypes.add(inlineType)
        AnyType type = TypesFactory.eINSTANCE.createAnyType()
        api.types.add(type)
        then:
        inlineType.isInlineType() == true
        type.isInlineType() == false
    }
}

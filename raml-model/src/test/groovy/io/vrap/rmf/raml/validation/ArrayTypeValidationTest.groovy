package io.vrap.rmf.raml.validation

import io.vrap.rmf.raml.model.types.ArrayType
import io.vrap.rmf.raml.model.types.TypesFactory
import io.vrap.rmf.raml.model.util.BaseValidatorTest

class ArrayTypeValidationTest extends BaseValidatorTest {
    ArrayType arrayType

    def "setup"() {
        arrayType = TypesFactory.eINSTANCE.createArrayType()
    }

    def "validate"() {
        when:
        arrayType.minItems = minItems
        arrayType.maxItems = maxItems
        then:
        validate(arrayType) == valid
        where:
        minItems | maxItems || valid
        2        | 1        || false
        null     | null     || true
        0        | 0        || true
        -1       | null     || false
        null     | -1       || false
        1        | 2        || true
    }
}

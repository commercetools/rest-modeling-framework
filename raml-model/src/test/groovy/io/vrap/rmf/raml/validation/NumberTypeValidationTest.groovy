package io.vrap.rmf.raml.validation

import io.vrap.rmf.raml.model.types.NumberType
import io.vrap.rmf.raml.model.types.TypesFactory
import io.vrap.rmf.raml.model.util.BaseValidatorTest

class NumberTypeValidationTest extends BaseValidatorTest {
    NumberType numberType

    def "setup"() {
        numberType = TypesFactory.eINSTANCE.createNumberType()
    }

    def "validateSizeRestriction"() {
        when:
        numberType.minimum = minimum
        numberType.maximum = maximum
        then:
        validate(numberType) == valid
        where:
        minimum        | maximum        || valid
        BigDecimal.TEN | BigDecimal.ONE || false
        null           | null           || true
        BigDecimal.ONE | BigDecimal.TEN || true
    }

    def "validateMultipleOf"() {
        when:
        numberType.multipleOf = multipleOf
        then:
        validate(numberType) == valid
        where:
        multipleOf || valid
        null       || true
        -1         || false
        0          || false
        1          || true
    }
}
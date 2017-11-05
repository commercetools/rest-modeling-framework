package io.vrap.rmf.raml.validation

import io.vrap.rmf.raml.model.types.IntegerType
import io.vrap.rmf.raml.model.types.TypesFactory

class IntegerTypeValidationTest extends BaseValidatorTest {
    IntegerType integerType

    def "setup"() {
        integerType = TypesFactory.eINSTANCE.createIntegerType()
    }

    def "validateSizeRestriction"() {
        when:
        integerType.minimum = minimum
        integerType.maximum = maximum
        then:
        validate(integerType) == valid
        where:
        minimum | maximum || valid
        2       | 1       || false
        null    | null    || true
        1       | 2       || true
    }
    
    def "validateMultipleOf"() {
        when:
        integerType.multipleOf = multipleOf
        then:
        validate(integerType) == valid
        where:
        multipleOf || valid
        null       || true
        -1         || false
        0          || false
        1          || true
    }
}
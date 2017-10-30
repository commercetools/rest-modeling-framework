package io.vrap.rmf.raml.validation

import io.vrap.rmf.raml.model.types.ArrayType
import io.vrap.rmf.raml.model.types.StringType
import io.vrap.rmf.raml.model.types.TypesFactory
import io.vrap.rmf.raml.model.util.BaseValidatorTest

class StringTypeValidationTest extends BaseValidatorTest {
    StringType stringType

    def "setup"() {
        stringType = TypesFactory.eINSTANCE.createStringType()
    }

    def "validateLengthRestriction"() {
        when:
        stringType.minLength = minLength
        stringType.maxLength = maxLength
        then:
        validate(stringType) == valid
        where:
        minLength | maxLength || valid
        2         | 1         || false
        null      | null      || true
        0         | 0         || true
        -1        | null      || false
        null      | -1        || false
        1         | 2         || true
    }
}

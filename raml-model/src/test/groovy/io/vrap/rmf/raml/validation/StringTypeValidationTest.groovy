package io.vrap.rmf.raml.validation

import io.vrap.rmf.raml.model.types.StringType
import io.vrap.rmf.raml.model.types.TypesFactory

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

    def "validateEnumFacet"() {
        when:
        enumValues.each {
            stringType.enum.add(createInstance(it))
        }
        then:
        validate(stringType) == valid
        where:
        enumValues   || valid
        [ 0, '1']    || false
        [ '0', '1']  || true
        [ '1', '1']  || false
    }
}

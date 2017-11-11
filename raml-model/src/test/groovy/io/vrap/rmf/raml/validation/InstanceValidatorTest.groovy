package io.vrap.rmf.raml.validation

import io.vrap.rmf.raml.model.facets.FacetsFactory
import io.vrap.rmf.raml.model.facets.IntegerInstance
import io.vrap.rmf.raml.model.facets.NumberInstance
import io.vrap.rmf.raml.model.facets.StringInstance
import io.vrap.rmf.raml.model.types.IntegerType
import io.vrap.rmf.raml.model.types.NumberType
import io.vrap.rmf.raml.model.types.StringType
import io.vrap.rmf.raml.model.types.TypesFactory
import io.vrap.rmf.raml.model.util.RegExp
import org.eclipse.emf.common.util.Diagnostic
import spock.lang.Shared
import spock.lang.Specification

/**
 * Unit tests for {@link io.vrap.rmf.raml.validation.InstanceValidator}.
 */
class InstanceValidatorTest extends Specification {
    @Shared
    InstanceValidator instanceValidator = new InstanceValidator()

    def "validateStringInstance"() {
        when:
        StringInstance stringInstance = FacetsFactory.eINSTANCE.createStringInstance();
        stringInstance.value = value

        StringType stringType = TypesFactory.eINSTANCE.createStringType()
        stringType.minLength = minLength
        stringType.maxLength = maxLength
        stringType.pattern = pattern == null ? null : RegExp.of(pattern)
        then:
        List<Diagnostic> validationResults = instanceValidator.validate(stringInstance, stringType)
        validationResults.size() == numErrors
        where:
        value | minLength | maxLength | pattern | numErrors
        "123" | null      | null      | '[a-z]+'| 1
        "abc" | null      | null      | '[a-z]+'| 0
        "123" | null      | null      | null    | 0
        "123" | 0         | 3         | null    | 0
        "123" | 0         | 2         | null    | 1
    }

    def "validateNumberInstance"() {
        when:
        NumberInstance numberInstance = FacetsFactory.eINSTANCE.createNumberInstance()
        numberInstance.value = BigDecimal.valueOf(value)

        NumberType numberType = TypesFactory.eINSTANCE.createNumberType()
        numberType.minimum = minimum != null ? BigDecimal.valueOf(minimum) : null
        numberType.maximum = maximum != null ? BigDecimal.valueOf(maximum) : null
        numberType.multipleOf = multipleOf != null ? BigDecimal.valueOf(multipleOf) : null
        then:
        List<Diagnostic> validationResults = instanceValidator.validate(numberInstance, numberType)
        validationResults.size() == numErrors
        where:
        value | minimum | maximum | multipleOf | numErrors
        6.008 | null    | null    | 3          | 1
        6.0   | null    | null    | 3          | 0
        2.5   | 2.5     | 5.3     | null       | 0
        5.3   | 2.5     | 5.3     | null       | 0
        1     | null    | null    | null       | 0
    }

    def "validateIntegerInstance"() {
        when:
        IntegerInstance numberInstance = FacetsFactory.eINSTANCE.createIntegerInstance()
        numberInstance.value = value

        IntegerType integerType = TypesFactory.eINSTANCE.createIntegerType()
        integerType.minimum = minimum
        integerType.maximum = maximum
        integerType.multipleOf = multipleOf
        then:
        List<Diagnostic> validationResults = instanceValidator.validate(numberInstance, integerType)
        validationResults.size() == numErrors
        where:
        value | minimum | maximum | multipleOf | numErrors
        7     | null    | null    | 3          | 1
        6     | null    | null    | 3          | 0
        2     | 2       | 5       | null       | 0
        5     | 2       | 5       | null       | 0
        1     | null    | null    | null       | 0
    }
}

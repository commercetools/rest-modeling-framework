package io.vrap.rmf.raml.validation

import io.vrap.rmf.raml.model.InstanceFixtures
import io.vrap.rmf.raml.model.types.*
import io.vrap.rmf.raml.model.values.RegExp
import org.eclipse.emf.common.util.Diagnostic
import spock.lang.Shared
import spock.lang.Specification

/**
 * Unit tests for {@link io.vrap.rmf.raml.validation.InstanceValidator}.
 */
class InstanceValidatorTest extends Specification implements InstanceFixtures {
    @Shared
    InstanceValidator instanceValidator = new InstanceValidator()

    def "validateAgainstAnyType"() {
        when:
        AnyType anyType = TypesFactory.eINSTANCE.createAnyType()
        Instance instance = createInstance(value)
        then:
        instanceValidator.validate(instance, anyType).empty == true
        where:
        value << [ 1, 'string', false, BigDecimal.ONE ]
    }

    def "validateAgainstNilType"() {
        when:
        NilType nilType = TypesFactory.eINSTANCE.createNilType()
        Instance instance = createInstance(value)
        then:
        instanceValidator.validate(instance, nilType).empty == valid
        where:

        value     || valid
        ''        || true
        'string'  || false
    }

    def "validateStringInstance"() {
        when:
        StringInstance stringInstance = TypesFactory.eINSTANCE.createStringInstance();
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

    def "validateEnumStringInstance"() {
        when:
        String validEnumValue = "valid-enum-value"

        StringInstance enumValue = TypesFactory.eINSTANCE.createStringInstance();
        enumValue.value = validEnumValue

        StringType enumStringType = TypesFactory.eINSTANCE.createStringType()
        enumStringType.enum.add(enumValue)

        StringInstance validStringInstance = TypesFactory.eINSTANCE.createStringInstance();
        validStringInstance.value = value
        then:
        List<Diagnostic> validationResults = instanceValidator.validate(validStringInstance, enumStringType)
        validationResults.size() == numErrors
        where:
        value              | numErrors
        "123"              | 1
        "valid-enum-value" | 0
    }

    def "validateNumberInstance"() {
        when:
        NumberInstance numberInstance = TypesFactory.eINSTANCE.createNumberInstance()
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
        IntegerInstance numberInstance = TypesFactory.eINSTANCE.createIntegerInstance()
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

    def "validateIntegerInstanceEnum"() {
        when:
        IntegerInstance numberInstance = TypesFactory.eINSTANCE.createIntegerInstance()
        numberInstance.value = value

        IntegerType integerType = TypesFactory.eINSTANCE.createIntegerType()
        enumValues.each {
            integerType.enum.add(createInstance(it))
        }
        then:
        List<Diagnostic> validationResults = instanceValidator.validate(numberInstance, integerType)
        validationResults.size() == numErrors
        where:
        value | enumValues || numErrors
        7     | [1, 2]     || 1
        1     | [1, 2]     || 0
        1     | []         || 0
    }

    def "validateArrayInstance"() {
        when:
        ArrayInstance arrayInstance = TypesFactory.eINSTANCE.createArrayInstance()
        value.each {
            arrayInstance.value.add(createInstance(it))
        }
        ArrayType arrayType = TypesFactory.eINSTANCE.createArrayType()
        arrayType.items = TypesFactory.eINSTANCE.createIntegerType()
        arrayType.minItems = minItems
        arrayType.maxItems = maxItems
        arrayType.uniqueItems = uniqueItems
        then:
        List<Diagnostic> validationResults = instanceValidator.validate(arrayInstance, arrayType)
        validationResults.size() == numErrors
        where:
        value   | minItems | maxItems | uniqueItems || numErrors
        [1, ''] | null     | null     | null        || 1
        [1, 1]  | null     | null     | true        || 1
        [1, 1]  | null     | null     | false       || 0
        [1, 2]  | null     | null     | null        || 0
        [1, 2]  | 3        | null     | null        || 1
        [1, 2]  | null     | 1        | null        || 1
    }
}

package io.vrap.rmf.raml.model.util

import io.vrap.rmf.raml.model.facets.FacetsFactory
import io.vrap.rmf.raml.model.facets.NumberInstance
import io.vrap.rmf.raml.model.facets.StringInstance
import io.vrap.rmf.raml.model.types.NumberType
import io.vrap.rmf.raml.model.types.StringType
import io.vrap.rmf.raml.model.types.TypesFactory
import org.eclipse.emf.common.util.Diagnostic
import spock.lang.Shared
import spock.lang.Specification

/**
 * Unit tests for {@link InstanceValidator}.
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
        stringType.pattern = pattern
        then:
        List<Diagnostic> validationResults = instanceValidator.validate(stringInstance, stringType)
        validationResults.size() == numErrors
        where:
        value | minLength | maxLength | pattern | numErrors
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
        then:
        List<Diagnostic> validationResults = instanceValidator.validate(numberInstance, numberType)
        validationResults.size() == numErrors
        where:
        value | minimum | maximum | numErrors
        1     | null    | null    | 0
    }
}

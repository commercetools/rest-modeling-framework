package io.vrap.rmf.raml.validation

import io.vrap.rmf.raml.model.types.Annotation
import io.vrap.rmf.raml.model.types.StringAnnotationType
import io.vrap.rmf.raml.model.types.TypesFactory

class StringAnnotationValidationTest extends BaseValidatorTest {
    StringAnnotationType stringAnnotationType
    Annotation annotation

    def setup() {
        stringAnnotationType = TypesFactory.eINSTANCE.createStringAnnotationType()
        annotation = TypesFactory.eINSTANCE.createAnnotation()
        annotation.type = stringAnnotationType
    }

    def "validateStringAnnotation"() {
        when:
        annotation.value = createInstance(value)
        then:
        validate(annotation) == valid
        where:
        value    || valid
        '1'      || true
        1        || false
    }
}

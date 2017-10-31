package io.vrap.rmf.raml.validation

import io.vrap.rmf.raml.model.types.FileType
import io.vrap.rmf.raml.model.types.StringType
import io.vrap.rmf.raml.model.types.TypesFactory
import io.vrap.rmf.raml.model.util.BaseValidatorTest

class FileTypeValidationTest extends BaseValidatorTest {
    FileType fileType

    def "setup"() {
        fileType = TypesFactory.eINSTANCE.createFileType()
    }

    def "validateLengthRestriction"() {
        when:
        fileType.minLength = minLength
        fileType.maxLength = maxLength
        then:
        validate(fileType) == valid
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

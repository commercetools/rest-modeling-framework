package io.vrap.rmf.raml.validation

import io.vrap.rmf.raml.model.LibraryFixtures
import io.vrap.rmf.raml.model.TypeFixtures
import io.vrap.rmf.raml.model.modules.Library
import io.vrap.rmf.raml.model.types.ObjectType

class ObjectInstanceValidationTest extends BaseValidatorTest implements TypeFixtures, LibraryFixtures {
    def "object type with valid examples"() {
        when:
        ObjectType objectTypeWithExample = constructType(
                '''\
            properties:
                name: string
                age: number
                sex?: string
            example:
                name: Hans
                age: 12
        ''')
        then:
        validate(objectTypeWithExample) == true
    }

    def "object type with one property"() {
        when:
        ObjectType objectTypeWithExample = constructType(
                """\
            properties:
                property: ${type}
            example:
                property: ${value}
        """)
        then:
        validate(objectTypeWithExample) == valid
        where:
        type      | value    || valid
        'string'  | 'aValue' || true
        'string'  | 12       || false
        'number'  | 12       || true
        'number'  | 'aValue' || false
        'boolean' | true     || true
        'boolean' | false    || true
        'boolean' | 'aValue' || false
    }

    def "object type with string enum property"() {
        when:
        Library library = constructLibrary(
                """\
        usage: Just a test
        types:
            Enum:
                type: string
                enum:
                    - value1
                    - value2
            Type:
                properties:
                    enum: Enum
                example:
                    enum: ${enumValue}
        """)
        then:
        validate(library) == valid
        where:
        enumValue || valid
        'value1'  || true
        'aValue'  || false
    }

    def "object type with integer enum property"() {
        when:
        Library library = constructLibrary(
                """\
        usage: Just a test
        types:
            Enum:
                type: integer
                enum:
                    - 1
                    - 2
            Type:
                properties:
                    enum: Enum
                example:
                    enum: ${enumValue}
        """)
        then:
        validate(library) == valid
        where:
        enumValue || valid
        1         || true
        2         || true
        3         || false
    }

    def "type hierarchy with discriminators"() {
        when:
        Library library = constructLibrary(
                """\
        usage: Just a test
        types:
            Base:
                discriminator: type
                additionalProperties: false
                properties:
                    type: string
                example:
                    type: ${discriminatorValue}
                    name: Peter
            SubType:
                type: Base
                properties:
                    name: string
        """)
        then:
        validate(library) == valid
        where:
        discriminatorValue || valid
        'Base'             || false
        'SubType'          || true
    }
}

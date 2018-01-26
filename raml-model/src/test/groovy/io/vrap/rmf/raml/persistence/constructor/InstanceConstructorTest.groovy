package io.vrap.rmf.raml.persistence.constructor

import io.vrap.rmf.raml.model.InstanceFixtures
import io.vrap.rmf.raml.model.types.*
import spock.lang.Specification

/**
 * Unit tests for {@link InstanceConstructor}.
 */
class InstanceConstructorTest extends Specification implements InstanceFixtures {

    def "primitive type instances parsed correctly"() {
        when:
        Instance instance = constructInstance(input)
        then:
        instance.eGet(instance.eClass().getEStructuralFeature('value')) == value
        where:
        input    | value
        'text'   | 'text'
        'true'   | true
        'false'  | false
        '1'      | 1
        '1.0'    | BigDecimal.ONE
    }

    def "object instance"() {
        when:
        Instance instance = constructInstance(
                '''\
        name: Name
        ''')

        then:
        instance instanceof ObjectInstance
        ObjectInstance objectInstance = instance
        objectInstance.value.size() == 1
        objectInstance.value[0].name == 'name'
        objectInstance.value[0].value instanceof StringInstance
    }

    def "object instance with array"() {
        when:
        Instance instance = constructInstance(
                '''\
        names: 
            - Name1
            - Name2
        ''')

        then:
        instance instanceof ObjectInstance
        ObjectInstance objectInstance = instance
        objectInstance.value.size() == 1
        objectInstance.value[0].name == 'names'
        objectInstance.value[0].value instanceof ArrayInstance
        ArrayInstance arrayInstance = objectInstance.value[0].value
        arrayInstance.value.size() == 2
        arrayInstance.value[0] instanceof StringInstance
        StringInstance value1 = arrayInstance.value[0]
        value1.value == 'Name1'
        StringInstance value2 = arrayInstance.value[1]
        value2.value == 'Name2'
    }

    def "object instance with object instance"() {
        when:
        Instance instance = constructInstance(
                '''\
        names: 
            firstName: John
            lastName: Doe
            age: 42
        ''')

        then:
        instance instanceof ObjectInstance
        ObjectInstance objectInstance = instance
        objectInstance.value.size() == 1
        objectInstance.value[0].value instanceof ObjectInstance
        ObjectInstance nestedObjectInstance = objectInstance.value[0].value
        nestedObjectInstance.value.size() == 3
    }

    def "array instance"() {
        when:
        Instance instance = constructInstance(
                '''\
        - 1
        - 2
        ''')

        then:
        instance instanceof ArrayInstance
        ArrayInstance arrayInstance = instance
        arrayInstance.value.size() == 2
        arrayInstance.value[0] instanceof IntegerInstance
        IntegerInstance value1 = arrayInstance.value[0]
        value1.value == 1
        IntegerInstance value2 = arrayInstance.value[1]
        value2.value == 2
    }
}

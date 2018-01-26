package io.vrap.rmf.raml.validation

import io.vrap.rmf.raml.model.InstanceFixtures
import io.vrap.rmf.raml.model.TypeFixtures
import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.modules.ModulesFactory
import io.vrap.rmf.raml.model.types.ObjectType
import io.vrap.rmf.raml.model.types.TypesFactory

class ObjectTypeValidationTest extends BaseValidatorTest implements TypeFixtures, InstanceFixtures {
    ObjectType objectType
    Api api

    def "setup"() {
        objectType = TypesFactory.eINSTANCE.createObjectType()
        api = ModulesFactory.eINSTANCE.createApi()
    }

    def "Neither discriminator nor discriminatorValue can be defined for any inline type"() {
        when:
        if (inlineType) {
            api.inlineTypes.add(objectType)
        }
        objectType.discriminator = discriminator
        objectType.discriminatorValue = discriminatorValue
        then:
        validate(objectType) == valid
        where:
        discriminator   | discriminatorValue   | inlineType || valid
        null            | null                 | true       || true
        'discriminator' | null                 | true       || false
        null            | 'discriminatorValue' | true       || false
        'discriminator' | 'discriminatorValue' | true       || false
        null            | null                 | false      || true
        'discriminator' | null                 | false      || true
        null            | 'discriminatorValue' | false      || true
        'discriminator' | 'discriminatorValue' | false      || true
    }

    def "strict example validation"() {
        when:
        ObjectType objectTypeWithExample = constructType(
                '''\
            properties:
                name: string
                phoneNo: number
            example:
                name: Hans
        ''')
        objectTypeWithExample.examples[0].strict = strict == null ? null : createInstance(strict)
        then:
        validate(objectTypeWithExample) == valid
        where:
        strict || valid
        null   || false
        true   || false
        false  || true
    }
}

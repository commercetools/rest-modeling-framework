package io.vrap.rmf.raml.model.types

import spock.lang.Specification

/**
 * Unit tests for {@link ObjectType}.
 */
class ObjectTypeTest extends Specification {

    def "discriminatorValueOrDefault"() {
        when:
        ObjectType objectType = TypesFactory.eINSTANCE.createObjectType()
        objectType.name = name
        objectType.discriminatorValue = discriminatorValue
        then:
        objectType.discriminatorValueOrDefault() == discriminatorValueOrDefault
        where:
        name                          | discriminatorValue || discriminatorValueOrDefault
        'Type'                        | null               || 'Type'
        'TypeWithDisciminatorValue'   | 'discriminator'    || 'discriminator'
    }

    def "getType finds itself"() {
        when:
        ObjectType objectType = TypesFactory.eINSTANCE.createObjectType()
        objectType.name = 'Type'
        objectType.discriminatorValue = discriminatorValue
        then:
        objectType.getType(queryDiscriminatorValue) == objectType
        where:
        discriminatorValue || queryDiscriminatorValue
        null               || 'Type'
        'discriminator'    || 'discriminator'
    }
}

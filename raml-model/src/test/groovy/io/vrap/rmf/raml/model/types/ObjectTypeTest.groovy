package io.vrap.rmf.raml.model.types

import io.vrap.rmf.raml.model.values.RegExp
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

    def "additionalPropertiesInherited"() {
        when:
        ObjectType baseType = TypesFactory.eINSTANCE.createObjectType()
        baseType.additionalProperties = baseAdditionalProperties
        ObjectType subType = TypesFactory.eINSTANCE.createObjectType()
        subType.setType(baseType)
        then:
        subType.additionalPropertiesInherited() == additionalPropertiesInherited
        where:
        baseAdditionalProperties | subAdditionalProperties || additionalPropertiesInherited
        null                     | null                    || true
        true                     | null                    || true
        false                    | null                    || false
        false                    | true                    || false
    }

    def "getProperty with pattern"() {
        when:
        Property property = TypesFactory.eINSTANCE.createProperty()
        property.pattern = pattern == null ? null : RegExp.of(pattern)
        ObjectType objectType = TypesFactory.eINSTANCE.createObjectType()
        objectType.properties.add(property)
        then:
        (objectType.getProperty(name) != null) == propertyFound
        where:
        pattern | name   || propertyFound
        '.*'    | 'test' || true
        'na.*'  | 'name' || true
        'na.*'  | 'test' || false
        null    | 'test' || false
    }
}

package io.vrap.rmf.raml.regression

import io.vrap.rmf.raml.model.RamlModelResult
import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.types.IntegerType
import io.vrap.rmf.raml.model.types.IntersectionType
import io.vrap.rmf.raml.model.types.ObjectType

class TypesTest extends RegressionTest {

    def "unknown type"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
                #%RAML 1.0
                title: Example API
                version: v1
                types:
                  DateOfBirthMessage:
                    properties:
                      dateOfBirth:
                        type: DateTime
        ''')
        then:
        ramlModelResult.validationResults.size() == 1
    }

    def "unknown type in extension"() {
        when:
        writeFile(
                "api.raml",
                '''\
                #%RAML 1.0
                title: Example API
                version: v1
                types:
                  DateOfBirthMessage:
                    properties:
                      dateOfBirth:
                        type: DateTime
        ''')

        RamlModelResult<Api> ramlModelResult = constructApi(
                "extend.raml",
                Arrays.asList("api.raml"),
                '''\
                #%RAML 1.0 Extension
                usage: Add something
                extends: api.raml
                '''
        )
        then:
        ramlModelResult.validationResults.size() == 1
    }

    def "union type array"() {
        when:

        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
                #%RAML 1.0
                title: Example API
                version: v1
                types:
                  SomeType:
                    type: object
                  AttributeValue:
                    type: SomeType |
                          SomeType[]
                    
                '''
        )
        then:
        ramlModelResult.validationResults.size() == 0
    }

    def "multi inheritance type"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
                #%RAML 1.0
                title: Example API
                version: v1
                types:
                  Person:
                    type: object
                    properties:
                      name: string
                  Employee:
                    type: object
                    properties:
                      employeeNr: integer
                  Teacher:
                    type: [ Person, Employee ]
                '''
        )
        then:
        ramlModelResult.validationResults.size() == 0
        with(ramlModelResult.rootObject) {
            types.size() == 3
            types[2] instanceof ObjectType
            types[2].type instanceof IntersectionType
            IntersectionType intersectionType = types[2].type
            intersectionType.allOf[0] == types[0]
            intersectionType.allOf[1] == types[1]
        }
    }

    def "multi inheritance type with primitive types"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
                #%RAML 1.0
                title: Example API
                version: v1
                types:
                  PositiveInt:
                    type: integer
                    minimum: 0
                  Teacher:
                    type: [ integer, PositiveInt ]
                '''
        )
        then:
        ramlModelResult.validationResults.size() == 0
        with(ramlModelResult.rootObject) {
            types.size() == 2
            types[0] instanceof IntegerType
            types[1] instanceof IntegerType
            types[1].type instanceof IntersectionType
            IntersectionType intersectionType = types[1].type
            intersectionType.allOf.size() == 2
            intersectionType.allOf[0] instanceof IntegerType
            intersectionType.allOf[1] == types[0]
        }
    }

    def "multi inheritance discriminator resolve order"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
                #%RAML 1.0
                title: Example API
                version: v1
                types:
                  Resource:
                    type: object
                    properties:
                      id: string
                  CategoryCreatedMessage:
                    type: [Message, CategoryCreatedMessagePayload]
                    discriminatorValue: CategoryCreated
                  Message:
                    type: Resource
                    discriminator: type
                    properties:
                      type: string
                      sequenceNumber: number
                  MessagePayload:
                    type: object
                    discriminator: type
                    properties:
                      type: string
                  CategoryCreatedMessagePayload:
                    type: MessagePayload
                    discriminatorValue: CategoryCreated
                    properties:
                      category: object
                '''
        )
        then:
        ramlModelResult.validationResults.size() == 0
        ramlModelResult.rootObject.getType('CategoryCreatedMessage') instanceof ObjectType
        ObjectType categoryCreatedMessageType = ramlModelResult.rootObject.getType('CategoryCreatedMessage')
        categoryCreatedMessageType.discriminatorValue == 'CategoryCreated'
        categoryCreatedMessageType.getType() instanceof IntersectionType
        IntersectionType intersectionType = categoryCreatedMessageType.getType()
        intersectionType.allOf.size() == 2
    }
}

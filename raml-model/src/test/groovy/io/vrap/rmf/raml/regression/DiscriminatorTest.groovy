package io.vrap.rmf.raml.regression


import io.vrap.rmf.raml.model.RamlModelResult
import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.types.AnyType
import io.vrap.rmf.raml.model.types.Instance
import io.vrap.rmf.raml.model.util.InstanceHelper
import io.vrap.rmf.raml.validation.InstanceValidator
import org.eclipse.emf.common.util.Diagnostic

class DiscriminatorTest extends RegressionTest {
    def "nested-discriminator-example-validation"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Some API
        types:
            UpdateAction:
                type: object
                discriminator: action
                properties:
                    action:
                        type: string
            CartDiscountChangeTargetAction:
                type: UpdateAction
                discriminatorValue: changeTarget
                example:
                    {
                        "action": "changeTarget",
                        "target": {
                            "type": "lineItems",
                            "predicate": "sku = \\"mySKU\\""
                        }
                    }
                properties:
                    target:
                        type: CartDiscountTarget
            CartDiscountTarget:
                type: object
                discriminator: type
                properties:
                    type:
                        type: string
            CartDiscountLineItemsTarget:
                type: CartDiscountTarget
                discriminatorValue: lineItems
                properties:
                    predicate:
                        type: string
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
        AnyType type = ramlModelResult.rootObject.getType('CartDiscountChangeTargetAction')

        Instance instance = InstanceHelper.parseJson('''\
                    {
                        "action": "changeTarget",
                        "target": {
                            "type": "lineItems",
                            "predicate": "sku = \\"mySKU\\""
                        }
                    }''')
        new InstanceValidator().validate(instance, type).size() == 0
    }

    def "unknown-discriminator-example-validation"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Some API
        types:
            UpdateAction:
                type: object
                discriminator: action
                properties:
                    action:
                        type: string
            CartDiscountChangeTargetAction:
                type: UpdateAction
                discriminatorValue: changeTarget
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
        AnyType type = ramlModelResult.rootObject.getType('UpdateAction')

        Instance instance = InstanceHelper.parseJson('''\
                    {
                        "action": "addLineItem",
                        "target": {
                            "type": "lineItems",
                            "predicate": "sku = \\"mySKU\\""
                        }
                    }''')
        List< Diagnostic> result = new InstanceValidator().validate(instance, type)
        result.find { it.severity == Diagnostic.ERROR }.iterator().size() == 1
    }

    def "optional-discriminator-example-validation"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Some API
        types:
            Reference:
                type: object
                discriminator: typeId
                properties:
                    typeId?:
                        type: string
                    id?:
                        type: string
            TypeReference:
                type: Reference
                discriminatorValue: type
            CategoryReference:
                type: Reference
                discriminatorValue: category
            ReferenceTypeId:
                type: string
                enum:
                    - type
                    - category
            CustomFields:
                type: object
                properties:
                    type:
                        type: TypeReference
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
        AnyType type = ramlModelResult.rootObject.getType('CustomFields')

        Instance instance = InstanceHelper.parseJson(input)
        List< Diagnostic> result = new InstanceValidator().validate(instance, type)
        result.find { it.severity == Diagnostic.ERROR }.iterator().size() == errors
        where:
        input                                                   | errors
        '{ "type": { "id": "test" } }'                          | 0
        '{ "type": { "id": "test", "typeId": "type" } }'        | 0
        '{ "type": { "id": "test", "typeId": "category" } }'    | 1
    }

    def "discriminator-property-description"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''
                #%RAML 1.0
                title: Import sink types.
                                
                types:
                  ImportSink:
                    properties:
                      skipPredicate?:
                        description: |
                          The optional skip predicate is evaluated against the current state of the corresponding CTP resource.
                          If it evaluates to true, the import will be skipped.
                        type: Predicate
                    examples:
                      with-skip-predicate: |
                        {
                          "skipPredicate": {
                            "type": "javascript",
                            "expression": "!attributes.new"
                          }
                        }
                  Predicate:
                    discriminator: type
                    type: object
                    properties:
                      type:
                        type: string
                  JavascriptPredicate:
                    type: Predicate
                    discriminatorValue: javascript
                    properties:
                      expression:
                        type: string
                ''')
        then:
        ramlModelResult.validationResults.size() == 0
    }

    def "additional-field-validation"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Some API
        types:
            Person:
                type: object
                properties:
                    name: string
                    age: number
                    test?: object
                    test2?:
                        properties:
                            foo: string
                            baz: object 
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
        AnyType type = ramlModelResult.rootObject.getType('Person')

        Instance instance = InstanceHelper.parseJson(input)
        List< Diagnostic> result = new InstanceValidator().validate(instance, type, strict)
        result.find { it.severity == Diagnostic.ERROR }.iterator().size() == errors
        where:
        input                                                                       | errors | strict
        '{ "name": "Hans", "age": 13 }'                                             | 0      | true
        '{ "name": "Hans", "age": 13, "foo": "bar" }'                               | 1      | true
        '{ "name": "Hans", "age": 13 }'                                             | 0      | false
        '{ "name": "Hans", "age": 13, "foo": "bar" }'                               | 0      | false
        '{ "name": "Hans", "age": 13, "foo": "bar", "test": { "foo": "bar" } }'     | 1      | true
        '{ "name": "Hans", "age": 13, "foo": "bar", "test": { "foo": "bar" } }'     | 0      | false
        '{ "name": "Hans", "age": 13, "test": { "foo": "bar" } }'                   | 0      | true
        '{ "name": "Hans", "age": 13, "test": { "foo": "bar" } }'                   | 0      | false
        '{ "name": "Hans", "age": 13, "test2": { "foo": "foo", "bar": { "foo": "bar" }, "baz": { "foo": "bar" } } }'    | 1      | true
        '{ "name": "Hans", "age": 13, "test2": { "foo": "foo", "bar": { "foo": "bar" }, "baz": { "foo": "bar" } } }'    | 0      | false
    }
}

package io.vrap.rmf.raml.regression

import io.vrap.rmf.raml.model.RamlModelResult
import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.resources.HttpMethod
import io.vrap.rmf.raml.model.types.ArrayInstance
import io.vrap.rmf.raml.model.types.Example
import io.vrap.rmf.raml.model.types.Instance
import io.vrap.rmf.raml.model.types.ObjectInstance
import io.vrap.rmf.raml.model.util.InstanceHelper
import io.vrap.rmf.raml.validation.InstanceValidator
import org.eclipse.emf.common.util.Diagnostic

class ExampleTest extends RegressionTest {
    def "example" () {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
                #%RAML 1.0
                title: Annotating Examples
                
                annotationTypes:
                    condition : string
                
                /request:
                    get:
                        body:
                            application/json:
                                example:
                                    (condition): test
                                    value: |
                                        {a:1}
                                examples:
                                    example1:
                                        (condition): test
                                        strict: true
                                        value: |
                                            {a:1}
                                    example2:
                                        value: |
                                            {a:2}
                                    example3:
                                        value: |
                                            a:3
                                    example4: "{a:4}"
                                    example5:
                                        name: foo
                                    example6:
                                        (condition): test
                                        value:
                                            name: foo
                                            value: bar
                                    example7:
                                        (condition): test
                                        value:
                                            - foo
                                            - bar
                                    example8:
                                        - foo
                                        - bar
                                    example9:
                                        value:
                                            - foo
                                            - bar
                                    example10:
                                        displayName: Example10
                                        description: Lorem ipsum
                                        strict: false
                                        (condition): test
                                        value:
                                            - foo
                                            - bar
                                    example11: /test/12345
                ''')
        then:
        ramlModelResult.validationResults.size() == 0
        List<Example> examples = ramlModelResult.rootObject.resources[0].getMethod(HttpMethod.GET).getBody('application/json').type.examples;

        examples[1].instanceValue.trim() == "{a:1}".trim()
        examples[2].instanceValue.trim() == "{a:2}".trim()
        examples[3].instanceValue.trim() == "a:3".trim()
        examples[4].instanceValue.trim() == "{a:4}".trim()
        examples[11].instanceValue.trim() == "/test/12345".trim()

        ((ObjectInstance)examples[5].value).value[0].name == "name";
        ((ObjectInstance)examples[5].value).value[0].value.value == "foo";

        ((ObjectInstance)examples[6].value).value[0].name == "name";
        ((ObjectInstance)examples[6].value).value[0].value.value == "foo";
        ((ObjectInstance)examples[6].value).value[1].name == "value";
        ((ObjectInstance)examples[6].value).value[1].value.value == "bar";

        ((ArrayInstance)examples[7].value).value[0].value.trim() == "foo";
        ((ArrayInstance)examples[7].value).value[1].value.trim() == "bar";
        ((ArrayInstance)examples[8].value).value[0].value.trim() == "foo";
        ((ArrayInstance)examples[8].value).value[1].value.trim() == "bar";
        ((ArrayInstance)examples[9].value).value[0].value.trim() == "foo";
        ((ArrayInstance)examples[9].value).value[1].value.trim() == "bar";

        ((ArrayInstance)examples[10].value).value[0].value.trim() == "foo";
        ((ArrayInstance)examples[10].value).value[1].value.trim() == "bar";
        examples[10].displayName.value == 'Example10'
        examples[10].description.value == 'Lorem ipsum'

        examples[0].strict.value
        examples[1].strict.value
        !examples[10].strict.value

        examples[1].getAnnotation('condition').value.value == 'test'
        examples[6].getAnnotation('condition').value.value == 'test'
        examples[7].getAnnotation('condition').value.value == 'test'
        examples[10].getAnnotation('condition').value.value == 'test'
    }

    def "number-example-string-valid"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
                #%RAML 1.0
                title: Annotating Examples
                
                types:
                    SomeObject:
                        properties:
                            sort: string
                        examples:
                            valid: |
                                {
                                    "sort": "0.2"
                                }
                ''')
        then:
        ramlModelResult.validationResults.size() == 0
    }

    def "number-example-string-invalid"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
                #%RAML 1.0
                title: Annotating Examples
                
                types:
                    SomeObject:
                        properties:
                            sort: string
                        examples:
                            invalid: |
                                {
                                    "sort": 0.2
                                }
                ''')
        then:
        ramlModelResult.validationResults.size() == 1
    }

    def "number-example-object-valid"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
                #%RAML 1.0
                title: Annotating Examples
                
                types:
                    SomeObject:
                        properties:
                            sort: string
                        examples:
                            valid: { "sort": "0.2" }
                ''')
        then:
        ramlModelResult.validationResults.size() == 0
    }

    def "number-example-object-invalid"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
                #%RAML 1.0
                title: Annotating Examples
                
                types:
                    SomeObject:
                        properties:
                            sort: string
                        examples:
                            invalid: { "sort": 0.2 }
                ''')
        then:
        ramlModelResult.validationResults.size() == 1
    }

    def "number-example-raml-object-valid"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
                #%RAML 1.0
                title: Annotating Examples
                
                types:
                    SomeObject:
                        properties:
                            sort: string
                        examples:
                            valid:
                                sort: "0.2"
                ''')
        then:
        ramlModelResult.validationResults.size() == 0
    }

    def "date-validation"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
                #%RAML 1.0
                title: Date Examples
                
                types:
                    SomeObject:
                        properties:
                          birthday:
                            type: date-only # no implications about time or offset
                            example: "2015-05-23"
                          lunchtime:
                            type: time-only # no implications about date or offset
                            example: "12:30:00"
                          fireworks:
                            type: datetime-only # no implications about offset
                            example: "2015-07-04T21:00:00"
                          created:
                            type: datetime
                            example: "2016-02-28T16:41:41.090Z"
                            format: rfc3339 # the default, so no need to specify
                          lastUpdated:
                            type: datetime
                            example: "2016-02-28T16:41:41.000+00:00"
                            format: rfc3339 # the default, so no need to specify
                          If-Modified-Since:
                            type: datetime
                            example: "Sun, 28 Feb 2016 16:41:41 GMT"
                            format: rfc2616 # this time it's required, otherwise, the example format is invalid
                ''')
        then:
        ramlModelResult.validationResults.size() == 0
    }

    def "uri"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
                #%RAML 1.0
                title: uri
                
                types:
                    Foo:
                        properties:
                          uri: string
                        example: |
                           {
                             "uri": "https://example.org"
                           }
                ''')
        then:
        ramlModelResult.validationResults.size() == 0

        Instance result = InstanceHelper.parseJson('{"uri": "https:\\/\\/example.org" }')
        List<Diagnostic> validationResults = new InstanceValidator().validate(result, ramlModelResult.rootObject.getType("Foo"));
        validationResults.size() == 0

    }

    def "value named property"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
                #%RAML 1.0
                title: Value named property
                
                types:
                    Foo:
                        properties:
                          value: string
                        example: |
                           {
                             "value": "bar"
                           }
                    Bar:
                        properties:
                           value: string
                        example:
                           value: {
                             "value": "bar"
                           }
                    Baz:
                        properties:
                           value: Boz
                        example:
                           value: {
                             "value": {
                               "value": "boz"
                             }
                           }
                    Boz:
                        properties:
                           value: string
                        example:
                           value: {
                               "value": "boz"
                           }
                ''')
        then:
        ramlModelResult.validationResults.size() == 0
    }

    def "enum value example"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
                #%RAML 1.0
                title: Value named property
                
                types:
                    Enum:
                        type: string
                        enum: [ "val1", "val2" ]
                    Foo:
                        properties:
                          value: Enum
                        example: |
                           {
                             "value": "bar"
                           }
                ''')
        then:
        ramlModelResult.validationResults.size() == 1
    }

    def "enum value examples"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
                #%RAML 1.0
                title: Value named property
                
                types:
                    Enum:
                        type: string
                        enum: [ "val1", "val2" ]
                    Foo:
                        properties:
                          value: Enum
                        examples:
                            one: |
                                {
                                    "value": "bar"
                                }
                ''')
        then:
        ramlModelResult.validationResults.size() == 1
    }

    def "validate value named property"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
                #%RAML 1.0
                title: Value named property
                
                types:
                    Foo:
                        properties:
                          prices: Price[]
                    Price:
                        properties:
                           value: Money
                    Money:
                        properties:
                           currencyCode: string
                           centAmount: integer
                ''')
        String json = '{ "prices": [{ "value": { "currencyCode": "EUR", "centAmount": 100 } }] }'

        then:
        ramlModelResult.validationResults.size() == 0

        Instance result = InstanceHelper.parseJson(json)
        List<Diagnostic> validationResults = new InstanceValidator().validate(result, ramlModelResult.rootObject.getType("Foo"));
        validationResults.size() == 0
    }

    def "validate numbers"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
                #%RAML 1.0
                title: Number validation
                
                types:
                    Foo:
                        properties:
                          centAmount:
                            type: number
                            format: int64
                ''')
        String json = '{ "centAmount" : 160158691200 }'

        then:
        ramlModelResult.validationResults.size() == 0

        Instance result = InstanceHelper.parseJson(json)
        List<Diagnostic> validationResults = new InstanceValidator().validate(result, ramlModelResult.rootObject.getType("Foo"));
        validationResults.size() == 0
    }
}

package io.vrap.rmf.raml.regression

import io.vrap.rmf.raml.model.RamlModelResult
import io.vrap.rmf.raml.model.modules.Api
import spock.lang.Ignore

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

    @Ignore
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

    @Ignore
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
}

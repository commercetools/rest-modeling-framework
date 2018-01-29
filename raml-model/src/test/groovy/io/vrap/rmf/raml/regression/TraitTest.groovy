package io.vrap.rmf.raml.regression

import io.vrap.rmf.raml.model.RamlModelResult
import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.resources.HttpMethod
import io.vrap.rmf.raml.model.responses.Response
import spock.lang.Ignore

class TraitTest extends RegressionTest {

    @Ignore
    def "status-code-template"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
                #%RAML 1.0
                title: Example API
                version: v1
                traits:
                  respCode:
                    responses:
                      201:
                        description: <<description>>
                resourceTypes:
                    test:
                        get:
                            is:
                                - respCode:
                                    description: Post created, returns the created post.
                /servers:
                  type: test
                  get:
                    description: test
                    responses:
                        200:
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
        ramlModelResult.rootObject.resources[0].getMethod(HttpMethod.GET).responses.size() == 2
    }
}

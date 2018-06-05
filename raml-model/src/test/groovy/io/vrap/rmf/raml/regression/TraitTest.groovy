package io.vrap.rmf.raml.regression

import io.vrap.rmf.raml.model.RamlModelResult
import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.resources.HttpMethod
import io.vrap.rmf.raml.model.resources.Method
import io.vrap.rmf.raml.model.resources.Resource

class TraitTest extends RegressionTest {

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
        ramlModelResult.rootObject.resources.size() == 1
        Resource resource = ramlModelResult.rootObject.resources[0]
        Method method = resource.getMethod(HttpMethod.GET)
        method != null
        method.responses.size() == 2
        method.responses.find {it.statusCode == "201"}.description.value == 'Post created, returns the created post.'
    }
}

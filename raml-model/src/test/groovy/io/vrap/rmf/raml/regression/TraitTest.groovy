package io.vrap.rmf.raml.regression

import io.vrap.rmf.raml.model.RamlModelResult
import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.resources.HttpMethod
import io.vrap.rmf.raml.model.resources.Method
import io.vrap.rmf.raml.model.resources.Resource
import spock.lang.Ignore

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

//    @Ignore
    def "queryparameter"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
                #%RAML 1.0
                title: Example API
                version: v1
                baseUri: https://api.example.org
                traits:
                  filter:
                    queryParameters:
                      date[from]?:
                        type: string
                      from?:
                        type: string
                      date_from:
                        type: string
                      dates_from:
                        type: string
                        required: false
                resourceTypes:
                  test:
                    get:
                      is:
                        - filter
                /servers:
                  type: test
                  get:
                    queryParameters:
                      date[to]?:
                        type: string
                      to?:
                        type: string
                      date_to:
                        type: string
                      dates_to:
                        type: string
                        required: false
                    responses:
                        200:
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
        ramlModelResult.rootObject.resources.size() == 1
        Resource resource = ramlModelResult.rootObject.resources[0]
        Method method = resource.getMethod(HttpMethod.GET)
        method != null
        method.queryParameters.size() == 8
    }
}

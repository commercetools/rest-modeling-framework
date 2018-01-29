package io.vrap.rmf.raml.regression

import io.vrap.rmf.raml.model.RamlModelResult
import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.resources.HttpMethod
import io.vrap.rmf.raml.model.resources.Method
import io.vrap.rmf.raml.model.types.Annotation
import spock.lang.Ignore

class ExtendTest extends RegressionTest {
    @Ignore
    def "extend-trait-with-annotation" () {
        when:
        writeFile(
                "api.raml",
                '''\
                #%RAML 1.0
                title: Some API
                resourceTypes:
                    base:
                        delete?:
                            is:
                                - versioned
                traits:
                    versioned:
                        queryParameters:
                            version:
                                type: number
                /category:
                    /{ID}:
                        type: base
                        delete:
        ''')
        RamlModelResult<Api> ramlModelResult = constructApi(
                "extend.raml",
                Arrays.asList("api.raml"),
                '''\
                #%RAML 1.0 Extension
                usage: Add postman test scripts
                extends: api.raml
                annotationTypes:
                    postman-default-value:
                        type: string
                        allowedTargets: TypeDeclaration
                traits:
                    versioned:
                        queryParameters:
                            version:
                                (postman-default-value): "{{version}}"
                '''
        )
        then:
        ramlModelResult.validationResults.size() == 0
        Api api = ramlModelResult.rootObject
        Method method = api.resources.get(0).resources.get(0).getMethod(HttpMethod.DELETE)
        api.getAnnotationType("postman-default-value") != null
        Annotation annotation = method.queryParameters.get(0).getAnnotation("postman-default-value")
        annotation.value.value == "{{version}}"
    }

    @Ignore
    def "extend-trait-with-annotation-multi-usage" () {
        when:
        writeFile(
                "api.raml",
                '''\
                #%RAML 1.0
                title: Some API
                resourceTypes:
                    base:
                        delete?:
                            is:
                                - versioned
                traits:
                    versioned:
                        queryParameters:
                            version:
                                type: number
                /category:
                    /{ID}:
                        type: base
                        delete:
                /customer:
                    /{ID}:
                        type: base
                        delete:
        ''')

        RamlModelResult<Api> ramlModelResult = constructApi(
                "extend.raml",
                Arrays.asList("api.raml"),
                '''\
                #%RAML 1.0 Extension
                usage: Add postman test scripts
                extends: api.raml
                annotationTypes:
                    postman-default-value:
                        type: string
                        allowedTargets: TypeDeclaration
                traits:
                    versioned:
                        queryParameters:
                            version:
                                (postman-default-value): "{{version}}"
                '''
        )
        then:
        ramlModelResult.validationResults.size() == 0
        Api api = ramlModelResult.rootObject
        Method method = api.resources.get(0).resources.get(0).getMethod(HttpMethod.DELETE)
        api.getAnnotationType("postman-default-value") != null
        Annotation annotation = method.queryParameters.get(0).getAnnotation("postman-default-value")
        annotation.value.value == "{{version}}"
    }

    def "multi-extension"() {
        when:
        writeFile("api.raml",
                '''\
                #%RAML 1.0
                title: Some API
                /category:
                    get:
                        description: Some API
                ''')
        writeFile("extend.raml",
                Arrays.asList("api.raml"),
                '''\
                #%RAML 1.0 Extension
                usage: extends api
                extends: api.raml
                /category:
                    post:
                        description: Extended API
                ''')
        RamlModelResult<Api> ramlModelResult = constructApi(
                "final.raml",
                Arrays.asList("extend.raml"),
                '''\
                #%RAML 1.0 Extension
                usage: final extension
                extends: extend.raml
                /category:
                    delete:
                        description: Final API
                ''')
        then:
        ramlModelResult.validationResults.size() == 0
        ramlModelResult.rootObject.resources[0].getMethod(HttpMethod.GET).description.value == "Some API"
        ramlModelResult.rootObject.resources[0].getMethod(HttpMethod.POST).description.value == "Extended API"
        ramlModelResult.rootObject.resources[0].getMethod(HttpMethod.DELETE).description.value == "Final API"
    }
}

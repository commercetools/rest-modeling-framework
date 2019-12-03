package io.vrap.rmf.raml.regression

import io.vrap.rmf.raml.model.RamlModelResult
import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.resources.HttpMethod
import io.vrap.rmf.raml.model.resources.Method

class AnnotationTest extends RegressionTest {
    def "validation-of-annotation-value"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''
        #%RAML 1.0 Library
        usage: Defines annotations.
        
        annotationTypes:
            ReferenceType:
                type: string
                enum:
                - category
        types:
            MyType:
                (ReferenceType): product
                ''')
        then:
        ramlModelResult.validationResults.size() == 1
        ramlModelResult.validationResults[0].message == "Value 'product' is not defined in enum facet '[category]'"
    }

    def "annotate-query-param"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''
        #%RAML 1.0
        title: "Test"
        annotationTypes:
            placeholderParam:
                type: object
                allowedTargets: TypeDeclaration
                properties:
                    paramName: string
                    template: string
                    placeholder: string
        /projection:
            get:
                queryParameters:
                    fuzzy:
                        type: string
                    test:
                        type: string
                        required: false
                        (placeholderParam):
                            paramName: searchKeywords
                            template: searchKeywords.<locale>
                            placeholder: locale
                ''')
        then:
        ramlModelResult.validationResults.size() == 0
        Method method = ramlModelResult.rootObject.resources[0].getMethod(HttpMethod.GET)
        method.queryParameters[0].type.annotations.size() == 0
        method.queryParameters[0].annotations.size() == 0

        method.queryParameters[1].type.annotations.size() == 0
        method.queryParameters[1].annotations.size() == 1
        method.queryParameters[1].getAnnotation("placeholderParam") != null
    }

    def "read-uri-annotation-value"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''
        #%RAML 1.0
        title: Test
        annotationTypes:
            testUri:
                type: string
        /{projectKey}:
            (testUri): /whatever
            uriParameters:
               projectKey:
                 type: string
            get:
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
        ramlModelResult.rootObject.resources[0].getAnnotation("testUri").value.value == "/whatever"
        ramlModelResult.rootObject.resources[0].getUriParameter("projectKey") != null
    }
}

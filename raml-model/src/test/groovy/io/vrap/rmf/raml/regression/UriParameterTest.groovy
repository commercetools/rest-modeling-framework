package io.vrap.rmf.raml.regression;

import io.vrap.rmf.raml.model.RamlModelResult;
import io.vrap.rmf.raml.model.modules.Api;

class UriParameterTest extends RegressionTest {
    def "baseuriparameter-with-invalid-type"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Test
        baseUri: http://{a}.myapi.org
        baseUriParameters:
            a:
                displayName: A
                description: This is A
                type: X
        ''')
        then:
        ramlModelResult.validationResults.size() == 1
        Api api = ramlModelResult.rootObject
        api.types.size() == 0
        api.baseUriParameters.size() == 1
        api.baseUriParameters.get(0).type != null
        api.baseUriParameters.get(0).type.type == null
    }

    def "simple-baseuri"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Test
        baseUri: http://example.com
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
        Api api = ramlModelResult.rootObject
        api.baseUri.expand() == "http://example.com"
    }

    def "baseuri-with-value"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Test
        baseUri:
            value: http://example.com
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
        Api api = ramlModelResult.rootObject
        api.baseUri.expand() == "http://example.com"
    }

    def "baseuri-with-annotation"() {
        when:
        RamlModelResult<Api> ramlModelResult = constructApi(
                '''\
        #%RAML 1.0
        title: Test
        annotationTypes:
            condition : string

        baseUri:
            (condition): test
            value: http://example.com
        ''')
        then:
        ramlModelResult.validationResults.size() == 0
        Api api = ramlModelResult.rootObject
        api.baseUri.expand() == "http://example.com"
        api.baseUri.getAnnotation('condition').value.value == 'test'
    }
}

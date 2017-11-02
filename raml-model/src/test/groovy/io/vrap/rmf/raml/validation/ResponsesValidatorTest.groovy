package io.vrap.rmf.raml.validation

import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.modules.ModulesFactory
import io.vrap.rmf.raml.model.resources.Method
import io.vrap.rmf.raml.model.resources.Resource
import io.vrap.rmf.raml.model.resources.ResourcesFactory
import io.vrap.rmf.raml.model.responses.BodyType
import io.vrap.rmf.raml.model.responses.ResponsesFactory
import io.vrap.rmf.raml.model.util.BaseValidatorTest
import org.eclipse.emf.common.util.Diagnostic

/**
 * Unit tests for {@link io.vrap.rmf.raml.model.responses.util.ResponsesValidator}
 */
class ResponsesValidatorTest extends BaseValidatorTest {
    Api api
    BodyType body

    def setup() {
        api = ModulesFactory.eINSTANCE.createApi()
        api.title = 'Test Api'
        Resource resource = ResourcesFactory.eINSTANCE.createResource()
        api.resources.add(resource)
        Method method = ResourcesFactory.eINSTANCE.createMethod()
        resource.methods.add(method)
        body = ResponsesFactory.eINSTANCE.createBodyType()
        method.bodies.add(body)
    }

    def "should report missing content types when no default media types are define"() {
        expect:
        validate(api) == false
        diagnostic.severity != Diagnostic.OK
    }

    def "should accept bodies with no content type when default media types are defined"() {
        when:
        api.mediaType.add('application/json')
        then:
        validate(api) == true
        diagnostic.severity == Diagnostic.OK
    }

    def "should accept bodies with content type"() {
        when:
        body.contentTypes.add('application/json')
        then:
        validate(api) == true
        diagnostic.severity == Diagnostic.OK
    }
}

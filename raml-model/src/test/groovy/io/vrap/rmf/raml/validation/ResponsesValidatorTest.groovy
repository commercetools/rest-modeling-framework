package io.vrap.rmf.raml.validation

import com.google.common.net.MediaType
import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.modules.ModulesFactory
import io.vrap.rmf.raml.model.resources.Method
import io.vrap.rmf.raml.model.resources.Resource
import io.vrap.rmf.raml.model.resources.ResourcesFactory
import io.vrap.rmf.raml.model.responses.Body
import io.vrap.rmf.raml.model.responses.ResponsesFactory
import org.eclipse.emf.common.util.Diagnostic

/**
 * Unit tests for {@link io.vrap.rmf.raml.model.responses.util.ResponsesValidator}
 */
class ResponsesValidatorTest extends BaseValidatorTest {
    Api api
    Body body

    def setup() {
        api = ModulesFactory.eINSTANCE.createApi()
        api.title = 'Test Api'
        Resource resource = ResourcesFactory.eINSTANCE.createResource()
        api.resources.add(resource)
        Method method = ResourcesFactory.eINSTANCE.createMethod()
        resource.methods.add(method)
        body = ResponsesFactory.eINSTANCE.createBody()
        method.bodies.add(body)
    }

    def "should report missing content types when no default media types are define"() {
        expect:
        validate(api) == false
        diagnostic.severity != Diagnostic.OK
    }

    def "should accept bodies with no content type when default media types are defined"() {
        when:
        api.mediaType.add(MediaType.parse('application/json'))
        then:
        validate(api) == true
        diagnostic.severity == Diagnostic.OK
    }

    def "should accept bodies with content type"() {
        when:
        body.contentTypes.add(MediaType.parse('application/json'))
        then:
        validate(api) == true
        diagnostic.severity == Diagnostic.OK
    }
}

package io.vrap.rmf.raml.validation

import com.damnhandy.uri.template.UriTemplate
import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.model.modules.ModulesFactory
import io.vrap.rmf.raml.model.resources.Resource
import io.vrap.rmf.raml.model.resources.ResourcesFactory
import org.eclipse.emf.common.util.Diagnostic

/**
 * Unit tests for
 */
class ModulesValidatorTest extends BaseValidatorTest {
    Api api

    def setup() {
        api = ModulesFactory.eINSTANCE.createApi()
        api.title = 'Test Api'
    }

    def "should report missing title"() {
        when:
        api.title = title
        then:
        validate(api) == false
        where:
        title << [ null, '']
    }

    def "should report invalid protocols"() {
        when:
        api.protocols.add('ftp')
        then:
        validate(api) == false
        diagnostic.severity != Diagnostic.OK
    }

    def "should accept valid protocols"() {
        when:
        api.protocols.add(protocol)
        then:
        validate(api) == true
        diagnostic.severity == Diagnostic.OK
        where:
        protocol << ['http', 'HTTP', 'https', 'HTTPS']
    }

    def "should report duplicate resources"() {
        when:
        api.resources.add(resource('/resource1'))
        api.resources.add(resource('/resource1'))
        then:
        validate(api) == false
        diagnostic.children.size() == 1
    }

    def "should report duplicate nested resources"() {
        when:
        api.resources.add(resource('/resource1', resource('/resource2')))
        api.resources.add(resource('/resource1/resource2'))
        then:
        validate(api) == false
        diagnostic.children.size() == 1
    }

    Resource resource(String relativeUri, Resource child = null) {
        Resource resource = ResourcesFactory.eINSTANCE.createResource()
        resource.setRelativeUri(UriTemplate.fromTemplate(relativeUri))
        if (child != null) {
            resource.resources.add(child)
        }
        resource
    }
}

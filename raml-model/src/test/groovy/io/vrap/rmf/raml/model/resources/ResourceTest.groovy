package io.vrap.rmf.raml.model.resources

import com.damnhandy.uri.template.UriTemplate
import spock.lang.Specification

/**
 * Unit tests for {@link Resource}
 */
class ResourceTest extends Specification {
    Resource resource

    def setup() {
        resource = ResourcesFactory.eINSTANCE.createResource()
    }

    def "getResourcePathName"() {
        when:
        resource.relativeUri = UriTemplate.fromTemplate(relativeUri)
        then:
        resource.resourcePathName == resourcePathName
        where:
        relativeUri         || resourcePathName
        '/resource'         || 'resource'
        '/resource/{ID}'    || 'resource'
        '/resource/id={ID}' || 'resource'
        '/resource/child'   || 'child'
    }
}

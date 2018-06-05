package io.vrap.rmf.raml.model.resources

import com.damnhandy.uri.template.UriTemplate
import spock.lang.Specification

/**
 * Unit tests for {@link Resource}
 */
class ResourceTest extends Specification {
    Resource parentResource
    Resource resource

    def setup() {
        parentResource = ResourcesFactory.eINSTANCE.createResource()
        resource = ResourcesFactory.eINSTANCE.createResource()
        parentResource.resources.add(resource)
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

    def "getResourcePath"() {
        when:
        parentResource.relativeUri = UriTemplate.fromTemplate(parentUri)
        resource.relativeUri = UriTemplate.fromTemplate(uri)
        then:
        resource.resourcePath == resourcePath
        where:
        parentUri   | uri     || resourcePath
        '/resource' | '/kind' || '/resource/kind'
        '/resource' | '/{ID}' || '/resource/{ID}'
    }
}

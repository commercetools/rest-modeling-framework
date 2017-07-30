package io.vrap.rmf.raml.persistence.constructor

import io.vrap.rmf.raml.model.resources.UriTemplate
import spock.lang.Specification

class UriTemplateConstructorTest extends Specification {

    def "simple uri template"() {
        when:
        UriTemplate uriTemplate = parse('http://localhost:8080/api/{version}');

        then:
        uriTemplate.parts.size() == 2
        uriTemplate.parts[0].literal == 'http://localhost:8080/api/'
        uriTemplate.parts[1].variables.size() == 1
        uriTemplate.parts[1].variables[0] == 'version'
    }

    UriTemplate parse(String input) {
        new UriTemplateConstructor().parse(input)
    }
}

package io.vrap.rmf.raml.model.util

import spock.lang.Specification

/**
 * Unit tests for {@link MediaType}.
 */
class MediaTypeTest extends Specification {

    def "parseAndToString"() {
        when:
        MediaType mediaType = MediaType.of(mediaTypeStr)
        then:
        mediaType.type == type
        mediaType.subtype == subtype
        mediaType.toString() == mediaTypeStr
        where:
        mediaTypeStr       || type          | subtype
        'application/json' || 'application' | 'json'
        'application/*'    || 'application' | '*'
        '*/*'              || '*'           | '*'
    }
}

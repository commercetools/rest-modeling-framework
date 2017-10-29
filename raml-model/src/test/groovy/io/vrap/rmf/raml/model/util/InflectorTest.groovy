package io.vrap.rmf.raml.model.util

import spock.lang.Specification

/**
 * Unit tests for {@link Inflector].
 */
class InflectorTest extends Specification {

    def "plural"() {
        expect:
        Inflector.plural('user') == 'users'
    }

    def "singular"() {
        expect:
        Inflector.singular('users') == 'user'
    }
}

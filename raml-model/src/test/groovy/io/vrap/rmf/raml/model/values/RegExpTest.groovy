package io.vrap.rmf.raml.model.values

import spock.lang.Specification

/**
 * Unit tests for {@link RegExp}.
 */
class RegExpTest extends Specification {

    def "factory method"() {
        when:
        RegExp regExp = RegExp.of('.*')
        then:
        regExp != null
    }

    def "toString() should return the pattern"() {
        when:
        RegExp regExp = RegExp.of('.*')
        then:
        regExp.toString() == '.*'
    }

    def "test should work as in JS"() {
        when:
        RegExp regExp = RegExp.of('.*')
        then:
        regExp.test('a') == true
    }

    def "test should work with empty pattern"() {
        when:
        RegExp regExp = RegExp.of('')
        then:
        regExp.test(value) == true
        where:
        value << [ '', 'a', "1"]
    }
}

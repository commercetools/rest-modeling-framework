package io.vrap.rmf.raml.model.util

import spock.lang.Specification

/**
 * Unit tests for {@link StringTemplate}.
 */
class StringTemplateTest extends Specification {

    def "toString() should return parsed string template"() {
        when:
        StringTemplate stringTemplate = StringTemplate.of(template);
        then:
        stringTemplate.toString() == template
        where:
        template << [ '<<test>><' ]
    }

    def "render(Map<String, String>)"() {
        when:
        StringTemplate stringTemplate = StringTemplate.of(template);
        then:
        stringTemplate.render(values) == result
        where:
        template                                    | values                       || result
        '<<resourcePathName>>Draft'                 | ['resourcePathName': 'User'] || 'UserDraft'
        '<<resourcePathName|!uppercamelcase>>Draft' | ['resourcePathName': 'user'] || 'UserDraft'
        'Name<<name>>'                              | ['name': 'Hello']            || 'NameHello'
        'Name<<name>>Impl'                          | ['name': 'Hello']            || 'NameHelloImpl'
    }

    def "getParameters()"() {
        when:
        StringTemplate stringTemplate = StringTemplate.of(template);
        then:
        stringTemplate.getParameters() == parameters.toSet()
        where:
        template                              || parameters
        '<<resourcePathName>>Draft'           || ['resourcePathName']
        '<<resourcePathName>>Draft<<Suffix>>' || ['resourcePathName', 'Suffix']
    }
}

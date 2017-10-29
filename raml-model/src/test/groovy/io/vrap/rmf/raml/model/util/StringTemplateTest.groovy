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
        template << [ '<<resourcePathName>>Draft', 'Name<<name>>', 'Name<<name>>Impl', '<<t>><<fff', '<<<<t>>', '201<<t>>' ]
    }

    def "render(Map<String, String>)"() {
        when:
        StringTemplate stringTemplate = StringTemplate.of(template);
        then:
        stringTemplate.render(values) == result
        where:
        template                                      | values                       || result
        '<<resourcePathName>>Draft'                   | ['resourcePathName': 'User'] || 'UserDraft'
        'Name<<name>>'                                | ['name': 'Hello']            || 'NameHello'
        'Name<<name>>Impl'                            | ['name': 'Hello']            || 'NameHelloImpl'
        '<<t>><<fff'                                  | ['t': 'T']                   || 'T<<fff'
        '<<t>>>>fff'                                  | ['t': 'T']                   || 'T>>fff'
        '<<<<t>>'                                     | ['t': 'T']                   || '<<T'
        '201<<t>>'                                    | ['t': 'T']                   || '201T'
    }

    def "render(Map<String, String>) with transformations"() {
        when:
        StringTemplate stringTemplate = StringTemplate.of(template);
        then:
        stringTemplate.render(values) == result
        where:
        template                                      | values                       || result
        '<<name|!lowercamelcase>>'                    | ['name': 'USER-ID']          || 'userId'
        '<<name|!uppercamelcase>>'                    | ['name': 'userId']           || 'UserId'
        '<<name|!lowerhyphencase>>'                   | ['name': 'USER-ID']          || 'user-id'
        '<<name|!upperhyphencase>>'                   | ['name': 'userId']           || 'USER-ID'
        '<<name|!lowerunderscorecase>>'               | ['name': 'USER-ID']          || 'user_id'
        '<<name|!upperunderscorecase>>'               | ['name': 'userId']           || 'USER_ID'
        '<<name|!lowercase>>'                         | ['name': 'USER-ID']          || 'user-id'
        '<<name|!uppercase>>'                         | ['name': 'user-id']          || 'USER-ID'
        '<<name|!pluralize>>'                         | ['name': 'user']             || 'users'
        '<<name|!singularize>>'                       | ['name': 'users']            || 'user'
        '<<resourcePathName|!uppercamelcase>>Draft'   | ['resourcePathName': 'user'] || 'UserDraft'
        '<<resourcePathName|!uppercamelcase>> Draft'  | ['resourcePathName': 'user'] || 'User Draft'
        '<<resourcePathName|!uppercamelcase>>Draft'   | ['resourcePathName': 'user'] || 'UserDraft'
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

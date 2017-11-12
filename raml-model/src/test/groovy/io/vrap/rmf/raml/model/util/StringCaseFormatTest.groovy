package io.vrap.rmf.raml.model.util

import spock.lang.Specification

/**
 * Unit tests for {@link StringCaseFormat}.
 */
class StringCaseFormatTest extends Specification {

    def "stringClassification"() {
        expect:
        format.test(value) == result
        where:
        format                                 | value   || result
        StringCaseFormat.LOWER_CAMEL_CASE      | 'aBcD'  || true
        StringCaseFormat.LOWER_CAMEL_CASE      | 'aBCDEf'  || true
        StringCaseFormat.UPPER_CAMEL_CASE      | 'ABCDef'  || true
        StringCaseFormat.UPPER_CAMEL_CASE      | 'AbcD'  || true
        StringCaseFormat.LOWER_HYPHEN_CASE     | 'ab-cd' || true
        StringCaseFormat.LOWER_HYPHEN_CASE     | '-cd'   || false
        StringCaseFormat.LOWER_UNDERSCORE_CASE | 'ab_cd' || true
    }

    def "compoundWords"() {
        expect:
        format.compoundWords(value) == result
        where:
        format                                 | value   || result
        StringCaseFormat.LOWER_CAMEL_CASE      | ''      || ['']
        StringCaseFormat.LOWER_CAMEL_CASE      | 'ABCD'  || ['ABCD']
        StringCaseFormat.LOWER_CAMEL_CASE      | 'aBcD'  || ['a', 'Bc', 'D']
        StringCaseFormat.LOWER_CAMEL_CASE      | 'aBcd'  || ['a', 'Bcd']
        StringCaseFormat.LOWER_CAMEL_CASE      | 'abcd'  || ['abcd']
        StringCaseFormat.LOWER_CAMEL_CASE      | 'aBCDEf' || ['a', 'BCD', 'Ef']
        StringCaseFormat.UPPER_CAMEL_CASE      | 'ABCDef'  || ['ABC', 'Def']
        StringCaseFormat.LOWER_HYPHEN_CASE     | 'ab-cd' || ['ab', 'cd']
        StringCaseFormat.LOWER_UNDERSCORE_CASE | 'ab_cd' || ['ab', 'cd']
    }

    def "render"() {
        expect:
        format.render(compoundWords) == result
        where:
        format                                 | compoundWords || result
        StringCaseFormat.LOWER_CAMEL_CASE      | ['Ab', 'Bd']  || 'abBd'
        StringCaseFormat.UPPER_CAMEL_CASE      | ['ab', 'Bd']  || 'AbBd'
        StringCaseFormat.LOWER_UNDERSCORE_CASE | ['Ab', 'Bd']  || 'ab_bd'
        StringCaseFormat.UPPER_UNDERSCORE_CASE | ['ab', 'Bd']  || 'AB_BD'
        StringCaseFormat.LOWER_UNDERSCORE_CASE | ['']          || ''
        StringCaseFormat.LOWER_HYPHEN_CASE     | ['']          || ''
        StringCaseFormat.LOWER_HYPHEN_CASE     | ['Ab', 'Bd']  || 'ab-bd'
        StringCaseFormat.UPPER_HYPHEN_CASE     | ['ab', 'Bd']  || 'AB-BD'
        StringCaseFormat.UPPER_HYPHEN_CASE     | ['']          || ''
        StringCaseFormat.UPPER_UNDERSCORE_CASE | ['']          || ''

    }

    def "apply"() {
        expect:
        format.apply(value) == result
        where:
        format                                 | value      || result
        StringCaseFormat.LOWER_CAMEL_CASE      | ''         || ''
        StringCaseFormat.LOWER_CAMEL_CASE      | 'ab-cd'    || 'abCd'
        StringCaseFormat.LOWER_CAMEL_CASE      | 'AB-CD'    || 'abCd'
        StringCaseFormat.UPPER_UNDERSCORE_CASE | 'ab-cd'    || 'AB_CD'
        StringCaseFormat.UPPER_HYPHEN_CASE     | 'ab-cd'    || 'AB-CD'
        StringCaseFormat.LOWER_HYPHEN_CASE     | 'aBCDEf'   || 'a-bcd-ef'
        StringCaseFormat.LOWER_HYPHEN_CASE     | 'ABCDef'   || 'abc-def'
        StringCaseFormat.LOWER_CAMEL_CASE      | 'ABCDef'   || 'abcDef'
        StringCaseFormat.UPPER_CAMEL_CASE      | 'ABCDef'   || 'AbcDef'
        StringCaseFormat.LOWER_CAMEL_CASE      | 'ABCdEf'   || 'abCdEf'
        StringCaseFormat.UPPER_CAMEL_CASE      | 'ABCdEf'   || 'AbCdEf'
        StringCaseFormat.LOWER_CAMEL_CASE      | 'aBCDEf'   || 'aBcdEf'
        StringCaseFormat.UPPER_CAMEL_CASE      | 'aBCDEf'   || 'ABcdEf'
        StringCaseFormat.LOWER_HYPHEN_CASE     | 'aBc-De_f' || 'a-bc-de-f'
        StringCaseFormat.UPPER_UNDERSCORE_CASE | 'aBc-De_f' || 'A_BC_DE_F'
        StringCaseFormat.LOWER_CAMEL_CASE      | 'aBc-De_F'  || 'aBcDeF'
        StringCaseFormat.UPPER_CAMEL_CASE      | 'aBc-De_F'  || 'ABcDeF'
    }
}

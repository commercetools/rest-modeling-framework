grammar MediaType;

@header {
package io.vrap.rmf.raml.persistence.antlr;
}

mediaType:
    type = name '/' subtype = name;

/**
 * This rule allows to use a wildcard, which isn'n allowed by the RFC.
 */
name:
    '*' | restricted_name;

/**
 * This rule conforms to RFC 6836, section 4.2
 */
restricted_name:
    (ALPHA|DIGIT) (ALPHA|DIGIT|'!'|'#'|'$'|'&'|'-'|'^'|'_'|'.'|'+')*;

ALPHA: 'a'..'z'|'A'..'Z';
DIGIT: '0'..'9';
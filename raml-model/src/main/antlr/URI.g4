grammar URI;


@header {
package io.vrap.rmf.raml.persistence.antlr;
}

uriTemplate:
    parts += uriTemplatePart*
    ;


uriTemplatePart:
        literal
    |   expression
    ;

literal:
    LITERAL
    ;

expression:
    '{' (operator=OPERATOR? variables+=VAR (',' variables +=  VAR)*)? '}'
    ;

VAR:
    VARCHAR+
    ;

LITERAL:
    ( VARCHAR | '/' | ':' | '.' )+
    ;

OPERATOR:
        '+'
    |   '#'
    ;

fragment VARCHAR:
        ALPHA
    |   DIGIT
    |   '_'
    ;

fragment ALPHA:
    [a-zA-Z]
    ;

fragment DIGIT:
    [0-9]
    ;
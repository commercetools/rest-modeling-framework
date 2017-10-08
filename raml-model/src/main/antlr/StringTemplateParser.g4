parser grammar StringTemplateParser;

options {
    tokenVocab=StringTemplateLexer;
}

@header {
package io.vrap.rmf.raml.persistence.antlr;
}


stringTemplate:
    (literal | expression)+
    ;

literal:
    TEXT
    ;
expression:
    OPEN ID fnApplication* CLOSE
    ;

fnApplication:
    SEPARATOR FN fn=ID
    ;
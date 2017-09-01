grammar StringTemplate;

@header {
package io.vrap.rmf.raml.persistence.antlr;
}


stringTemplate:
    (literal | expression)+
    ;

literal:
    ID
    ;
expression:
    '<<' ID fnApplication* '>>'
    ;

fnApplication:
    '|' '!' fn=ID
    ;

ID : [a-zA-Z_]+ [a-zA-Z0-9_-]* ;
WS : [ \t\n\r]+ -> channel(HIDDEN) ;
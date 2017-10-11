grammar StringTemplate;

@header {
package io.vrap.rmf.raml.persistence.antlr;
}

stringTemplate:
    (expression | literal)*
    ;

literal:
    .+?
    ;

expression:
    '<<' ID fnApplication* WS* '>>'
    ;

fnApplication:
    WS* '|' WS* '!' fn=ID
    ;

ID : [a-zA-Z_]+ [a-zA-Z0-9_-]* ;
WS : [ \t\n\r]+;
ANY: .; // catch all so that literal works, must be the last terminal

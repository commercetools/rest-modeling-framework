lexer grammar StringTemplateLexer;

@header {
package io.vrap.rmf.raml.persistence.antlr;
}

OPEN: '<<' -> mode(ISLAND);
TEXT: '<' ~'<'*
    | ~'<'+;


mode ISLAND;
ID : [a-zA-Z_]+ [a-zA-Z0-9_-]* ;
SEPARATOR: '|';
FN: '!';
CLOSE: '>>' -> mode(DEFAULT_MODE);
WS : [ \t\n\r]+ -> channel(HIDDEN) ;

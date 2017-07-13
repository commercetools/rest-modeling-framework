grammar TypeExpression;

@header {
package io.vrap.rmf.raml.persistence.antlr;
}

type_expr:
    type_reference |
    type_expr ('[]')+ |
    type_expr ('|' type_expr)+ |
    '(' type_expr ')' ;

type_reference:
    ID;

ID : [a-zA-Z_]+ [a-zA-Z0-9_]* ;
WS : [ \t\n\r]+ -> channel(HIDDEN) ;
grammar TypeExpression;

@header {
package io.vrap.rmf.raml.persistence.antlr;
}

type_expr:
    ID                           # TypeReference
    | type_expr '[]'             # ArrayType
    | type_expr '|' type_expr    # UnionType
    | '(' type_expr ')'          # Parens
    ;

ID : [a-zA-Z_]+ [a-zA-Z0-9_]* ;
WS : [ \t\n\r]+ -> channel(HIDDEN) ;
grammar TypeExpression;

@header {
package io.vrap.rmf.raml.persistence.antlr;
}

type_expr:
    primary_type_expr
    | union_type_expr
    | intersection_type_expr
    ;


intersection_type_expr:
    '[' (primary_type_expr | union_type_expr) (',' (primary_type_expr | union_type_expr))+ ']' # IntersectionType
    ;

union_type_expr:
    primary_type_expr ('|' primary_type_expr)+ # UnionType
    ;

primary_type_expr:
    qualified_name              # TypeReference
    | stringTemplate            # TypeTemplate
    | '(' type_expr ')'         # Parens
    | primary_type_expr '[]'    # ArrayType
    ;

qualified_name:
    ID ('.' ID)?;

stringTemplate:
    (ID | ('<<' ID fnApplication* '>>'))+
    ;

fnApplication:
    '|' '!' fn=ID
    ;

ID : [a-zA-Z_]+ [a-zA-Z0-9_-]* ;
WS : [ \t\n\r]+ -> channel(HIDDEN) ;
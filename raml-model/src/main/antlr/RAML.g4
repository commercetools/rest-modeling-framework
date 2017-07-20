grammar RAML;


@header {
package io.vrap.rmf.raml.persistence.antlr;
}


tokens {
    MAP_START, MAP_END, LIST_START, LIST_END, SCALAR
}

api:
    MAP_START
    ( attributeFacet | typesFacet )*
    MAP_END;

attributeFacet:
    facet=SCALAR value=facetValue;

facetValue:
        value=SCALAR
    |   (LIST_START values+=SCALAR* LIST_END)
    ;

library:
    MAP_START
    ( attributeFacet | typesFacet )*
    MAP_END
    ;

typesFacet:
    facet=( 'types' | 'annotationTypes' )
        MAP_START
        ( types+=typeDeclaration )*
        MAP_END
    ;

typeDeclaration:
    name=SCALAR
    (
        MAP_START
        ( attributeFacet | propertiesFacet | typeFacet )*
        MAP_END
    )?
    ;

typeFacet:
    facet=( 'type' | 'items') typeExpression=SCALAR
    ;

propertiesFacet:
    facet='properties'
        MAP_START
        ( properties+=property )*
        MAP_END
    ;

property:
    name=SCALAR
    (
        MAP_START
        ( attributeFacet | typeFacet )*
        MAP_END
    )?
    ;

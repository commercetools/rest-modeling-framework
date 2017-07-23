grammar RAML;


@header {
package io.vrap.rmf.raml.persistence.antlr;
}


tokens {
    MAP_START, MAP_END, LIST_START, LIST_END, SCALAR,
    ANNOTATION_TYPE_REF
}

api:
    MAP_START
    ( attributeFacet | typesFacet | annotationFacet )*
    MAP_END;

attributeFacet:
    facet=SCALAR value=facetValue;

facetValue:
        value=SCALAR
    |   (LIST_START values+=SCALAR* LIST_END)
    ;

library:
    MAP_START
    ( attributeFacet | typesFacet | annotationFacet )*
    MAP_END
    ;

typesFacet:
    facet=( 'types' | 'annotationTypes' )
        MAP_START
        ( types+=typeDeclarationFacet )*
        MAP_END
    ;

typeDeclarationFacet:
    typeDeclarationTuple | typeDeclarationMap
    ;

typeDeclarationTuple:
    name=SCALAR typeExpression=SCALAR;

typeDeclarationMap:
    name=SCALAR
        MAP_START
        ( attributeFacet | propertiesFacet | typeFacet | annotationFacet )*
        MAP_END
    ;

typeDeclarationFragment:
    MAP_START
    ( attributeFacet | propertiesFacet | typeFacet | annotationFacet )*
    MAP_END
    ;

typeFacet:
    facet=( 'type' | 'items') typeExpression=SCALAR
    ;

propertiesFacet:
    facet='properties'
        MAP_START
        ( propertyFacets+=propertyFacet )*
        MAP_END
    ;

propertyFacet:
    propertyTuple | propertyMap
    ;

propertyTuple:
    name=id type=SCALAR
    ;

propertyMap:
    name=id
        MAP_START
        ( attributeFacet | propertiesFacet | requiredFacet | typeFacet | annotationFacet )*
        MAP_END
    ;

id:
        'required'
    |   SCALAR
    ;

requiredFacet:
    'required' required=SCALAR
    ;

annotationFacet:
        annotationTuple
    |   annotationMap
    ;

annotationTuple:
    type=ANNOTATION_TYPE_REF value=SCALAR
    ;

annotationMap:
    type=ANNOTATION_TYPE_REF
        MAP_START
            propertyValues += attributeFacet*
        MAP_END
    ;

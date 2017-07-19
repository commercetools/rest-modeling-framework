grammar RAML;


@header {
package io.vrap.rmf.raml.persistence.antlr;
}


tokens {
    MAP_START, MAP_END, LIST_START, LIST_END, SCALAR
}

api:
    MAP_START
    ( apiFacet )*
    MAP_END;

apiFacet:
    facet=('title' | 'description' | 'version' | 'baseUri' |'protocols' | 'mediaType') value=facetValue
    ;

facetValue:
        value=SCALAR
    |   (LIST_START values+=SCALAR* LIST_END)
    ;

library:
    MAP_START
    (
            libraryFacet
        |   typeDeclarations
    )*
    MAP_END
    ;

libraryFacet:
    facet='usage' value=SCALAR
    ;

typeDeclarations:
    facet='types'
        MAP_START
        ( types+=typeDeclaration )*
        MAP_END
    ;

typeDeclaration:
    name=SCALAR
    (
        MAP_START
        ( typeDeclarationFacet | propertiesFacet | typeFacet )*
        MAP_END
    )?
    ;

typeDeclarationFacet:
    facet=(
            'displayName' | 'description' | 'default' | 'enum' |
            'pattern' |
            'minLength' | 'maxLength'  |
            'fileTypes' |
            'minimum' | 'maximum' | 'format' | 'multipleOf' |
            'minProperties' | 'maxProperties' | 'additionalProperties' | 'discriminator' | 'discriminatorValue' |
            'uniqueItems' | 'minItems' | 'maxItems'
          )
    value=facetValue
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
        ( propertyFacet | typeDeclarationFacet | typeFacet )*
        MAP_END
    )?
    ;

propertyFacet:
    facet='required' value=SCALAR
    ;
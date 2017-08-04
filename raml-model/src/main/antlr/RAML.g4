grammar RAML;


@header {
package io.vrap.rmf.raml.persistence.antlr;
}


tokens {
    MAP_START, MAP_END, LIST_START, LIST_END, SCALAR,
    ANNOTATION_TYPE_REF, RELATIVE_URI, MEDIA_TYPE
}

api:
    MAP_START
    (
        usesFacet | baseUriFacet | baseUriParametersFacet | resourceFacet
        | securitySchemesFacet | securedByFacet
        | resourceTypesFacet | attributeFacet
        | typesFacet | traitsFacet | annotationFacet )*
    MAP_END;

traitsFacet:
    'traits'
        (
            SCALAR
            |   (
                    MAP_START
                    ( traitFacet )*
                    MAP_END
                )
        )
    ;

traitFacet:
    name=SCALAR
        (
            SCALAR
            |   (
                    MAP_START
                    (
                        bodyFacet | attributeFacet | headersFacet | queryParametersFacet | annotationFacet
                        | responsesFacet | securedByFacet
                    )*
                    MAP_END
                )
        )
    ;

resourceFacet:
    relativeUri=RELATIVE_URI
        (
            SCALAR
            |   (
                    MAP_START
                    (
                        resourceFacet | methodFacet | attributeFacet | uriParametersFacet | annotationFacet
                        | securedByFacet
                    )*
                    MAP_END
                )
        )
    ;

methodFacet:
    httpMethod
        (
            SCALAR
            |   (
                    MAP_START
                    (
                        bodyFacet | attributeFacet | headersFacet | queryParametersFacet | annotationFacet
                        | responsesFacet | securedByFacet
                    )*
                    MAP_END
                )
        )
    ;

bodyFacet:
    'body'
        (
            SCALAR
            |   (
                    MAP_START
                    (bodyContentTypeFacet* | bodyTypeFacet?)
                    MAP_END
                )
        )
    ;

bodyContentTypeFacet:
    contentType=SCALAR
        MAP_START
        ( propertiesFacet | typeFacet | annotationFacet | attributeFacet )*
        MAP_END
    ;

bodyTypeFacet:
    ( propertiesFacet | typeFacet | annotationFacet | attributeFacet )*
    ;

responsesFacet:
    'responses'
            (
                SCALAR
                |   (
                        MAP_START
                        ( responseFacet )*
                        MAP_END
                    )
            )
    ;

responseFacet:
    statusCode=SCALAR
            (
                SCALAR
                |   (
                        MAP_START
                        ( headersFacet | bodyFacet | attributeFacet )*
                        MAP_END
                    )
            )
    ;

httpMethod:
    'get' | 'patch' | 'put' | 'post' | 'delete' | 'head' | 'options'
    ;

headersFacet:
    'headers'
        (
            SCALAR
            |   (
                    MAP_START
                    ( headerFacets+=typedElementFacet )*
                    MAP_END
                )
        )
    ;

queryParametersFacet:
    'queryParameters'
        (
            SCALAR
            |   (
                    MAP_START
                    ( queryParameters+=typedElementFacet )*
                    MAP_END
                )
        )
    ;

uriParametersFacet:
    'uriParameters'
        (
            SCALAR
            |   (
                    MAP_START
                    ( uriParameterFacets+=typedElementFacet )*
                    MAP_END
                )
        )
    ;

baseUriFacet:
    'baseUri' baseUri=SCALAR
    ;

baseUriParametersFacet:
    'baseUriParameters'
        (
            SCALAR
            |   (
                    MAP_START
                    ( uriParameterFacets+=typedElementFacet )*
                    MAP_END
                )
        )
    ;

securitySchemesFacet:
    'securitySchemes'
        MAP_START
            securitySchemeFacet*
        MAP_END
    ;

securitySchemeFacet:
    name=id
    (
        MAP_START
            ( securitySchemeTypeFacet | securitySchemeSettingsFacet | attributeFacet )*
        MAP_END
    )?
    ;

securitySchemeTypeFacet:
    'type' type=SCALAR
    ;

securitySchemeSettingsFacet:
    'settings'
        (
            SCALAR
            |   (
                    MAP_START
                    securitySchemeSettingsFacets+=attributeFacet*
                    MAP_END
                )
        )
    ;

securedByFacet:
    'securedBy'
        LIST_START
            securitySchemes+=SCALAR*
        LIST_END
    ;

resourceTypesFacet:
    'resourceTypes'
        MAP_START
            SCALAR*
        MAP_END
    ;

attributeFacet:
    facet=SCALAR value=facetValue;

facetValue:
        value=id
    |   (LIST_START values+=id* LIST_END)
    ;

library:
    MAP_START
    ( usesFacet | attributeFacet | typesFacet | annotationFacet | securitySchemesFacet )*
    MAP_END
    ;

usesFacet:
    'uses'
        MAP_START
            ( libraryUse )*
        MAP_END
    ;

libraryUse:
    name=SCALAR libraryUri=SCALAR
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
        (
            SCALAR
            |   (
                    MAP_START
                    ( propertyFacets+=typedElementFacet )*
                    MAP_END
                )
        )
    ;

typedElementFacet:
    typedElementTuple | typedElementMap
    ;

typedElementTuple:
    name=id type=SCALAR
    ;

typedElementMap:
    name=id
        MAP_START
        ( attributeFacet | propertiesFacet | requiredFacet | typeFacet | annotationFacet )*
        MAP_END
    ;

id:
        'annotationTypes'
    |   'body'
    |   'baseUri' | 'baseUriParameters'
    |   'get' | 'patch' | 'put' | 'post' | 'delete' | 'head' | 'options'
    |   'headers'
    |   'items'
    |   'properties'
    |   'queryParameters'
    |   'required' | 'resourceTypes'
    |   'type' | 'types'
    |   'uses' | 'uriParameters'
    |   'responses'
    |   'securedBy' | 'securitySchemes' | 'settings'
    |   'traits'
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

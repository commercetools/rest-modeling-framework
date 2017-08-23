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
        usesFacet | baseUriFacet | baseUriParametersFacet | documentationFacet
        | resourceFacet
        | securitySchemesFacet | securedByFacet
        | resourceTypesFacet | attributeFacet
        | typesFacet | traitsFacet | annotationFacet )*
    MAP_END;

documentationFacet:
    'documentation'
        LIST_START
        ( MAP_START document MAP_END )*
        LIST_END
    ;

document:
    ( attributeFacet )+
    ;

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
                        bodyFacet | attributeFacet
                        | headersFacet | queryParametersFacet
                        | isFacet
                        | annotationFacet
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
                        | securedByFacet | resourceTypeFacet | isFacet
                    )*
                    MAP_END
                )
        )
    ;

resourceTypeFacet:
    'type' resourceTypeApplication
    ;


resourceTypeApplication:
    type=id |
    (
        MAP_START
            type=id
            (
                SCALAR |
                (
                    MAP_START
                    argument*
                    MAP_END
                )
            )
        MAP_END
    )
    ;

resourceTypeDeclarationFacet:
    name=id
        (
            SCALAR
            |   (
                    MAP_START
                    (
                        methodFacet | attributeFacet | uriParametersFacet | annotationFacet
                        | securedByFacet | resourceTypeFacet
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
                        bodyFacet | attributeFacet
                        | headersFacet | queryParametersFacet
                        | isFacet
                        | annotationFacet
                        | responsesFacet | securedByFacet
                    )*
                    MAP_END
                )
        )
    ;

isFacet:
    'is'
    (LIST_START traitApplication* LIST_END)?
    ;

traitApplication:
    trait=id |
    (
        MAP_START
            trait=id
            (
                SCALAR |
                (
                    MAP_START
                    argument*
                    MAP_END
                )
            )
        MAP_END
    )
    ;

argument:
    name=id value=instance
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
        (
         SCALAR
                    |   (
                            MAP_START
        ( attributeFacet | enumFacet | propertiesFacet | typeFacet | itemsFacet | defaultFacet | exampleFacet | examplesFacet | annotationFacet )*
        MAP_END
        )
        )
    ;

bodyTypeFacet:
    ( attributeFacet | enumFacet | propertiesFacet | typeFacet | itemsFacet | defaultFacet | exampleFacet | examplesFacet | annotationFacet )*
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
    'get' | 'patch' | 'put' | 'post' | 'delete' | 'head' | 'options' |
    'get?' | 'patch?' | 'put?' | 'post?' | 'delete?' | 'head?' | 'options?'
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
            ( securitySchemeTypeFacet | securitySchemeSettingsFacet | attributeFacet | describedByFacet )*
        MAP_END
    )?
    ;

describedByFacet:
    'describedBy'
        (
            SCALAR
            |   (
                    MAP_START
                    (queryParametersFacet | headersFacet | responsesFacet )*
                    MAP_END
                )
        )
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
            securedBy*
        LIST_END
    ;

securedBy:
    name=SCALAR |
        (   MAP_START
                name=SCALAR?
                ( SCALAR | parameters=objectInstance)?
            MAP_END
        )
    ;

resourceTypesFacet:
    'resourceTypes'
        MAP_START
            resourceTypeDeclarationFacet*
        MAP_END
    ;

attributeFacet:
    facet=SCALAR value=facetValue
    ;

facetValue:
        value=id
    |   (LIST_START values+=id* LIST_END)
    ;

library:
    MAP_START
    ( usesFacet | attributeFacet | typesFacet | traitsFacet | resourceTypesFacet | annotationFacet | securitySchemesFacet )*
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
        ( attributeFacet | enumFacet | propertiesFacet | typeFacet | itemsFacet | defaultFacet | exampleFacet | examplesFacet | annotationFacet )*
        MAP_END
    ;

enumFacet:
    'enum'
        LIST_START
        values+=instance*
        LIST_END
    ;

typeDeclarationFragment:
    MAP_START
    ( attributeFacet | enumFacet | propertiesFacet | typeFacet | itemsFacet| defaultFacet | exampleFacet | examplesFacet | annotationFacet )*
    MAP_END
    ;

typeFacet:
    'type' typeExpression=SCALAR
    ;

itemsFacet:
    'items' typeExpression=SCALAR
    ;

defaultFacet:
    'default' instance
    ;

exampleFacet:
    'example' instance
    ;


examplesFacet:
    'examples'
        (
            SCALAR
            |   (
                    MAP_START
                    namedExample*
                    MAP_END
                 )
        )
    ;

namedExample:
    name=id instance
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
        (
            SCALAR
            |   (
                    MAP_START
                    ( attributeFacet | enumFacet | propertiesFacet | requiredFacet | typeFacet | itemsFacet | annotationFacet | exampleFacet | examplesFacet | defaultFacet )*
                    MAP_END
                 )
        )
    ;

instance:
    simpleInstance | arrayInstance | objectInstance
    ;

simpleInstance:
    value=id
    ;

arrayInstance:
    LIST_START
    ( instance )*
    LIST_END
    ;

objectInstance:
    MAP_START
    ( instanceProperty )*
    MAP_END
    ;

instanceProperty:
    name=id value=instance
    ;

id:
        'annotationTypes'
    |   'body'
    |   'baseUri' | 'baseUriParameters'
    |   'get' | 'patch' | 'put' | 'post' | 'delete' | 'head' | 'options'
    |   'default' | 'describedBy' | 'documentation'
    |   'enum' | 'example' | 'examples'
    |   'headers'
    |   'items' | 'is'
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
    type=ANNOTATION_TYPE_REF value=instance
    ;

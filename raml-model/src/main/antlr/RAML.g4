grammar RAML;


@header {
package io.vrap.rmf.raml.persistence.antlr;
}


tokens {
    MAP_START, MAP_END, LIST_START, LIST_END, SCALAR,
    INT, FLOAT, BOOL,
    ANNOTATION_TYPE_REF, RELATIVE_URI, MEDIA_TYPE
}

api:
    MAP_START
        (apiFacets | typeContainerFacets)*
    MAP_END;

apiFacets:
      baseUriFacet
    | baseUriParametersFacet
    | documentationFacet
    | resourceFacet
    | securedByFacet
    ;

extension:
    MAP_START
        (extendsFacet | apiFacets | typeContainerFacets)*
    MAP_END;

extendsFacet:
    'extends' uri=SCALAR
    ;

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
                        | securedByFacet | resourceTypeFacet | isFacet
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
                    (bodyContentTypeFacet+ | bodyFacets)
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
                        bodyFacets
                    MAP_END
                )
        )
    ;

bodyFacets:
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
    statusCode=INT
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
    'baseUri' baseUri=annotatedStringInstance
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

displayNameFacet:
    'displayName' annotatedStringInstance
    ;

descriptionFacet:
    'description' annotatedStringInstance
    ;

strictFacet:
    'strict' annotatedBooleanInstance
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
    facet=('description' | 'displayName' | SCALAR ) value=facetValue
    ;

facetValue:
        value=anyValue
    |   (LIST_START values+=anyValue* LIST_END)
    ;

anyValue:
    id | BOOL | INT | FLOAT
    ;

library:
    MAP_START
        typeContainerFacets*
    MAP_END
    ;

typeContainerFacets:
      usesFacet
    | attributeFacet
    | annotationTypesFacet
    | typesFacet
    | traitsFacet
    | resourceTypesFacet
    | annotationFacet
    | securitySchemesFacet
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
    'types'
        MAP_START
        ( types+=typeDeclarationFacet )*
        MAP_END
    ;

annotationTypesFacet:
    'annotationTypes'
        MAP_START
        ( annotationTypes+=typeDeclarationFacet )*
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
    'items'
    (
        typeExpression=SCALAR |
        (
            MAP_START
            ( attributeFacet | enumFacet | propertiesFacet | typeFacet | itemsFacet | defaultFacet | exampleFacet | examplesFacet | annotationFacet )*
            MAP_END
        )
    )
    ;

defaultFacet:
    'default' instance
    ;

exampleFacet:
    'example' example
    ;

example:
    MAP_START
        (
            'value' value=baseInstance
            | strictFacet
            | displayNameFacet
            | descriptionFacet
            | annotationFacet
        )+
    MAP_END |
    value=baseInstance
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
    name=id example
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
    annotatedSimpleInstance | annotatedArrayInstance | annotatedObjectInstance | arrayInstance | objectInstance
    ;

baseInstance:
    simpleInstance | arrayInstance | objectInstance
    ;

simpleInstance:
    stringInstance | booleanInstance | integerInstance | numberInstance
    ;

annotatedSimpleInstance:
    annotatedStringInstance | annotatedBooleanInstance | annotatedIntegerInstance | annotatedNumberInstance
    ;

annotatedStringInstance:
    value=id |
    MAP_START
        (
            ('value' value=id)
            | annotationFacet
        )+
    MAP_END
    ;

annotatedBooleanInstance:
    value=BOOL|
    MAP_START
        (
            ('value' value=BOOL)
            | annotationFacet
        )+
    MAP_END
    ;

annotatedIntegerInstance:
    value=INT |
    MAP_START
        (
            ('value' value=INT)
            | annotationFacet
        )+
    MAP_END
    ;

annotatedNumberInstance:
    value=FLOAT
    MAP_START
        (
            ('value' value=FLOAT)
            | annotationFacet
        )+
    MAP_END
    ;

annotatedArrayInstance:
    MAP_START
        (
            'value' arrayInstance
            | annotationFacet
        )+
    MAP_END
    ;

annotatedObjectInstance:
    MAP_START
        (
            'value' objectInstance
            | annotationFacet
        )+
    MAP_END
    ;

stringInstance:
    value=id
    ;

booleanInstance:
    value=BOOL
    ;

integerInstance:
    value=INT
    ;

numberInstance:
    value=FLOAT
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
    name=id value=baseInstance
    ;

id:
        'annotationTypes'
    |   'body'
    |   'baseUri' | 'baseUriParameters'
    |   'get' | 'patch' | 'put' | 'post' | 'delete' | 'head' | 'options'
    |   'default' | 'describedBy' | 'documentation'
    |   'enum' | 'example' | 'examples' | 'extends'
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
    |   'value'
    |   'strict' | 'displayName' | 'description'
    |   SCALAR
    ;

requiredFacet:
    'required' required=BOOL
    ;

annotationFacet:
    type=ANNOTATION_TYPE_REF value=instance
    ;

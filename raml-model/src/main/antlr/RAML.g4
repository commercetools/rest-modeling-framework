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
    | mediaTypeFacet
    | securedByFacet
    ;

mediaTypeFacet:
    'mediaType'
    (
        types += id |
        LIST_START
            types +=id*
        LIST_END
    );

extension:
    MAP_START
        (extendsFacet | apiFacets | typeContainerFacets)*
    MAP_END;

extendsFacet:
    'extends' uri=id
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
            empty=id
            |   (
                    MAP_START
                    ( traitFacet )*
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
                empty=id |
                (
                    MAP_START
                    argument*
                    MAP_END
                )
            )
        MAP_END
    )
    ;

resourceFacet:
    relativeUri=RELATIVE_URI
    (
        empty=id |
        (
            MAP_START
                ( resourceBaseFacet | resourceFacet)*
            MAP_END
        )
    )
    ;

resourceTypeDeclarationFacet:
    name=id
    (
        empty=id |
        (
            MAP_START
                resourceBaseFacet*
            MAP_END
        )
    )
    ;

resourceBaseFacet:
    methodFacet
    | attributeFacet
    | descriptionFacet
    | displayNameFacet
    | uriParametersFacet
    | annotationFacet
    | securedByFacet
    | resourceTypeFacet
    | isFacet
    ;

methodFacet:
    httpMethod
    (
        empty=id |
        (
            MAP_START
                methodBaseFacet*
            MAP_END
        )
    );

traitFacet:
    name=id
    (
        empty=id |
        (
            MAP_START
                methodBaseFacet*
            MAP_END
        )
    );

methodBaseFacet:
    bodyFacet
    | descriptionFacet
    | displayNameFacet
    | attributeFacet
    | headersFacet
    | queryParametersFacet
    | isFacet
    | annotationFacet
    | responsesFacet
    | securedByFacet
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
                empty=id |
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
            empty=id
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
            empty=id
            |   (
                    MAP_START
                        bodyFacets
                    MAP_END
                )
        )
    ;

bodyFacets:
    (
        attributeFacet
        | descriptionFacet
        | enumFacet
        | propertiesFacet
        | typeFacet
        | itemsFacet
        | defaultFacet
        | exampleFacet
        | examplesFacet
        | annotationFacet
    )*
    ;

responsesFacet:
    'responses'
            (
                empty=id
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
                empty=id
                |   (
                        MAP_START
                        (
                            headersFacet
                            | bodyFacet
                            | descriptionFacet
                            | attributeFacet
                        )*
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
            empty=id
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
            empty=id
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
            empty=id
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
            empty=id
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
            (
                securitySchemeTypeFacet
                | securitySchemeSettingsFacet
                | descriptionFacet
                | displayNameFacet
                | attributeFacet
                | describedByFacet
            )*
        MAP_END
    )?
    ;

describedByFacet:
    'describedBy'
        (
            empty=id
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
    'type' type=id
    ;

securitySchemeSettingsFacet:
    'settings'
        (
            empty=id
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
    name=id |
        (   MAP_START
                name=id?
                ( empty=id | parameters=objectInstance)?
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
    | descriptionFacet
    | displayNameFacet
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
    name=id libraryUri=id
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
    name=id typeExpression;

typeDeclarationMap:
    name=id
        MAP_START
        (
            attributeFacet
            | descriptionFacet
            | displayNameFacet
            | enumFacet
            | propertiesFacet
            | typeFacet
            | itemsFacet
            | defaultFacet
            | exampleFacet
            | examplesFacet
            | annotationFacet
        )*
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
    (
        attributeFacet
        | enumFacet
        | descriptionFacet
        | displayNameFacet
        | propertiesFacet
        | typeFacet
        | itemsFacet
        | defaultFacet
        | exampleFacet
        | examplesFacet
        | annotationFacet
    )*
    MAP_END
    ;

typeFacet:
    'type' typeExpression
    ;

typeExpression:
    exprs+=id | (LIST_START exprs+=id+ LIST_END)
    ;

itemsFacet:
    'items'
    (
        typeExpression |
        (
            MAP_START
            (
                attributeFacet
                | enumFacet
                | descriptionFacet
                | displayNameFacet
                | propertiesFacet
                | typeFacet
                | itemsFacet
                | defaultFacet
                | exampleFacet
                | examplesFacet
                | annotationFacet
            )*
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
            empty=id
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
            empty=id
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
    name=id type=id
    ;

typedElementMap:
    name=id
        (
            empty=id
            |   (
                    MAP_START
                    (
                        attributeFacet
                        | descriptionFacet
                        | displayNameFacet
                        | enumFacet
                        | propertiesFacet
                        | requiredFacet
                        | typeFacet
                        | itemsFacet
                        | annotationFacet
                        | exampleFacet
                        | examplesFacet
                        | defaultFacet
                    )*
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
    relativeUriInstance | stringInstance | booleanInstance | integerInstance | numberInstance
    ;

annotatedSimpleInstance:
    annotatedStringInstance | annotatedBooleanInstance | annotatedIntegerInstance | annotatedNumberInstance
    ;

annotatedRelativeUriInstance:
    value=RELATIVE_URI |
    MAP_START
        (
            ('value' value=RELATIVE_URI)
            | annotationFacet
        )+
    MAP_END
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

relativeUriInstance:
    value=RELATIVE_URI
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
    |   'mediaType'
    |   'required' | 'resourceTypes'
    |   'type' | 'types'
    |   'uses' | 'uriParameters'
    |   'responses'
    |   'securedBy' | 'securitySchemes' | 'settings'
    |   'traits'
    |   'value'
    |   'strict' | 'displayName' | 'description'
    |   RELATIVE_URI
    |   SCALAR
    ;

requiredFacet:
    'required' required=BOOL
    ;

annotationFacet:
    type=ANNOTATION_TYPE_REF value=instance
    ;

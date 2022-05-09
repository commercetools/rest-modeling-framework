grammar OpenAPI31;


@header {
package com.commercetools.rmf.openapi.persistence.antlr;
}


tokens {
    MAP_START, MAP_END, LIST_START, LIST_END, SCALAR,
    INT, FLOAT, BOOL, NULL, RELATIVE_URI, MEDIA_TYPE
}

api:
    MAP_START
        (apiFacets | extensionFacets)*
    MAP_END;

apiFacets:
    openapiFacet
    | infoFacet
    | jsonSchemaDialectFacet
    | serversFacet
    | pathsFacet
    | webhooksFacet
    | componentsFacet
    | securityFacet
    | tagsFacet
    | externalDocsFacet
    ;

extensionFacets:
    type=id value=instance
    ;

openapiFacet:
    'openapi' openapi=stringInstance
    ;

infoFacet:
    'info' openapi=objectInstance
    ;

jsonSchemaDialectFacet:
    'jsonSchemaDialect' openapi=stringInstance
    ;

serversFacet:
    'servers' openapi=objectInstance
    ;

pathsFacet:
    'paths' openapi=objectInstance
    ;

webhooksFacet:
    'webhooks'
        MAP_START
            ( stringInstance | objectInstance )*
        MAP_END
    ;

componentsFacet:
    'components' openapi=objectInstance
    ;

securityFacet:
    'security' openapi=objectInstance
    ;

tagsFacet:
    'tags' openapi=objectInstance
    ;

externalDocsFacet:
    'externalDocs' openapi=objectInstance
    ;

relativeUriInstance:
    value=RELATIVE_URI
    ;

instance:
    simpleInstance | arrayInstance | objectInstance
    ;

simpleInstance:
    relativeUriInstance | stringInstance | booleanInstance | integerInstance | numberInstance
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
    name=id value=instance
    ;

id:
    RELATIVE_URI
    |   SCALAR
    |   NULL
    ;

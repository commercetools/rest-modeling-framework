grammar RAML;

@header {
package io.vrap.rmf.raml.persistence.antlr;
}

tokens {
    MAP_START, MAP_END, LIST_START, LIST_END, SCALAR
}

api:
    MAP_START
        api_facet+
    MAP_END;

api_facet:
      'title' SCALAR
    | 'description' SCALAR
    | 'version' SCALAR
    | 'baseUri' SCALAR
    | 'protocols' protocols
    | 'mediaType' mediaType
    ;

protocols:
    LIST_START
        SCALAR+
    LIST_END;

mediaType:
      SCALAR
    | LIST_START
        SCALAR+
      LIST_END;

library:
    MAP_START
    (
        'usage' SCALAR |
        type_declarations
    )+
    MAP_END
    ;

type_declarations:
    'types'
        MAP_START
        (
            type_declaration
        )+
        MAP_END
    ;

type_declaration:
    SCALAR
        MAP_START
        (
           'type' SCALAR |
           'displayName' SCALAR |
           'description' SCALAR |
           'default' SCALAR |
           'pattern' SCALAR |
           'properties'
                MAP_START
                (
                    property
                )+
                MAP_END
        )+
        MAP_END
    ;

property:
    SCALAR
        MAP_START
        (
            'type' SCALAR |
            'displayName' SCALAR |
            'description' SCALAR |
            'default' SCALAR
        )+
        MAP_END
    ;

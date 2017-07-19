grammar RAML;


@header {
package io.vrap.rmf.raml.persistence.antlr;
}


tokens {
    MAP_START, MAP_END, LIST_START, LIST_END, SCALAR
}

api:
    MAP_START
    ( simple_api_facet | list_api_facet )*
    MAP_END;

simple_api_facet:
    facet=('title' | 'description' | 'version' | 'baseUri') value=SCALAR
    ;

list_api_facet:
    facet=('protocols' | 'mediaType')
    ( values+=SCALAR | (LIST_START values+=SCALAR* LIST_END) )
    ;

library:
    MAP_START
    ( simple_library_facet | type_declarations )*
    MAP_END
    ;

simple_library_facet:
    facet='usage' value=SCALAR
    ;

type_declarations:
    facet='types'
        MAP_START
        ( types+=type_declaration )*
        MAP_END
    ;

type_declaration:
    name=SCALAR
        MAP_START
        ( type_declaration_facet | properties_facet )*
        MAP_END
    ;

type_declaration_facet:
    facet=('type' | 'displayName' | 'description' | 'default' | 'pattern') value=SCALAR
    ;

properties_facet:
    facet='properties'
        MAP_START
        ( properties+=property )*
        MAP_END
    ;

property:
    name=SCALAR
        MAP_START
        ( simple_property_facet | type_declaration_facet)*
        MAP_END
    ;

simple_property_facet:
    facet='required' value=SCALAR
    ;
grammar Node;

@header {
package io.vrap.rmf.nodes.antlr;
}


tokens {
    MAP_START, MAP_END, LIST_START, LIST_END,
    STRING, INT, FLOAT, BOOL
}

node:
    arrayNode | objectNode | valueNode;

arrayNode:
    LIST_START
        elements += node*
    LIST_END;

valueNode:
    STRING | INT | FLOAT | BOOL;

objectNode:
    MAP_START
        properties += property*
    MAP_END;

property:
    key=valueNode node
    ;

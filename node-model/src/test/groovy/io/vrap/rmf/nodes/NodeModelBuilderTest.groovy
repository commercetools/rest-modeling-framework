package io.vrap.rmf.nodes

import spock.lang.Shared
import spock.lang.Specification

class NodeModelBuilderTest extends Specification {
    @Shared
    NodeModelBuilder nodeModelBuilder = new NodeModelBuilder()

    def "parse json object"() {
        when:
        Node node = nodeModelBuilder.parseJson('''
        {
            "test": "me"
        }
        ''')
        then:
        node instanceof ObjectNode
        ObjectNode objectNode = node
        objectNode.properties.size() == 1
        objectNode.properties[0].key instanceof StringNode
        objectNode.properties[0].value instanceof StringNode
    }

    def "parse json array"() {
        when:
        Node node = nodeModelBuilder.parseJson('''
        [ "test", "me" ]
        ''')
        then:
        node instanceof ArrayNode
        ArrayNode arrayNode = node
        arrayNode.elements.size() == 2
        arrayNode.elements[0] instanceof StringNode
        arrayNode.elements[1] instanceof StringNode
    }

    def "parse yaml object"() {
        when:
        Node node = nodeModelBuilder.parseYaml('''\
        test: me
        ''')
        then:
        node instanceof ObjectNode
        ObjectNode objectNode = node
        objectNode.properties.size() == 1
        objectNode.properties[0].key instanceof StringNode
        objectNode.properties[0].value instanceof StringNode
    }

    def "parse yaml array"() {
        when:
        Node node = nodeModelBuilder.parseYaml('''\
        - test
        - me
        ''')
        then:
        node instanceof ArrayNode
        ArrayNode arrayNode = node
        arrayNode.elements.size() == 2
        arrayNode.elements[0] instanceof StringNode
        arrayNode.elements[1] instanceof StringNode
    }
}

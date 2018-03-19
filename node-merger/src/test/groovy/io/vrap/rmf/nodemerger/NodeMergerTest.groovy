package io.vrap.rmf.nodemerger

import com.fasterxml.jackson.core.JsonPointer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import spock.lang.Shared
import spock.lang.Specification

class NodeMergerTest extends Specification {
    @Shared
    NodeMerger merger = new NodeMerger()

    def "simple merge"() {
        when:
        JsonNode source = parse('''\
        description: source
        type: string
        ''')
        JsonNode target = parse('''\
        description:
        type:
            item: string
        ''')
        JsonNode merged = merger.merge(source, target)
        then:
        JsonNode description = merged.get('description')

        description != null
        description.asText() == 'source'

        JsonNode type = merged.get('type')
        type != null
    }

    def "merge resource types from RAML spec"() {
        when:
        JsonNode node = parse('''\
        resourceTypes:
            collection:
                get:
                    description: a list
                    headers:
                        APIKey:
        /products:
            type: collection
            get:
                description: override the description
                responses:
                    200:
                        body:
                            application/json:
        ''')

        JsonNode source = node.at(JsonPointer.compile('/resourceTypes/collection'))
        JsonNode target = node.at(JsonPointer.compile('/~1products'))
        JsonNode merged = merger.merge(source, target)
        then:
        JsonNode expected = parse('''\
        type: collection
        get:
            description: override the description
            responses:
                200:
                    body:
                        application/json:
            headers:
                APIKey:
        ''')
        merged == expected
    }

    JsonNode parse(String input) {
        YAMLFactory factory = new YAMLFactory();
        String stripIndent = input.stripIndent()
        return new ObjectMapper().readTree(factory.createParser(stripIndent))
    }

    String serialize(JsonNode node) {
        YAMLFactory factory = new YAMLFactory();
        return new ObjectMapper(factory).writeValueAsString(node)
    }
}

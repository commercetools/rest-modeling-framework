package io.vrap.rmf.nodes

import org.eclipse.emf.ecore.util.EcoreUtil
import spock.lang.Shared
import spock.lang.Specification

class NodeMergerTest extends Specification {
    @Shared
    NodeMerger merger = new NodeMerger()

    def "simple merge"() {
        when:
        Node source = parse('''\
        description: source
        type: string
        ''')
        Node target = parse('''\
        description:
        type:
            item: string
        ''')
        Node merged = merger.merge(source, target)
        then:
        Node expected = parse('''\
        description: source
        type:
            item: string
        ''')
        EcoreUtil.equals(merged, expected) == true
    }

    def "merge with missing source property"() {
        when:
        Node source = parse('''\
        get:
            queryParameters:
        ''')
        Node target = parse('''\
        get:
            queryParameters:
                id:
        ''')
        Node merged = merger.merge(source, target)
        then:
        Node expected = parse('''\
        get:
            queryParameters:
                id:
        ''')
        EcoreUtil.equals(merged, expected) == true
    }

    def "merge resource types from RAML spec"() {
        when:
        Node source = parse('''\
        get:
            description: a description
            headers:
                APIKey:
        ''')
        Node target = parse('''\
        get:
            description: override the description
            responses:
                200:
                    body:
                        application/json:
        ''')
        Node merged = merger.merge(source, target)
        then:
        Node expected = parse('''\
        get:
            description: override the description
            responses:
                200:
                    body:
                        application/json:
            headers:
                APIKey:
        ''')
        EcoreUtil.equals(merged, expected) == true
    }

    Node parse(String input) {
        String stripIndent = input.stripIndent()
        return new NodeModelBuilder().parseYaml(stripIndent)
    }
}

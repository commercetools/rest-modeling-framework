package io.vrap.rmf.raml.persistence.antlr

import org.eclipse.emf.common.util.URI
import spock.lang.Specification

class YamlLexerTest extends Specification {

    def "scalar values should be parsed correctly"() {
        when:
        YamlLexer lexer = new YamlLexer(input, URI.createURI('test.raml'), null)
        then:
        lexer.nextToken().type == tokenType
        where:
        input      | tokenType
        'true'     | RAMLParser.BOOL
        'false'    | RAMLParser.BOOL
        '123'      | RAMLParser.INT
        '123.0'    | RAMLParser.FLOAT
    }
}

package io.vrap.rmf.raml.persistence.antlr

import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.TokenStream
import spock.lang.Specification

/**
 * Unit tests for {@link URIParser}.
 */
class URIParserTest extends Specification {

    def "uri template with variables"() {
        when:
        def parser = parser('http://localhost:8080/api/{version}/user/{userId}');
        URIParser.UriTemplateContext uriTemplate = parser.uriTemplate()

        then:
        uriTemplate.parts.size() == 4
        uriTemplate.parts[0].literal().text == 'http://localhost:8080/api/'

        uriTemplate.parts[1].expression().variables.size() == 1
        uriTemplate.parts[1].expression().variables[0].text == 'version'

        uriTemplate.parts[2].literal().text == '/user/'
        uriTemplate.parts[3].expression().variables[0].text == 'userId'
    }

    URIParser parser(String input) {
        final CharStream charStream = CharStreams.fromString(input);
        final URILexer lexer = new URILexer(charStream);
        final TokenStream tokenStream = new CommonTokenStream(lexer);
        return new URIParser(tokenStream);
    }
}

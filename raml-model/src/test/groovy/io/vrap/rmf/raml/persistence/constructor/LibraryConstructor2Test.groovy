package io.vrap.rmf.raml.persistence.constructor

import io.vrap.rmf.raml.model.modules.Library
import io.vrap.rmf.raml.model.modules.OAuth20Settings
import io.vrap.rmf.raml.persistence.RamlResourceSet
import io.vrap.rmf.raml.persistence.antlr.RAMLCustomLexer
import io.vrap.rmf.raml.persistence.antlr.RAMLParser
import org.antlr.v4.runtime.CommonTokenFactory
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.TokenStream
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.URIConverter
import spock.lang.Shared
import spock.lang.Specification

/**
 * Unit tests for {@link ApiConstructor}
 */
class LibraryConstructor2Test extends Specification {
    @Shared
    ResourceSet resourceSet = new RamlResourceSet()
    @Shared
    URI uri = URI.createURI("test.raml");

    def "security scheme"() {
        when:
        Library library = constructLibrary(
                '''\
        securitySchemes:
            oauth_2_0:
                type: OAuth 2.0
                settings:
                    accessTokenUri: https://api.example.com/1/oauth2/token
                    authorizationGrants: [ authorization_code, implicit ]
                    authorizationUri: https://www.example.com/1/oauth2/authorize
        ''')

        then:
        library.securitySchemes.size() == 1
        library.securitySchemes[0].name == 'oauth_2_0'
        library.securitySchemes[0].type.literal == 'OAuth 2.0'
        library.securitySchemes[0].settings instanceof OAuth20Settings
        OAuth20Settings oauth20Settings = library.securitySchemes[0].settings
        oauth20Settings.accessTokenUri == 'https://api.example.com/1/oauth2/token'
        oauth20Settings.authorizationGrants == ['authorization_code', 'implicit']
        oauth20Settings.authorizationUri == 'https://www.example.com/1/oauth2/authorize'
    }

    Library constructLibrary(String input) {
        RAMLParser parser = parser(input)
        def libraryConstructor = new LibraryConstructor()
        Scope scope = Scope.of(resourceSet.createResource(uri))
        return libraryConstructor.construct(parser, scope)
    }

    RAMLParser parser(String input) {
        final URIConverter uriConverter = resourceSet.getURIConverter();
        def strippedInput = input.stripIndent()
        final RAMLCustomLexer lexer = new RAMLCustomLexer(strippedInput, uri, uriConverter);
        final TokenStream tokenStream = new CommonTokenStream(lexer);
        lexer.setTokenFactory(CommonTokenFactory.DEFAULT);
        new RAMLParser(tokenStream)
    }
}

package io.vrap.rmf.raml.generic.generator.php

import io.vrap.rmf.raml.generic.generator.php.PhpGenerator
import io.vrap.rmf.raml.model.modules.Api
import io.vrap.rmf.raml.persistence.RamlResourceSet
import io.vrap.rmf.raml.persistence.antlr.RAMLCustomLexer
import io.vrap.rmf.raml.persistence.antlr.RAMLParser
import io.vrap.rmf.raml.persistence.constructor.ApiConstructor
import io.vrap.rmf.raml.persistence.constructor.Scope
import org.antlr.v4.runtime.CommonTokenFactory
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.TokenStream
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.URIConverter
import spock.lang.Shared
import spock.lang.Specification

class TypeGeneratorTest extends Specification {
    @Shared
    ResourceSet resourceSet = new RamlResourceSet()
    @Shared
    URI uri = URI.createURI("test.raml");

    def "generate php"() {
        when:
        Api api = constructApi(
                '''\
        types:
            Person:
                properties:
                    name:
                example:
                    name: Mr. X
            Name:
                type: string
                example: John Doe        
        ''')
        then:
        TypesGenerator generator = new TypesGenerator()
        String result = generator.generateFile(api.types.get(0));
        result != null
    }

    Api constructApi(String input) {
        RAMLParser parser = parser(input)
        def apiConstructor = new ApiConstructor()
        Scope scope = Scope.of(resourceSet.createResource(uri))
        return (Api)apiConstructor.construct(parser, scope)
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

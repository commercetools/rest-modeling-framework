package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.persistence.RamlResourceSet;
import io.vrap.rmf.raml.persistence.ResourceFixtures;
import io.vrap.rmf.raml.persistence.antlr.RAMLCustomLexer;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import io.vrap.rmf.raml.persistence.antlr.RAMLParserFixtures;
import org.antlr.v4.runtime.CommonTokenFactory;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assume.assumeTrue;

public class TypeDeclarationResolverTest implements RAMLParserFixtures, ResourceFixtures {

    @Test
    public void api() {
        final File apiFile = new File("/Users/mkoester/Development/commercetools-api-reference/api.raml");
        assumeTrue(apiFile.exists());

        final URI apiUri = URI.createURI(apiFile.toURI().toString());
        final URIConverter uriConverter = new RamlResourceSet().getURIConverter();
        final RAMLCustomLexer lexer = new RAMLCustomLexer(apiUri, uriConverter);
        lexer.setTokenFactory(CommonTokenFactory.DEFAULT);

        final TokenStream tokenStream = new CommonTokenStream(lexer);

        final RAMLParser parser = new RAMLParser(tokenStream);
        final Resource resource = new RamlResourceSet().createResource(apiUri);

        final Scope scope = Scope.of(resource);
        final TypeDeclarationResolver resolver = new TypeDeclarationResolver();
        resolver.resolve(parser.api(), scope);

        assertThat(resource.getErrors()).isEmpty();
    }
}

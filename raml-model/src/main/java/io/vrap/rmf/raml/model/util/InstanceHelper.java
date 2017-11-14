package io.vrap.rmf.raml.model.util;

import io.vrap.rmf.raml.model.RamlDiagnostic;
import io.vrap.rmf.raml.model.RamlModelResult;
import io.vrap.rmf.raml.model.types.AnyType;
import io.vrap.rmf.raml.model.values.Instance;
import io.vrap.rmf.raml.persistence.RamlResourceSet;
import io.vrap.rmf.raml.persistence.antlr.RAMLCustomLexer;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import io.vrap.rmf.raml.persistence.constructor.InstanceConstructor;
import io.vrap.rmf.raml.persistence.constructor.Scope;
import io.vrap.rmf.raml.validation.InstanceValidator;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides helper methods for working with {@link Instance}s.
 */
public interface InstanceHelper {

    /**
     * Parses the instance given as JSON text and validates it against the given type.
     *
     * @param text the JSON serialized instance
     * @param type the type to validate the instance against
     *
     * @return the parsed and validated instance
     */
    static RamlModelResult<Instance> parse(final String text, final AnyType type) {
        final ResourceSet resourceSet = new RamlResourceSet();
        final URIConverter uriConverter = resourceSet.getURIConverter();
        final URI uri = URI.createURI("validate.raml");
        final RAMLCustomLexer lexer = new RAMLCustomLexer(text, uri, uriConverter);
        final TokenStream tokenStream = new CommonTokenStream(lexer);
        final RAMLParser parser = new RAMLParser(tokenStream);
        final Scope scope = Scope.of(resourceSet.createResource(uri));
        final Instance instance = new InstanceConstructor().construct(parser, scope);

        final List<Resource.Diagnostic> validationResults = new InstanceValidator().validate(instance, type).stream()
                .map(RamlDiagnostic::of)
                .collect(Collectors.toList());

        return RamlModelResult.of(validationResults, instance);
    }
}

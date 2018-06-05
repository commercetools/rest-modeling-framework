package io.vrap.rmf.raml.model.util;

import io.vrap.rmf.nodes.antlr.NodeToken;
import io.vrap.rmf.nodes.antlr.NodeTokenProvider;
import io.vrap.rmf.raml.model.RamlDiagnostic;
import io.vrap.rmf.raml.model.RamlModelResult;
import io.vrap.rmf.raml.model.types.AnyType;
import io.vrap.rmf.raml.model.types.Instance;
import io.vrap.rmf.raml.persistence.RamlResourceSet;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import io.vrap.rmf.raml.persistence.antlr.RamlNodeTokenSource;
import io.vrap.rmf.raml.persistence.constructor.InstanceConstructor;
import io.vrap.rmf.raml.persistence.constructor.Scope;
import io.vrap.rmf.raml.validation.InstanceValidator;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.List;
import java.util.UUID;
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
    static RamlModelResult<Instance> parseAndValidate(final String text, final AnyType type) {
        final Instance instance = parse(text, resourceFile(type));

        final List<Resource.Diagnostic> validationResults = validate(instance, type).stream()
                .map(RamlDiagnostic::of)
                .collect(Collectors.toList());

        return RamlModelResult.of(validationResults, instance);
    }

    static Instance parse(final String text) {
        return parse(text, null);
    }

    static Instance parse(final String text, String resource) {
        final ResourceSet resourceSet = new RamlResourceSet();
        final URIConverter uriConverter = resourceSet.getURIConverter();
        final URI uri = URI.createURI(resource == null ? UUID.randomUUID() + ".raml": resource);
        final RamlNodeTokenSource lexer = new RamlNodeTokenSource(text, uri, uriConverter);
        final TokenStream tokenStream = new CommonTokenStream(lexer);
        final RAMLParser parser = new RAMLParser(tokenStream);
        final Scope scope = Scope.of(resourceSet.createResource(uri));
        return new InstanceConstructor().construct(parser, scope);
    }

    static Instance parseJson(final String text) {
        return parseJson(text, null);
    }

    static Instance parseJson(final String text, String resource) {
        final ResourceSet resourceSet = new RamlResourceSet();
        final URIConverter uriConverter = resourceSet.getURIConverter();
        final URI uri = URI.createURI(resource == null ? UUID.randomUUID() + ".json": resource);
        final RamlNodeTokenSource lexer = new RamlNodeTokenSource(text, uri, uriConverter);
        final TokenStream tokenStream = new CommonTokenStream(lexer);
        final RAMLParser parser = new RAMLParser(tokenStream);
        final Scope scope = Scope.of(resourceSet.createResource(uri, "application/json"));
        return new InstanceConstructor().construct(parser, scope);
    }

    static List<Diagnostic> validate(final Instance instance, final AnyType type) {
        return new InstanceValidator().validate(instance, type);
    }

    static String resourceFile(EObject object) {
        String source = null;
        final NodeTokenProvider ramlTokenProvider =
                (NodeTokenProvider) EcoreUtil.getExistingAdapter(object, NodeTokenProvider.class);
        if (ramlTokenProvider != null) {
            final NodeToken nodeToken = ramlTokenProvider.getStart();
            source = nodeToken.getLocation();
        }

        return source;
    }
}

package io.vrap.rmf.raml.persistence;

import io.vrap.rmf.raml.model.types.BuiltinType;
import io.vrap.rmf.raml.validation.RamlValidationSetup;
import io.vrap.rmf.raml.validation.RamlValidator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ContentHandler;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIHandler;
import org.eclipse.emf.ecore.resource.impl.*;
import org.eclipse.emf.ecore.util.Diagnostician;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RamlResourceSet extends ResourceSetImpl {
    private final static List<String> ACCEPTED_MIME_TYPES =
            Arrays.asList("application/raml+yaml", "application/yaml", "application/json");
    private final static String ACCEPT_HEADER_VALUE = ACCEPTED_MIME_TYPES.stream()
            .collect(Collectors.joining(", "));

    static {
        setup();
    }

    public static void setup() {
        RamlValidationSetup.setup();
    }

    public RamlResourceSet() {
        final List<URIHandler> uriHandlers = Arrays.asList(
                new PlatformResourceURIHandlerImpl(),
                new FileURIHandlerImpl(),
                new EFSURIHandlerImpl(),
                new ArchiveURIHandlerImpl(),
                new ContentNegotiationURIHandler(ACCEPT_HEADER_VALUE));
        final ExtensibleURIConverterImpl uriConverter =
                new ExtensibleURIConverterImpl(uriHandlers, ContentHandler.Registry.INSTANCE.contentHandlers());
        setURIConverter(uriConverter);

        final Resource.Factory.Registry resourceFactoryRegistry = getResourceFactoryRegistry();
        final RamlResourceFactory resourceFactory = new RamlResourceFactory();
        resourceFactoryRegistry
                .getExtensionToFactoryMap().put("raml", resourceFactory);

        resourceFactoryRegistry.getProtocolToFactoryMap()
                .put("http", resourceFactory);

        final Map<String, Object> contentTypeToFactoryMap = resourceFactoryRegistry.getContentTypeToFactoryMap();
        for (final String contentType : ACCEPTED_MIME_TYPES) {
            contentTypeToFactoryMap
                    .put(contentType, resourceFactory);
        }
        addBuiltinTypes();
    }

    private void addBuiltinTypes() {
        final URL url = RamlResource.class.getResource("/builtin-types.raml");
        final URI uri = URI.createURI(url.toString());
        getURIConverter().getURIMap()
                .put(BuiltinType.RESOURCE_URI, uri);
    }

    private static class RamlResourceFactory extends ResourceFactoryImpl {
        @Override
        public Resource createResource(final URI uri) {
            return new RamlResource(uri);
        }
    }

    /**
     * Validates all resources and returns the errors.
     * @return the errors
     */
    public List<Resource.Diagnostic> validate() {
        getResources().stream()
                .filter(RamlResource.class::isInstance).map(RamlResource.class::cast)
                .forEach(RamlResource::validate);

        final List<Resource.Diagnostic> errors = getResources().stream()
                .flatMap(r -> r.getErrors().stream())
                .collect(Collectors.toList());

        return errors;
    }
}

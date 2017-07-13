package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.modules.Library;
import io.vrap.rmf.raml.model.modules.LibraryUse;
import io.vrap.rmf.raml.model.types.BuiltinType;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.nodes.Node;

import java.io.*;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.vrap.rmf.raml.model.annotations.AnnotationsPackage.Literals.ANY_ANNOTATION_TYPE;
import static io.vrap.rmf.raml.model.modules.ModulesPackage.Literals.TYPE_CONTAINER__ANNOTATION_TYPES;
import static io.vrap.rmf.raml.model.modules.ModulesPackage.Literals.TYPE_CONTAINER__TYPES;

/**
 * Represents the current scope during the construction phase.
 */
public class Scope {
    private final Resource resource;
    private final URI uri;
    private final ResourceSet resourceSet;
    private final EObject eObject;
    private final EStructuralFeature feature;
    private final Yaml yaml;
    private final Node valueNode;

    private Scope(final Resource resource, final URI uri, final Yaml yaml,
                  final EObject eObject, final EStructuralFeature feature, final Node valueNode) {
        this.resource = resource;
        this.uri = uri;
        this.yaml = yaml;
        this.resourceSet = resource.getResourceSet();
        this.eObject = eObject;
        this.feature = feature;
        this.valueNode = valueNode;
    }

    public Library getUsedLibrary(final String name) {
        final Optional<LibraryUse> libraryUse = resource.getContents().stream()
                .filter(Library.class::isInstance)
                .map(Library.class::cast)
                .flatMap(library -> library.getUses().stream())
                .filter(LibraryUse.class::isInstance)
                .map(LibraryUse.class::cast)
                .filter(use -> use.getName().equals(name))
                .findFirst();

        return libraryUse.map(LibraryUse::getLibrary).orElse(null);
    }

    public Resource getResource() {
        return resource;
    }

    public EObject eObject() {
        return eObject;
    }

    public EStructuralFeature eFeature() {
        return feature;
    }

    public URI resolve(final String relativePath) {
        final String[] segments = URI.createURI(relativePath).segments();
        return getBaseUri().appendSegments(segments);
    }

    private URI getBaseUri() {
        return uri.trimSegments(1);
    }

    public Node getValueNode() {
        return valueNode;
    }

    public Resource getResource(final String relativePath) {
        final URI uri = resolve(relativePath);
        return resourceSet.getResource(uri, true);
    }

    public EObject getImportedTypeById(final String id) {
        final String uriFragment = getUriFragment(id);

        final Resource builtinTypeResource = resourceSet.getResource(BuiltinType.RESOURCE_URI, true);
        final EObject resolvedType = Optional.ofNullable(builtinTypeResource.getEObject(uriFragment))
                .orElseGet(() -> getImportedTypeById(this.resource, id));
        return resolvedType;
    }

    private String getUriFragment(final String id) {
        final EClass type = (EClass) feature.getEType();
        final String fragment = ANY_ANNOTATION_TYPE.isSuperTypeOf(type) ?
                TYPE_CONTAINER__ANNOTATION_TYPES.getName() :
                TYPE_CONTAINER__TYPES.getName();
        return Stream.of(fragment, id)
                .collect(Collectors.joining("/", "/", ""));
    }

    private EObject getImportedTypeById(final Resource resource, final String id) {
        final EClass type = (EClass) feature.getEType();
        final String uriFragment = getUriFragment(id);

        final EObject resolvedType;
        final String[] segments = id.split("\\.");
        if (segments.length == 1) {
            final InternalEObject internalEObject;
            internalEObject = (InternalEObject) EcoreUtil.create(type);
            internalEObject.eSetProxyURI(resource.getURI().appendFragment(uriFragment));
            resolvedType = internalEObject;
        } else if (segments.length == 2) {
            final String libraryName = segments[0];
            final Library usedLibrary = getUsedLibrary(libraryName);
            if (usedLibrary == null) {
                addError("Library use {0} doesn't exist in {1}", libraryName, resource.getURI());
                resolvedType = null;
            } else {
                final String resolvedId = segments[1];
                final Scope usedLibraryScope = with(usedLibrary.eResource());
                resolvedType = usedLibraryScope.getImportedTypeById(resolvedId);
            }
        } else {
            addError("Uses has invalid format {0}", id);
            resolvedType = null;
        }
        return resolvedType;
    }

    /**
     * Sets the given value on this scope.
     *
     * @param value the value, either a wrapper primitive wrapper object {@link Object},
     *              a {@link EObject} or a {@link List} of these types.
     * @return the value
     */
    @SuppressWarnings("unchecked")
    public <T> T setValue(final T value) {
        final EObject container = eObject();
        final EStructuralFeature feature = eFeature();
        if (feature.isMany() && !(value instanceof List)) {
            ((EList<T>) container.eGet(feature)).add(value);
        } else {
            container.eSet(feature, value);
        }
        return value;
    }

    /**
     * Composes a yaml node from the given reader.
     *
     * @param reader the reader
     * @return the composed node
     */
    public Node compose(final Reader reader) {
        return yaml.compose(reader);
    }

    /**
     * Composes a yaml node from the given uri.
     *
     * @param uri the uri
     * @return the composed node
     */
    public Scope compose(final String uri) {
        try {
            final URI resolvedUri = resolve(uri);
            final InputStream inputStream = resourceSet.getURIConverter()
                    .createInputStream(resolvedUri);

            try (final Reader reader = new InputStreamReader(new BufferedInputStream(inputStream, 1024))) {
                return with(yaml.compose(reader), resolvedUri);
            }
        } catch (IOException e) {
            addError(e.getMessage());
            return this;
        }
    }

    public void addError(final String messagePattern, final Object... arguments) {
        final String message = MessageFormat.format(messagePattern, arguments);

        final Optional<Node> optionalNode = Stream.of(arguments)
                .filter(Node.class::isInstance)
                .map(Node.class::cast)
                .findFirst();
        final Optional<Mark> startMark = optionalNode.map(Node::getStartMark);

        final int line = startMark.map(Mark::getLine).orElse(0);
        final int column = startMark.map(Mark::getColumn).orElse(0);
        final String location = uri.toString();

        resource.getErrors()
                .add(new RamlDiagnostic(message, location, line, column));
    }

    public Scope with(final EObject eObject) {
        return new Scope(resource, uri, yaml, eObject, feature, valueNode);
    }

    public Scope with(final EObject eObject, final EStructuralFeature feature) {
        return new Scope(resource, uri, yaml, eObject, feature, null);
    }

    public Scope with(final Node node, final URI uri) {
        return new Scope(resource, uri, yaml, eObject, feature, node);
    }

    public Scope with(final Node node) {
        return with(node, uri);
    }

    public Scope with(final EStructuralFeature feature) {
        return with(eObject, feature);
    }

    public Scope with(final Resource resource) {
        return new Scope(resource, uri, yaml, eObject, feature, valueNode);
    }

    public static Scope of(final Resource resource) {
        return new Scope(resource, resource.getURI(), new Yaml(), null, null, null);
    }

    private static class RamlDiagnostic implements Resource.Diagnostic {
        private final String message;
        private final String location;
        private final int line;
        private final int column;

        public RamlDiagnostic(final String message, final String location, final int line, final int column) {
            this.message = message;
            this.location = location;
            this.line = line;
            this.column = column;
        }

        @Override
        public String getMessage() {
            return message;
        }

        @Override
        public String getLocation() {
            return location;
        }

        @Override
        public int getLine() {
            return line;
        }

        @Override
        public int getColumn() {
            return column;
        }
    }
}

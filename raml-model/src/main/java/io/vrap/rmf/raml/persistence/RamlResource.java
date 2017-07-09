package io.vrap.rmf.raml.persistence;

import io.vrap.rmf.raml.persistence.constructor.*;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static io.vrap.functional.utils.Classes.as;
import static io.vrap.rmf.raml.persistence.RamlFragmentKind.ANNOTATION_TYPE_DECLARATION;
import static io.vrap.rmf.raml.persistence.RamlFragmentKind.DATA_TYPE;

public class RamlResource extends ResourceImpl {
    private final static Map<RamlFragmentKind, Constructor<MappingNode>> ROOT_CONSTRUCTORS = new HashMap<>();
    {
        addRootConstructor(RamlFragmentKind.API,
                new ApiConstructor());
        addRootConstructor(RamlFragmentKind.LIBRARY,
                new LibraryConstructor());

        for (final RamlFragmentKind fragmentKind : Arrays.asList(DATA_TYPE, ANNOTATION_TYPE_DECLARATION)) {
            addRootConstructor(fragmentKind,
                    new TypeDeclarationFragmentConstructor(fragmentKind));
        }
    }

    private final Scope resourceScope;

    public RamlResource(final URI uri) {
        super(uri);
        resourceScope = Scope.of(this);
    }

    private static void addRootConstructor(final RamlFragmentKind fragmentKind, final Constructor<MappingNode> constructor) {
        ROOT_CONSTRUCTORS.put(fragmentKind, constructor);
    }

    @Override
    protected void doLoad(final InputStream inputStream, final Map<?, ?> options) throws IOException {
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        final Constructor<MappingNode> rootConstructor = getRootConstructor(bufferedInputStream);

        try (final Reader reader = new InputStreamReader(bufferedInputStream)) {
            final Node rootNode = resourceScope.compose(reader);
            final EObject rootObject = construct(rootConstructor, rootNode);
            getContents().add(rootObject);
        }
    }

    private EObject construct(final Constructor<MappingNode> rootConstructor, final Node node) {
        final Optional<MappingNode> validMappingNode = as(MappingNode.class, node);
        if (validMappingNode.isPresent()) {
            return (EObject) rootConstructor.apply(validMappingNode.get(), resourceScope);
        } else {
            throw new IllegalArgumentException("Invalid node");
        }
    }

    @Override
    protected EObject getEObject(final List<String> uriFragmentPath) {
        if (uriFragmentPath.size() != 2) {
            throw new AssertionError("Invalid uri fragment path:" + uriFragmentPath.stream().collect(Collectors.joining("/")));
        }
        if (uriFragmentPath.size() == 2) {
            final EObject rootObject = getEObjectForURIFragmentRootSegment("");
            final String featureName = uriFragmentPath.get(0);
            final EReference feature = (EReference) rootObject.eClass().getEStructuralFeature(featureName);
            final EAttribute idAttribute = feature.getEReferenceType().getEIDAttribute();
            final EList<EObject> children = (EList<EObject>) rootObject.eGet(feature);
            final String id = uriFragmentPath.get(1);
            return children.stream()
                    .filter(eObject -> id.equals(eObject.eGet(idAttribute)))
                    .findFirst().orElse(null);
        }
        return null;
    }

    private Constructor<MappingNode> getRootConstructor(final InputStream inputStream) throws IOException {
        inputStream.mark(1024);
        final String header = new Scanner(inputStream).useDelimiter("\\n").next();
        inputStream.reset();
        final RamlFragmentKind fragmentKind = RamlFragmentKind.fromHeader(header).orElse(null);
        return ROOT_CONSTRUCTORS.get(fragmentKind);
    }
}

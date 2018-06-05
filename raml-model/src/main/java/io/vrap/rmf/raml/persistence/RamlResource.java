package io.vrap.rmf.raml.persistence;

import io.vrap.rmf.raml.model.RamlDiagnostic;
import io.vrap.rmf.raml.persistence.antlr.ParserErrorCollector;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import io.vrap.rmf.raml.persistence.antlr.RamlNodeTokenSource;
import io.vrap.rmf.raml.persistence.constructor.*;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.util.Diagnostician;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import static io.vrap.rmf.raml.model.elements.ElementsPackage.Literals.NAMED_ELEMENT;
import static io.vrap.rmf.raml.model.elements.ElementsPackage.Literals.NAMED_ELEMENT__NAME;
import static io.vrap.rmf.raml.model.modules.ModulesPackage.Literals.TYPE_CONTAINER__ANNOTATION_TYPES;
import static io.vrap.rmf.raml.model.modules.ModulesPackage.Literals.TYPE_CONTAINER__TYPES;

public class RamlResource extends ResourceImpl {
    private final Scope resourceScope;

    public RamlResource(final URI uri) {
        super(uri);
        resourceScope = Scope.of(this);
    }

    @Override
    protected void doLoad(final InputStream inputStream, final Map<?, ?> options) throws IOException {
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        final Optional<BaseConstructor> optionalRootConstructor = getRootConstructor(bufferedInputStream);

        if (optionalRootConstructor.isPresent()) {
            final BaseConstructor rootConstructor = optionalRootConstructor.get();
            final RamlNodeTokenSource lexer = new RamlNodeTokenSource(uri, getURIConverter());
            final TokenStream tokenStream = new CommonTokenStream(lexer);
            final RAMLParser parser = new RAMLParser(tokenStream);

            parser.removeErrorListeners();
            final ParserErrorCollector errorCollector = new ParserErrorCollector();
            parser.addErrorListener(errorCollector);

            try {
                rootConstructor.construct(parser, resourceScope);
                validate();
            } catch (final Exception e) {
                getErrors().addAll(errorCollector.getErrors());
                throw e;
            }
        }
    }

    protected void validate() {
        for (final EObject eObject : getContents()) {
            org.eclipse.emf.common.util.Diagnostic diagnostic = Diagnostician.INSTANCE.validate(eObject);
            if (diagnostic.getSeverity() != org.eclipse.emf.common.util.Diagnostic.OK) {
                diagnostic.getChildren().stream().map(RamlDiagnostic::of).forEach(getErrors()::add);
            }
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
            final EClass eReferenceType = feature.getEReferenceType();

            if (NAMED_ELEMENT.isSuperTypeOf(eReferenceType)) {
                @SuppressWarnings("unchecked") final EList<EObject> children = (EList<EObject>) rootObject.eGet(feature);
                final String name = uriFragmentPath.get(1);
                return children.stream()
                        .filter(eObject -> name.equals(eObject.eGet(NAMED_ELEMENT__NAME)))
                        .findFirst()
                        .orElse(null);
            }
        }
        return null;
    }

    private Optional<BaseConstructor> getRootConstructor(final InputStream inputStream) throws IOException {
        inputStream.mark(1024);
        @SuppressWarnings("resource")        final String header = new Scanner(inputStream).useDelimiter("\\n").next();
        inputStream.reset();
        final RamlFragmentKind fragmentKind = RamlFragmentKind.fromHeader(header).orElse(null);
        if (fragmentKind == null) {
            resourceScope.addError("Unknown fragment kind {0}", header);
            return Optional.empty();
        }
        switch (fragmentKind) {
            case API: return Optional.of(new ApiConstructor());
            case EXTENSION: return Optional.of(new ExtensionConstructor());
            case LIBRARY: return Optional.of(new LibraryConstructor());
            case DATA_TYPE: return Optional.of(new TypeDeclarationFragmentConstructor(TYPE_CONTAINER__TYPES));
            case ANNOTATION_TYPE_DECLARATION: return Optional.of(new TypeDeclarationFragmentConstructor(TYPE_CONTAINER__ANNOTATION_TYPES));
            default:
                resourceScope.addError("Not yet implemented fragment kind {0}", fragmentKind);
                return Optional.empty();
        }
    }
}

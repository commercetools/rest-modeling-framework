package io.vrap.rmf.raml.persistence;

import io.vrap.rmf.raml.persistence.antlr.ParserErrorCollector;
import io.vrap.rmf.raml.persistence.antlr.RAMLCustomLexer;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import io.vrap.rmf.raml.persistence.constructor.*;
import org.antlr.v4.runtime.CommonTokenFactory;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

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
        final AbstractConstructor rootConstructor = getRootConstructor(bufferedInputStream);

        final RAMLCustomLexer lexer = new RAMLCustomLexer(uri, getURIConverter());
        lexer.setTokenFactory(CommonTokenFactory.DEFAULT);
        final TokenStream tokenStream = new CommonTokenStream(lexer);
        final RAMLParser parser = new RAMLParser(tokenStream);

        parser.removeErrorListeners();
        final ParserErrorCollector errorCollector = new ParserErrorCollector();
        parser.addErrorListener(errorCollector);

        try {
            rootConstructor.construct(parser, resourceScope);
        } catch (Exception e) {
            getErrors().addAll(errorCollector.getErrors());
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
            @SuppressWarnings("unchecked")            final EList<EObject> children = (EList<EObject>) rootObject.eGet(feature);
            final String id = uriFragmentPath.get(1);
            return children.stream()
                    .filter(eObject -> id.equals(eObject.eGet(idAttribute)))
                    .findFirst().orElse(null);
        }
        return null;
    }

    private AbstractConstructor getRootConstructor(final InputStream inputStream) throws IOException {
        inputStream.mark(1024);
        @SuppressWarnings("resource")        final String header = new Scanner(inputStream).useDelimiter("\\n").next();
        inputStream.reset();
        final RamlFragmentKind fragmentKind = RamlFragmentKind.fromHeader(header).orElse(null);
        switch (fragmentKind) {
            case API: return new ApiConstructor();
            case LIBRARY: return new LibraryConstructor();
            case DATA_TYPE: return new TypeDeclarationFragmentConstructor(TYPE_CONTAINER__TYPES);
            case ANNOTATION_TYPE_DECLARATION: return new TypeDeclarationFragmentConstructor(TYPE_CONTAINER__ANNOTATION_TYPES);
            default:
                throw new IllegalStateException("Unknown fragment kind:" + fragmentKind);
        }
    }
}

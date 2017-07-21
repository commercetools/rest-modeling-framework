package io.vrap.rmf.raml.persistence.antlr;

import io.vrap.rmf.raml.model.modules.Library;
import io.vrap.rmf.raml.model.modules.ModulesFactory;
import io.vrap.rmf.raml.persistence.RamlResourceSet;
import io.vrap.rmf.raml.persistence.constructor.Scope;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Constructs a library from a {@link RAMLParser.LibraryContext}.
 */
public class LibraryConstructor extends AbstractConstructor {
    protected final static ModulesFactory FACTORY = ModulesFactory.eINSTANCE;

    protected LibraryConstructor(final Scope scope) {
        pushScope(scope);
    }

    @Override
    public Object visitLibrary(final RAMLParser.LibraryContext ctx) {
        final Library library = FACTORY.createLibrary();
        peekScope().getResource().getContents().add(library);

        for (final RAMLParser.AttributeFacetContext attributeFacet : ctx.attributeFacet()) {
            setAttribute(attributeFacet, library);
        }

        final Scope libraryScope = peekScope().with(library);

        for (final RAMLParser.TypesFacetContext typesFacet : ctx.typesFacet()) {
            final String typesReferenceName = typesFacet.facet.getText();
            final EStructuralFeature typesFeature = library.eClass().getEStructuralFeature(typesReferenceName);

            final Scope typesScope = pushScope(libraryScope.with(typesFeature));

            final List<Object> types = typesFacet.types.stream()
                    .map(this::visitTypeDeclaration)
                    .collect(Collectors.toList());

            typesScope.setValue(ECollections.asEList(types));
        }

        return library;
    }

    public static LibraryConstructor of(final URI uri) {
        final Resource resource = new RamlResourceSet().createResource(uri);
        final Scope rootScope = Scope.of(resource);

        return new LibraryConstructor(rootScope);
    }
}

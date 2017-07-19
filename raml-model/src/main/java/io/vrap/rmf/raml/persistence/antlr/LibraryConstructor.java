package io.vrap.rmf.raml.persistence.antlr;

import io.vrap.rmf.raml.model.modules.Library;
import io.vrap.rmf.raml.model.modules.ModulesFactory;
import io.vrap.rmf.raml.persistence.RamlResourceSet;
import io.vrap.rmf.raml.persistence.constructor.Scope;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import java.util.List;
import java.util.stream.Collectors;

import static io.vrap.rmf.raml.model.modules.ModulesPackage.Literals.TYPE_CONTAINER__TYPES;

/**
 * Constructs a library from a {@link RAMLParser.LibraryContext}.
 */
public class LibraryConstructor extends AbstractConstructor {
    protected final static ModulesFactory FACTORY = ModulesFactory.eINSTANCE;

    protected LibraryConstructor(final Scope scope) {
        super(scope);
    }

    @Override
    public Object visitLibrary(final RAMLParser.LibraryContext ctx) {
        final Library library = FACTORY.createLibrary();
        scope.getResource().getContents().add(library);

        for (final RAMLParser.AttributeFacetContext attributeFacet : ctx.attributeFacet()) {
            setAttribute(attributeFacet, library);
        }

        final Scope libraryScope = scope.with(library);
        final Scope typesScope = libraryScope.with(TYPE_CONTAINER__TYPES);
        final TypeDeclarationConstructor typeDeclarationConstructor = TypeDeclarationConstructor.of(typesScope);

        for (final RAMLParser.TypeDeclarationsContext typeDeclarations : ctx.typeDeclarations()) {
            final List<Object> types = typeDeclarations.types.stream()
                    .map(typeDeclarationConstructor::visitTypeDeclaration)
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

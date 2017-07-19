package io.vrap.rmf.raml.persistence.antlr;

import io.vrap.rmf.raml.model.modules.Library;
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
    protected LibraryConstructor(final Scope scope) {
        super(scope);
    }

    @Override
    public Object visitLibrary(final RAMLParser.LibraryContext ctx) {
        final Library library = FACTORY.createLibrary();
        scope.getResource().getContents().add(library);

        final Scope libraryScope = scope.with(library);
        for (final RAMLParser.Library_facetContext libraryFacet : ctx.library_facet()) {
            constructAttribute(library, libraryFacet.facet, libraryFacet.value);
        }

        final Scope typesScope = libraryScope.with(TYPE_CONTAINER__TYPES);
        final TypeDeclarationConstructor typeDeclarationConstructor = TypeDeclarationConstructor.of(typesScope);

        for (final RAMLParser.Type_declarationsContext typeDeclarations : ctx.type_declarations()) {
            final List<Object> types = typeDeclarations.types.stream()
                    .map(typeDeclarationConstructor::visitType_declaration)
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

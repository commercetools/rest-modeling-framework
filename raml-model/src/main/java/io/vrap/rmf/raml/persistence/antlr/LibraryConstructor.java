package io.vrap.rmf.raml.persistence.antlr;

import io.vrap.rmf.raml.model.modules.Library;
import io.vrap.rmf.raml.persistence.constructor.Scope;
import org.eclipse.emf.common.util.ECollections;

import java.util.List;
import java.util.stream.Collectors;

import static io.vrap.rmf.raml.model.modules.ModulesPackage.Literals.TYPE_CONTAINER__TYPES;

public class LibraryConstructor extends AbstractConstructor {
    protected LibraryConstructor(final Scope scope) {
        super(scope);
    }

    @Override
    public Object visitLibrary(final RAMLParser.LibraryContext ctx) {
        final Library library = FACTORY.createLibrary();
        final Scope libraryScope = scope.with(library);
        for (final RAMLParser.Simple_library_facetContext simpleLibraryFacet : ctx.simple_library_facet()) {
            constructAttribute(library, simpleLibraryFacet.facet, simpleLibraryFacet.value);
        }
        final Scope typesScope = libraryScope.with(TYPE_CONTAINER__TYPES);
        final TypeDeclarationConstructor typeDeclarationConstructor = new TypeDeclarationConstructor(typesScope);
        for (final RAMLParser.Type_declarationsContext typeDeclarations : ctx.type_declarations()) {
            final List<Object> types = typeDeclarations.types.stream()
                    .map(typeDeclarationConstructor::visitType_declaration)
                    .collect(Collectors.toList());
            typesScope.setValue(ECollections.asEList(types));
        }

        return library;
    }


    public static LibraryConstructor of(final Scope scope) {
        return new LibraryConstructor(scope);
    }
}

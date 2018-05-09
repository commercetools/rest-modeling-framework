package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.modules.Library;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import org.eclipse.emf.ecore.EObject;

import java.util.function.Predicate;

/**
 * Constructs a library from a {@link RAMLParser.LibraryContext}.
 */
public class LibraryConstructor extends BaseConstructor {

    @Override
    public EObject construct(final RAMLParser parser, final Scope scope) {
        final DeclarationResolver declarationResolver =
                new DeclarationResolver();
        final RAMLParser.LibraryContext libraryContext = parser.library();
        declarationResolver.resolve(libraryContext, scope);

        final Library library = (Library) withinScope(scope,
                s -> visitLibrary(libraryContext));
        return library;
    }

    @Override
    public Object visitLibrary(final RAMLParser.LibraryContext ctx) {
        final EObject rootObject = scope.getResource().getContents().get(0);

        return withinScope(scope.with(rootObject), rootScope -> {
            final Predicate<RAMLParser.TypeContainerFacetsContext> isSecuritySchemesFacet =
                    typeContainerFacets -> typeContainerFacets.securitySchemesFacet() != null;

            // TODO move to first pass
            // order is relevant here: first create security schemes
            ctx.typeContainerFacets().stream()
                    .filter(isSecuritySchemesFacet)
                    .forEach(this::visitTypeContainerFacets);

            ctx.typeContainerFacets().stream()
                    .filter(isSecuritySchemesFacet.negate())
                    .forEach(this::visitTypeContainerFacets);

            return rootObject;
        });
    }
}

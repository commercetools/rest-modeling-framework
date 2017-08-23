package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.modules.Library;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import org.eclipse.emf.ecore.EObject;

/**
 * Constructs a library from a {@link RAMLParser.LibraryContext}.
 */
public class LibraryConstructor extends BaseConstructor {

    @Override
    public EObject construct(final RAMLParser parser, final Scope scope) {
        final TypeDeclarationResolver typeDeclarationResolver =
                new TypeDeclarationResolver();
        typeDeclarationResolver.resolve(parser.library(), scope);
        parser.reset();

        final Library library = (Library) withinScope(scope,
                s -> visitLibrary(parser.library()));
        return library;
    }

    @Override
    public Object visitLibrary(final RAMLParser.LibraryContext ctx) {
        final EObject rootObject = scope.getResource().getContents().get(0);

        return withinScope(scope.with(rootObject), rootScope -> {
            ctx.usesFacet().forEach(this::visitUsesFacet);

            ctx.annotationFacet().forEach(this::visitAnnotationFacet);
            ctx.attributeFacet().forEach(this::visitAttributeFacet);
            ctx.typesFacet().forEach(this::visitTypesFacet);
            ctx.securitySchemesFacet().forEach(this::visitSecuritySchemesFacet);
            ctx.traitsFacet().forEach(this::visitTraitsFacet);
            ctx.resourceTypesFacet().forEach(this::visitResourceTypesFacet);

            return rootObject;
        });
    }
}

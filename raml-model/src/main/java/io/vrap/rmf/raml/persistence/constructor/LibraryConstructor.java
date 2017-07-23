package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.modules.Library;
import io.vrap.rmf.raml.model.modules.ModulesFactory;
import io.vrap.rmf.raml.persistence.RamlResourceSet;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * Constructs a library from a {@link RAMLParser.LibraryContext}.
 */
public class LibraryConstructor extends AbstractConstructor {
    protected final static ModulesFactory FACTORY = ModulesFactory.eINSTANCE;

    @Override
    public EObject construct(final RAMLParser parser, final Scope scope) {
        final Library library = (Library) withinScope(scope,
                s -> visitLibrary(parser.library()));
        return library;
    }

    @Override
    public Object visitLibrary(final RAMLParser.LibraryContext ctx) {
        final Library library = FACTORY.createLibrary();
        scope.getResource().getContents().add(library);

        pushScope(scope.with(library));
        ctx.usesFacet().forEach(this::visitUsesFacet);

        ctx.annotationFacet().forEach(this::visitAnnotationFacet);
        ctx.attributeFacet().forEach(this::visitAttributeFacet);
        ctx.typesFacet().forEach(this::visitTypesFacet);

        popScope();

        return library;
    }

    public static LibraryConstructor of(final URI uri) {
        final Resource resource = new RamlResourceSet().createResource(uri);

        final LibraryConstructor constructor = new LibraryConstructor();
        constructor.pushScope(Scope.of(resource));

        return constructor;
    }
}

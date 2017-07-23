package io.vrap.rmf.raml.persistence.antlr;

import io.vrap.rmf.raml.model.modules.Library;
import io.vrap.rmf.raml.model.modules.ModulesFactory;
import io.vrap.rmf.raml.persistence.RamlResourceSet;
import io.vrap.rmf.raml.persistence.constructor.Scope;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * Constructs a library from a {@link RAMLParser.LibraryContext}.
 */
public class LibraryConstructor extends AbstractConstructor {
    protected final static ModulesFactory FACTORY = ModulesFactory.eINSTANCE;

    @Override
    public Object visitLibrary(final RAMLParser.LibraryContext ctx) {
        final Library library = FACTORY.createLibrary();
        peekScope().getResource().getContents().add(library);

        pushScope(peekScope().with(library));

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

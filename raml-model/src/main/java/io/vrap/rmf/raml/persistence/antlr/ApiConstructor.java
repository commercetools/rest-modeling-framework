package io.vrap.rmf.raml.persistence.antlr;

import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.modules.ModulesFactory;
import io.vrap.rmf.raml.persistence.RamlResourceSet;
import io.vrap.rmf.raml.persistence.constructor.Scope;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

public class ApiConstructor extends AbstractConstructor {
    protected final static ModulesFactory FACTORY = ModulesFactory.eINSTANCE;

    @Override
    public Object visitApi(final RAMLParser.ApiContext ctx) {
        final Api api = FACTORY.createApi();
        scope.getResource().getContents().add(api);

        pushScope(scope.with(api));

        ctx.annotationFacet().forEach(this::visitAnnotationFacet);
        ctx.attributeFacet().forEach(this::visitAttributeFacet);
        ctx.typesFacet().forEach(this::visitTypesFacet);

        popScope();

        return api;
    }

    public static ApiConstructor of(final URI uri) {
        final Resource resource = new RamlResourceSet().createResource(uri);

        final ApiConstructor constructor = new ApiConstructor();
        constructor.pushScope(Scope.of(resource));

        return constructor;
    }
}

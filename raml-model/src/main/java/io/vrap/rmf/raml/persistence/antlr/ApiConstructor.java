package io.vrap.rmf.raml.persistence.antlr;

import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.modules.ModulesFactory;
import io.vrap.rmf.raml.persistence.RamlResourceSet;
import io.vrap.rmf.raml.persistence.constructor.Scope;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

import java.util.List;
import java.util.stream.Collectors;

public class ApiConstructor extends AbstractConstructor {
    protected final static ModulesFactory FACTORY = ModulesFactory.eINSTANCE;

    protected ApiConstructor(final Scope scope) {
        super(scope);
    }

    @Override
    public Object visitApi(final RAMLParser.ApiContext ctx) {
        final Api api = FACTORY.createApi();
        scope.getResource().getContents().add(api);

        for (final RAMLParser.AttributeFacetContext attributeFacet : ctx.attributeFacet()) {
            setAttribute(attributeFacet, api);
        }

        final Scope apiScope = scope.with(api);

        for (final RAMLParser.TypesFacetContext typesFacet : ctx.typesFacet()) {
            final String typesReferenceName = typesFacet.facet.getText();
            final EStructuralFeature typesFeature = api.eClass().getEStructuralFeature(typesReferenceName);

            final Scope typesScope = apiScope.with(typesFeature);
            final TypeDeclarationConstructor typeDeclarationConstructor = TypeDeclarationConstructor.of(typesScope);

            final List<Object> types = typesFacet.types.stream()
                    .map(typeDeclarationConstructor::visitTypeDeclaration)
                    .collect(Collectors.toList());

            typesScope.setValue(ECollections.asEList(types));
        }

        return api;
    }

    public static ApiConstructor of(final URI uri) {
        final Resource resource = new RamlResourceSet().createResource(uri);
        final Scope rootScope = Scope.of(resource);

        return new ApiConstructor(rootScope);
    }
}

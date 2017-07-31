package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.resources.Resource;
import io.vrap.rmf.raml.model.resources.ResourcesFactory;
import io.vrap.rmf.raml.model.resources.UriTemplate;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import org.eclipse.emf.ecore.EObject;

import java.util.List;
import java.util.stream.Collectors;

import static io.vrap.rmf.raml.model.modules.ModulesPackage.Literals.API__BASE_URI;
import static io.vrap.rmf.raml.model.modules.ModulesPackage.Literals.API__BASE_URI_PARAMETERS;
import static io.vrap.rmf.raml.model.resources.ResourcesPackage.Literals.RESOURCE_CONTAINER__RESOURCES;
import static io.vrap.rmf.raml.model.resources.ResourcesPackage.Literals.RESOURCE__URI_PARAMETERS;

public class ApiConstructor extends AbstractConstructor {
    private final UriTemplateConstructor uriTemplateConstructor = new UriTemplateConstructor();

    @Override
    public EObject construct(final RAMLParser parser, final Scope scope) {
        final TypeDeclarationResolver typeDeclarationResolver = new TypeDeclarationResolver();
        typeDeclarationResolver.resolve(parser.api(), scope);
        parser.reset();

        final Api api = (Api) withinScope(scope,
                s -> visitApi(parser.api()));
        return api;
    }

    @Override
    public Object visitApi(final RAMLParser.ApiContext ctx) {
        final EObject rootObject = scope.getResource().getContents().get(0);

        return withinScope(scope.with(rootObject), rootScope -> {
            ctx.annotationFacet().forEach(this::visitAnnotationFacet);
            ctx.attributeFacet().forEach(this::visitAttributeFacet);
            ctx.typesFacet().forEach(this::visitTypesFacet);
            ctx.baseUriFacet().forEach(this::visitBaseUriFacet);
            ctx.baseUriParametersFacet().forEach(this::visitBaseUriParametersFacet);
            ctx.resourceFacet().forEach(this::visitResourceFacet);

            return rootObject;
        });
    }

    @Override
    public Object visitBaseUriFacet(RAMLParser.BaseUriFacetContext ctx) {
        final String baseUriText = ctx.baseUri.getText();
        final UriTemplate uriTemplate = uriTemplateConstructor.parse(baseUriText, scope);
        scope.with(API__BASE_URI).setValue(uriTemplate, ctx.getStart());

        return uriTemplate;
    }


    @Override
    public Object visitBaseUriParametersFacet(RAMLParser.BaseUriParametersFacetContext baseUriParametersFacet) {
        return withinScope(scope.with(API__BASE_URI_PARAMETERS), baseUriParametersScope -> {
            final List<Object> baseUriParameters = baseUriParametersFacet.uriParameterFacets.stream()
                    .map(this::visitTypedElementFacet)
                    .collect(Collectors.toList());

            return baseUriParameters;
        });
    }

    @Override
    public Object visitResourceFacet(RAMLParser.ResourceFacetContext resourceFacet) {
        return withinScope(scope.with(RESOURCE_CONTAINER__RESOURCES), resourcesScope -> {
            final Resource resource = ResourcesFactory.eINSTANCE.createResource();
            resourcesScope.setValue(resource, resourceFacet.getStart());

            final UriTemplate relativeUri = uriTemplateConstructor.parse(resourceFacet.relativeUri.getText(), scope);
            resource.setRelativeUri(relativeUri);
            withinScope(scope.with(resource), resourceScope ->
                    resourceFacet.attributeFacet().stream()
                        .map(this::visitAttributeFacet)
                        .collect(Collectors.toList())
            );
            withinScope(scope.with(resource), resourceScope ->
                    resourceFacet.uriParametersFacet().stream()
                            .map(this::visitUriParametersFacet)
                            .collect(Collectors.toList())
            );
            withinScope(scope.with(resource), resourceScope ->
                    resourceFacet.resourceFacet().stream()
                            .map(this::visitResourceFacet)
                            .collect(Collectors.toList())
            );

            return resource;
        });
    }

    @Override
    public Object visitUriParametersFacet(RAMLParser.UriParametersFacetContext uriParametersFacet) {
        return withinScope(scope.with(RESOURCE__URI_PARAMETERS), baseUriParametersScope -> {
            final List<Object> baseUriParameters = uriParametersFacet.uriParameterFacets.stream()
                    .map(this::visitTypedElementFacet)
                    .collect(Collectors.toList());

            return baseUriParameters;
        });
    }
}

package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.resources.*;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.EObject;

import java.util.List;
import java.util.stream.Collectors;

import static io.vrap.rmf.raml.model.modules.ModulesPackage.Literals.API__BASE_URI;
import static io.vrap.rmf.raml.model.modules.ModulesPackage.Literals.API__BASE_URI_PARAMETERS;
import static io.vrap.rmf.raml.model.resources.ResourcesPackage.Literals.*;

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
            withinScope(scope.with(RESOURCE_CONTAINER__RESOURCES), resourcesScope ->
                    ctx.resourceFacet().stream().map(this::visitResourceFacet).collect(Collectors.toList()));

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
        final Resource resource = ResourcesFactory.eINSTANCE.createResource();
        scope.setValue(resource, resourceFacet.getStart());

        final UriTemplate relativeUri = uriTemplateConstructor.parse(resourceFacet.relativeUri.getText(), scope);
        resource.setRelativeUri(relativeUri);
        final Scope resourceScope = scope.with(resource);
        withinScope(resourceScope, attributeScope ->
                resourceFacet.attributeFacet().stream()
                        .map(this::visitAttributeFacet)
                        .collect(Collectors.toList())
        );
        withinScope(resourceScope.with(RESOURCE__URI_PARAMETERS), baseUriParametersScope ->
                resourceFacet.uriParametersFacet().stream()
                        .map(this::visitUriParametersFacet)
                        .collect(Collectors.toList())
        );
        withinScope(scope.with(RESOURCE_CONTAINER__RESOURCES), resoureResourcesScope ->
                resourceFacet.resourceFacet().stream()
                        .map(this::visitResourceFacet)
                        .collect(Collectors.toList())
        );
        withinScope(scope.with(RESOURCE__METHODS), resourceMethodsScope ->
                resourceFacet.methodFacet().stream()
                        .map(this::visitMethodFacet)
                        .collect(Collectors.toList())
        );

        return resource;
    }

    @Override
    public Object visitMethodFacet(RAMLParser.MethodFacetContext methodFacet) {
        final Method method = ResourcesFactory.eINSTANCE.createMethod();
        final String httpMethodText = methodFacet.httpMethod().getText();
        final HttpMethod httpMethod = (HttpMethod) ResourcesFactory.eINSTANCE.createFromString(HTTP_METHOD, httpMethodText);
        method.setMethod(httpMethod);
        scope.setValue(method, methodFacet.getStart());

        final Scope methodScope = scope.with(method);
        withinScope(methodScope, attributeScope ->
                methodFacet.attributeFacet().stream().map(this::visitAttributeFacet).collect(Collectors.toList()));

        withinScope(methodScope.with(METHOD__HEADERS), headersScope ->
                methodFacet.headersFacet().stream().map(this::visitHeadersFacet).collect(Collectors.toList()));

        withinScope(methodScope.with(METHOD__QUERY_PARAMETERS), queryParametersScope ->
                methodFacet.queryParametersFacet().stream().map(this::visitQueryParametersFacet).collect(Collectors.toList()));

        return method;
    }

    @Override
    public Object visitHeadersFacet(RAMLParser.HeadersFacetContext headersFacet) {
        final List<Object> baseUriParameters = ECollections.asEList(headersFacet.headerFacets.stream()
                .map(this::visitTypedElementFacet)
                .collect(Collectors.toList()));
        scope.setValue(baseUriParameters, headersFacet.getStart());

        return baseUriParameters;
    }

    @Override
    public Object visitQueryParametersFacet(RAMLParser.QueryParametersFacetContext queryParametersFacet) {
        final List<Object> baseUriParameters = ECollections.asEList(queryParametersFacet.queryParameters.stream()
                .map(this::visitTypedElementFacet)
                .collect(Collectors.toList()));
        scope.setValue(baseUriParameters, queryParametersFacet.getStart());

        return baseUriParameters;
    }

    @Override
    public Object visitUriParametersFacet(RAMLParser.UriParametersFacetContext uriParametersFacet) {
        final List<Object> baseUriParameters = ECollections.asEList(uriParametersFacet.uriParameterFacets.stream()
                .map(this::visitTypedElementFacet)
                .collect(Collectors.toList()));
        scope.setValue(baseUriParameters, uriParametersFacet.getStart());

        return baseUriParameters;
    }
}

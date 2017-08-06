package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.modules.Document;
import io.vrap.rmf.raml.model.modules.ModulesFactory;
import io.vrap.rmf.raml.model.resources.*;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.EObject;

import java.util.List;
import java.util.stream.Collectors;

import static io.vrap.rmf.raml.model.modules.ModulesPackage.Literals.*;
import static io.vrap.rmf.raml.model.resources.ResourcesPackage.Literals.*;

public class ApiConstructor extends BaseConstructor {
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
            ctx.documentationFacet().forEach(this::visitDocumentationFacet);
            ctx.annotationFacet().forEach(this::visitAnnotationFacet);
            ctx.attributeFacet().forEach(this::visitAttributeFacet);
            ctx.traitsFacet().forEach(this::visitTraitsFacet);
            ctx.typesFacet().forEach(this::visitTypesFacet);
            ctx.baseUriFacet().forEach(this::visitBaseUriFacet);
            ctx.baseUriParametersFacet().forEach(this::visitBaseUriParametersFacet);

            // order is relevant:
            // 1. construct security schemes
            ctx.securitySchemesFacet().forEach(this::visitSecuritySchemesFacet); // TODO move to first construction phase
            // 2. resolve secured by

            ctx.securedByFacet().forEach(this::visitSecuredByFacet);
            withinScope(scope.with(RESOURCE_CONTAINER__RESOURCES), resourcesScope ->
                    ctx.resourceFacet().stream().map(this::visitResourceFacet).collect(Collectors.toList()));

            return rootObject;
        });
    }

    @Override
    public Object visitDocumentationFacet(RAMLParser.DocumentationFacetContext documentationFacet) {
        return withinScope(scope.with(API__DOCUMENTATION), documentationScope ->
                documentationFacet.document().stream().map(this::visitDocument).collect(Collectors.toList())
        );
    }

    @Override
    public Object visitDocument(RAMLParser.DocumentContext ctx) {
        final Document document = ModulesFactory.eINSTANCE.createDocument();
        scope.setValue(document, ctx.getStart());

        return withinScope(scope.with(document), documentScope -> {
            ctx.attributeFacet().forEach(this::visitAttributeFacet);

            return document;
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
        return withinScope(scope.with(resource), resourceScope -> {
            resourceFacet.attributeFacet().forEach(this::visitAttributeFacet);
            resourceFacet.annotationFacet().forEach(this::visitAnnotationFacet);
            resourceFacet.securedByFacet().forEach(this::visitSecuredByFacet);

            resourceFacet.methodFacet().forEach(this::visitMethodFacet);
            resourceFacet.uriParametersFacet().forEach(this::visitUriParametersFacet);

            withinScope(scope.with(RESOURCE_CONTAINER__RESOURCES), resoureResourcesScope ->
                    resourceFacet.resourceFacet().stream()
                            .map(this::visitResourceFacet)
                            .collect(Collectors.toList())
            );

            return resource;
        });
    }

    @Override
    public Object visitMethodFacet(RAMLParser.MethodFacetContext methodFacet) {
        return withinScope(scope.with(RESOURCE__METHODS), methodsScope -> {
            final Method method = ResourcesFactory.eINSTANCE.createMethod();
            final String httpMethodText = methodFacet.httpMethod().getText();
            final HttpMethod httpMethod = (HttpMethod) ResourcesFactory.eINSTANCE.createFromString(HTTP_METHOD, httpMethodText);
            method.setMethod(httpMethod);
            methodsScope.setValue(method, methodFacet.getStart());

            withinScope(methodsScope.with(method), methodScope -> {
                        methodFacet.attributeFacet().forEach(this::visitAttributeFacet);
                        methodFacet.annotationFacet().forEach(this::visitAnnotationFacet);
                        methodFacet.securedByFacet().forEach(this::visitSecuredByFacet);
                        methodFacet.headersFacet().forEach(this::visitHeadersFacet);
                        methodFacet.queryParametersFacet().forEach(this::visitQueryParametersFacet);

                        withinScope(methodScope.with(METHOD__BODIES), bodiesScope -> {
                            methodFacet.bodyFacet().forEach(this::visitBodyFacet);
                            return null;
                        });

                        methodFacet.responsesFacet().forEach(this::visitResponsesFacet);

                        withinScope(methodScope.with(METHOD__IS), isScope -> {
                           methodFacet.isFacet().forEach(this::visitIsFacet);

                           return null;
                        });
                        return methodScope.eObject();
                    });

            return method;
        });
    }

    @Override
    public Object visitUriParametersFacet(RAMLParser.UriParametersFacetContext uriParametersFacet) {
        return withinScope(scope.with(RESOURCE__URI_PARAMETERS), uriParametersScope -> {
            final List<Object> uriParameters = ECollections.asEList(uriParametersFacet.uriParameterFacets.stream()
                    .map(this::visitTypedElementFacet)
                    .collect(Collectors.toList()));
            scope.setValue(uriParameters, uriParametersFacet.getStart());

            return uriParameters;
        });
    }
}

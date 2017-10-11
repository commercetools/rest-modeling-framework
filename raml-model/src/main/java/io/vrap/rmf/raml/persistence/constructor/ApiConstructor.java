package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.modules.Document;
import io.vrap.rmf.raml.model.resources.Resource;
import io.vrap.rmf.raml.model.resources.UriTemplate;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.EObject;

import java.util.List;
import java.util.function.Predicate;
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
            final Predicate<RAMLParser.ApiFacetsContext> isSecuritySchemesFacet =
                    apiFacetsContext -> apiFacetsContext.securitySchemesFacet() != null;

            // TODO move to first pass
            // order is relevant here: first create security schemes
            ctx.apiFacets().stream()
                    .filter(isSecuritySchemesFacet)
                    .forEach(this::visitApiFacets);

            ctx.apiFacets().stream()
                    .filter(isSecuritySchemesFacet.negate())
                    .forEach(this::visitApiFacets);

            return rootObject;
        });
    }

    @Override
    public Object visitApiFacets(RAMLParser.ApiFacetsContext ctx) {
        // TODO move creation of security schemes to 1 pass
        final RAMLParser.SecuritySchemesFacetContext securitySchemesFacet = ctx.securitySchemesFacet();
        if (securitySchemesFacet != null) {
            visitSecuritySchemesFacet(securitySchemesFacet);
        }

        for (int i = 0; i < ctx.getChildCount(); i++) {
            final ParseTree child = ctx.getChild(i);
            if (child != securitySchemesFacet) {
                child.accept(this);
            }
        }

        return scope.eObject();
    }

    @Override
    public Object visitDocumentationFacet(RAMLParser.DocumentationFacetContext documentationFacet) {
        return withinScope(scope.with(API__DOCUMENTATION), documentationScope ->
                documentationFacet.document().stream().map(this::visitDocument).collect(Collectors.toList())
        );
    }

    @Override
    public Object visitDocument(RAMLParser.DocumentContext ctx) {
        final Document document = create(DOCUMENT, ctx);
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
            scope.setValue(baseUriParameters, baseUriParametersFacet.getStart());
            return baseUriParameters;
        });
    }

    @Override
    public Object visitResourceFacet(RAMLParser.ResourceFacetContext resourceFacet) {
        return withinScope(scope.with(RESOURCE_CONTAINER__RESOURCES), resourcesScope -> {
            final Resource resource = create(RESOURCE, resourceFacet);
            resourcesScope.setValue(resource, resourceFacet.getStart());

            final UriTemplate relativeUri = uriTemplateConstructor.parse(resourceFacet.relativeUri.getText(), resourcesScope);
            resource.setRelativeUri(relativeUri);
            return withinScope(resourcesScope.with(resource), resourceScope -> {
                resourceFacet.attributeFacet().forEach(this::visitAttributeFacet);
                resourceFacet.annotationFacet().forEach(this::visitAnnotationFacet);
                resourceFacet.securedByFacet().forEach(this::visitSecuredByFacet);

                resourceFacet.methodFacet().forEach(this::visitMethodFacet);
                resourceFacet.uriParametersFacet().forEach(this::visitUriParametersFacet);
                resourceFacet.resourceFacet().forEach(this::visitResourceFacet);

                resourceFacet.isFacet().forEach(this::visitIsFacet);
                resourceFacet.resourceTypeFacet().forEach(this::visitResourceTypeFacet);

                return resource;
            });
        });
    }

    @Override
    public Object visitUriParametersFacet(RAMLParser.UriParametersFacetContext uriParametersFacet) {
        return withinScope(scope.with(RESOURCE_BASE__URI_PARAMETERS), uriParametersScope -> {
            final List<Object> uriParameters = ECollections.asEList(uriParametersFacet.uriParameterFacets.stream()
                    .map(this::visitTypedElementFacet)
                    .collect(Collectors.toList()));
            scope.setValue(uriParameters, uriParametersFacet.getStart());

            return uriParameters;
        });
    }
}

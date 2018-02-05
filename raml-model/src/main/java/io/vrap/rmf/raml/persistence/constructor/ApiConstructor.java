package io.vrap.rmf.raml.persistence.constructor;

import com.damnhandy.uri.template.MalformedUriTemplateException;
import com.damnhandy.uri.template.UriTemplate;
import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.modules.Document;
import io.vrap.rmf.raml.model.resources.AnnotatedUriTemplate;
import io.vrap.rmf.raml.model.resources.Resource;
import io.vrap.rmf.raml.model.resources.ResourcesFactory;
import io.vrap.rmf.raml.model.resources.ResourcesPackage;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.EObject;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static io.vrap.rmf.raml.model.modules.ModulesPackage.Literals.*;
import static io.vrap.rmf.raml.model.resources.ResourcesPackage.Literals.*;

public class ApiConstructor extends BaseConstructor {

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

            ctx.apiFacets().forEach(this::visitApiFacets);

            return rootObject;
        });
    }

    @Override
    public Object visitDocumentationFacet(RAMLParser.DocumentationFacetContext documentationFacet) {
        return withinScope(scope.with(API_BASE__DOCUMENTATION), documentationScope ->
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
        final String baseUriText = ctx.baseUri.id(0).getText();
        final AnnotatedUriTemplate annotatedUriTemplate = create(ANNOTATED_URI_TEMPLATE, ctx);
        try {
            final UriTemplate uriTemplate = (UriTemplate) ResourcesFactory.eINSTANCE
                    .createFromString(ResourcesPackage.Literals.URI_TEMPLATE, baseUriText);
            annotatedUriTemplate.setValue(uriTemplate);
            scope.with(API_BASE__BASE_URI).setValue(annotatedUriTemplate, ctx.getStart());
        } catch (final MalformedUriTemplateException uriTemplateException) {
            scope.addError(uriTemplateException.getMessage(), ctx);
            return null;
        }
        return withinScope(scope.with(annotatedUriTemplate), annotatedUriTemplateScope -> {
            ctx.baseUri.annotationFacet().forEach(this::visitAnnotationFacet);
            return annotatedUriTemplate;
        });
    }


    @Override
    public Object visitBaseUriParametersFacet(RAMLParser.BaseUriParametersFacetContext baseUriParametersFacet) {
        return withinScope(scope.with(API_BASE__BASE_URI_PARAMETERS), baseUriParametersScope -> {
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

            final UriTemplate relativeUri = (UriTemplate) ResourcesFactory.eINSTANCE.createFromString(ResourcesPackage.Literals.URI_TEMPLATE, resourceFacet.relativeUri.getText());
            resource.setRelativeUri(relativeUri);
            return withinScope(resourcesScope.with(resource), resourceScope -> {
                resourceFacet.resourceBaseFacet().forEach(this::visitResourceBaseFacet);
                resourceFacet.resourceFacet().forEach(this::visitResourceFacet);

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

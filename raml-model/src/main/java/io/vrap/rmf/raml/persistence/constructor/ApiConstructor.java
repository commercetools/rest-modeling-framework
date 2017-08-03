package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.resources.*;
import io.vrap.rmf.raml.model.types.BuiltinType;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.List;
import java.util.stream.Collectors;

import static io.vrap.rmf.raml.model.modules.ModulesPackage.Literals.API__BASE_URI;
import static io.vrap.rmf.raml.model.modules.ModulesPackage.Literals.API__BASE_URI_PARAMETERS;
import static io.vrap.rmf.raml.model.resources.ResourcesPackage.Literals.*;
import static io.vrap.rmf.raml.model.types.TypesPackage.Literals.INLINE_TYPE_CONTAINER__INLINE_TYPES;
import static io.vrap.rmf.raml.model.types.TypesPackage.Literals.TYPED_ELEMENT__TYPE;

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
        withinScope(resourceScope, s ->
                resourceFacet.annotationFacet().stream()
                        .map(this::visitAnnotationFacet)
                        .collect(Collectors.toList())
        );
        withinScope(resourceScope, s ->
            resourceFacet.securedByFacet().stream()
                    .map(this::visitSecuredByFacet)
                    .collect(Collectors.toList())
        );

        // TODO cleanup
        withinScope(resourceScope.with(RESOURCE__URI_PARAMETERS), baseUriParametersScope ->
                resourceFacet.uriParametersFacet().stream()
                        .map(this::visitUriParametersFacet)
                        .collect(Collectors.toList())
        );
        // TODO cleanup
        withinScope(scope.with(RESOURCE_CONTAINER__RESOURCES), resoureResourcesScope ->
                resourceFacet.resourceFacet().stream()
                        .map(this::visitResourceFacet)
                        .collect(Collectors.toList())
        );
        resourceFacet.methodFacet().stream()
                .map(this::visitMethodFacet)
                .collect(Collectors.toList());

        return resource;
    }

    @Override
    public Object visitMethodFacet(RAMLParser.MethodFacetContext methodFacet) {
        return withinScope(scope.with(RESOURCE__METHODS), methodsScope -> {
            final Method method = ResourcesFactory.eINSTANCE.createMethod();
            final String httpMethodText = methodFacet.httpMethod().getText();
            final HttpMethod httpMethod = (HttpMethod) ResourcesFactory.eINSTANCE.createFromString(HTTP_METHOD, httpMethodText);
            method.setMethod(httpMethod);
            methodsScope.setValue(method, methodFacet.getStart());

            final Scope methodScope = methodsScope.with(method);
            withinScope(methodScope, attributeScope ->
                    methodFacet.attributeFacet().stream().map(this::visitAttributeFacet).collect(Collectors.toList()));
            withinScope(methodScope, annotationsScope ->
                    methodFacet.annotationFacet().stream().map(this::visitAnnotationFacet).collect(Collectors.toList()));
            withinScope(methodScope, s ->
                    methodFacet.securedByFacet().stream()
                            .map(this::visitSecuredByFacet)
                            .collect(Collectors.toList())
            );

            withinScope(methodScope.with(METHOD__HEADERS), headersScope ->
                    methodFacet.headersFacet().stream().map(this::visitHeadersFacet).collect(Collectors.toList()));

            withinScope(methodScope.with(METHOD__QUERY_PARAMETERS), queryParametersScope ->
                    methodFacet.queryParametersFacet().stream().map(this::visitQueryParametersFacet).collect(Collectors.toList()));

            withinScope(methodScope.with(METHOD__BODIES), bodiesScope ->
                    methodFacet.bodyFacet().stream().map(this::visitBodyFacet).collect(Collectors.toList()));
            return method;
        });
    }

    @Override
    public Object visitBodyContentTypeFacet(RAMLParser.BodyContentTypeFacetContext bodyContentType) {
        final BodyType bodyType = ResourcesFactory.eINSTANCE.createBodyType();
        scope.setValue(bodyType, bodyContentType.getStart());
        if (bodyContentType.contentType != null) {
            final String contentType = bodyContentType.contentType.getText();
            bodyType.getContentTypes().add(contentType);
        }
        final Scope bodyTypeScope = scope.with(bodyType);
        EObject type;
        if (bodyContentType.typeFacet().size() == 1) {
            type = (EObject) visitTypeFacet(bodyContentType.typeFacet(0));
            scope.with(bodyType, TYPED_ELEMENT__TYPE).setValue(type, bodyContentType.getStart());
        } else if (bodyContentType.propertiesFacet().size() == 1) {
            type = scope.getEObjectByName(BuiltinType.OBJECT.getName());
        } else {
            type = scope.getEObjectByName(BuiltinType.ANY.getName());
        }
        // inline type declaration
        if (bodyContentType.attributeFacet().size() > 0) {
            type = EcoreUtil.create(type.eClass());
            bodyTypeScope.addValue(INLINE_TYPE_CONTAINER__INLINE_TYPES, type);
            withinScope(scope.with(type),
                    inlineTypeDeclarationScope ->
                            bodyContentType.attributeFacet().stream()
                                    .map(this::visitAttributeFacet)
                                    .collect(Collectors.toList()));
        }
        bodyTypeScope.with(TYPED_ELEMENT__TYPE).setValue(type, bodyContentType.getStart());

        bodyTypeScope.with(TYPED_ELEMENT__TYPE).setValue(type, bodyContentType.getStart());
        bodyContentType.annotationFacet().forEach(this::visitAnnotationFacet);

        return bodyType;
    }

    @Override
    public Object visitBodyTypeFacet(RAMLParser.BodyTypeFacetContext bodyTypeFacet) {
        final BodyType bodyType = ResourcesFactory.eINSTANCE.createBodyType();
        scope.setValue(bodyType, bodyTypeFacet.getStart());
        final Scope bodyTypeScope = scope.with(bodyType);
        EObject type;
        if (bodyTypeFacet.typeFacet().size() == 1) {
            type = (EObject) visitTypeFacet(bodyTypeFacet.typeFacet(0));
            scope.with(bodyType, TYPED_ELEMENT__TYPE).setValue(type, bodyTypeFacet.getStart());
        } else if (bodyTypeFacet.propertiesFacet().size() == 1) {
            type = scope.getEObjectByName(BuiltinType.OBJECT.getName());
        } else {
            type = scope.getEObjectByName(BuiltinType.ANY.getName());
        }
        // inline type declaration
        if (bodyTypeFacet.attributeFacet().size() > 0) {
            type = EcoreUtil.create(type.eClass());
            bodyTypeScope.addValue(INLINE_TYPE_CONTAINER__INLINE_TYPES, type);
            withinScope(scope.with(type),
                    inlineTypeDeclarationScope ->
                            bodyTypeFacet.attributeFacet().stream()
                                    .map(this::visitAttributeFacet)
                                    .collect(Collectors.toList()));
        }
        bodyTypeScope.with(TYPED_ELEMENT__TYPE).setValue(type, bodyTypeFacet.getStart());

        bodyTypeFacet.annotationFacet().forEach(this::visitAnnotationFacet);
        bodyTypeFacet.propertiesFacet().forEach(this::visitPropertiesFacet);

        return bodyType;
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

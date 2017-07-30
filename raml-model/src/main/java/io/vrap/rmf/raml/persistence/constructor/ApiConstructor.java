package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.modules.UriTemplate;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import org.eclipse.emf.ecore.EObject;

import java.util.List;
import java.util.stream.Collectors;

import static io.vrap.rmf.raml.model.modules.ModulesPackage.Literals.API__BASE_URI;
import static io.vrap.rmf.raml.model.modules.ModulesPackage.Literals.API__BASE_URI_PARAMETERS;

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

            return rootObject;
        });
    }

    @Override
    public Object visitBaseUriFacet(RAMLParser.BaseUriFacetContext ctx) {
        final String baseUriText = ctx.baseUri.getText();
        final UriTemplate uriTemplate = uriTemplateConstructor.parse(baseUriText);
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

}

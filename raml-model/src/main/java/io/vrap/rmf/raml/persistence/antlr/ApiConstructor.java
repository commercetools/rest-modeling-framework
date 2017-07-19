package io.vrap.rmf.raml.persistence.antlr;

import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.modules.ModulesFactory;
import org.antlr.v4.runtime.Token;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.List;
import java.util.stream.Collectors;

import static io.vrap.rmf.raml.model.modules.ModulesPackage.Literals.API;

public class ApiConstructor extends RAMLBaseVisitor<Object> {
    private final static ModulesFactory FACTORY = ModulesFactory.eINSTANCE;

    @Override
    public Api visitApi(final RAMLParser.ApiContext ctx) {
        final Api api = FACTORY.createApi();
        for (final RAMLParser.Simple_api_facetContext simpleApiFacet : ctx.simple_api_facet()) {
            final String featureName = simpleApiFacet.facet.getText();
            final EAttribute eAttribute = (EAttribute) API.getEStructuralFeature(featureName);
            final Object value = EcoreUtil.createFromString(eAttribute.getEAttributeType(), simpleApiFacet.value.getText());
            api.eSet(eAttribute, value);
        }
        for (final RAMLParser.List_api_facetContext listApiFacet : ctx.list_api_facet()) {
            final String featureName = listApiFacet.facet.getText();
            final EAttribute eAttribute = (EAttribute) API.getEStructuralFeature(featureName);
            final List<Object> values = listApiFacet.values.stream()
                    .map(Token::getText)
                    .map(v -> EcoreUtil.createFromString(eAttribute.getEAttributeType(), v))
                    .collect(Collectors.toList());
            api.eSet(eAttribute, values);
        }
        return api;
    }

    @Override
    public Object visitSimple_api_facet(final RAMLParser.Simple_api_facetContext simpleApiFacet) {
        final String featureName = simpleApiFacet.facet.getText();
        final EAttribute eAttribute = (EAttribute) API.getEStructuralFeature(featureName);
        final Object value = EcoreUtil.createFromString(eAttribute.getEAttributeType(), simpleApiFacet.value.getText());
        return value;
    }
}

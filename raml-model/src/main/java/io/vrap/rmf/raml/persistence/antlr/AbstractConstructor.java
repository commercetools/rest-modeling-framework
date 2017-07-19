package io.vrap.rmf.raml.persistence.antlr;

import io.vrap.rmf.raml.model.modules.ModulesFactory;
import io.vrap.rmf.raml.persistence.constructor.Scope;
import org.antlr.v4.runtime.Token;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Abstract base class for antlr based constructors.
 */
public abstract class AbstractConstructor extends RAMLBaseVisitor<Object> {
    protected final static ModulesFactory FACTORY = ModulesFactory.eINSTANCE;
    protected final Scope scope;

    protected AbstractConstructor(final Scope scope) {
        this.scope = scope;
    }

    protected void constructAttribute(final EObject eObject, final Token facetToken, final List<Token> valueTokens) {
        final EClass eClass = eObject.eClass();
        final String featureName = facetToken.getText();
        final EAttribute eAttribute = (EAttribute) eClass.getEStructuralFeature(featureName);
        final List<Object> values = valueTokens.stream()
                .map(Token::getText)
                .map(v -> EcoreUtil.createFromString(eAttribute.getEAttributeType(), v))
                .collect(Collectors.toList());
        eObject.eSet(eAttribute, values);
    }

    protected void constructAttribute(final EObject eObject, final Token facetToken, final Token valueToken) {
        final EClass eClass = eObject.eClass();
        final String featureName = facetToken.getText();
        final EAttribute eAttribute = (EAttribute) eClass.getEStructuralFeature(featureName);
        final Object value = EcoreUtil.createFromString(eAttribute.getEAttributeType(), valueToken.getText());
        eObject.eSet(eAttribute, value);
    }
}

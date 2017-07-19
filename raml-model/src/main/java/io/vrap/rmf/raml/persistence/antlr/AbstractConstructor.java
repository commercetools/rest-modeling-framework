package io.vrap.rmf.raml.persistence.antlr;

import io.vrap.rmf.raml.persistence.constructor.Scope;
import org.antlr.v4.runtime.Token;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Abstract base class for antlr based constructors.
 */
public abstract class AbstractConstructor extends RAMLBaseVisitor<Object> {
    protected final Scope scope;

    protected AbstractConstructor(final Scope scope) {
        this.scope = scope;
    }

    /**
     * Sets an attribute given by the attribute facet on the given eobject.
     *
     * @param attributeFacet the attribute facet
     * @param eObject        the object to set the attribute
     */
    protected void setAttribute(final RAMLParser.AttributeFacetContext attributeFacet, final EObject eObject) {
        final EClass eClass = eObject.eClass();
        final String attributeName = attributeFacet.facet.getText();
        final EAttribute eAttribute = eClass.getEAllAttributes().stream()
                .filter(a -> a.getName().equals(attributeName))
                .findFirst()
                .orElse(null); // TODO: handle unknown attribute

        if (attributeFacet.facetValue().value != null) {
            setAttribute(eObject, eAttribute, attributeFacet.facetValue().value);
        } else {
            setAttribute(eObject, eAttribute, attributeFacet.facetValue().values);
        }
    }

    private void setAttribute(final EObject eObject, final EAttribute eAttribute, final List<Token> valueTokens) {
        final List<Object> values = valueTokens.stream()
                .map(Token::getText)
                .map(v -> EcoreUtil.createFromString(eAttribute.getEAttributeType(), v))
                .collect(Collectors.toList());

        eObject.eSet(eAttribute, values);
    }

    private void setAttribute(final EObject eObject, final EAttribute eAttribute, final Token valueToken) {
        final Object value = EcoreUtil.createFromString(eAttribute.getEAttributeType(), valueToken.getText());

        if (eAttribute.isMany()) {
            eObject.eSet(eAttribute, Collections.singletonList(value));
        } else {
            eObject.eSet(eAttribute, value);
        }
    }
}

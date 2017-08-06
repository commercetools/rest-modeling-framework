package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.persistence.antlr.RAMLBaseVisitor;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Abstract base visitor which provides scoping.
 */
abstract class AbstractScopedVisitor<T> extends RAMLBaseVisitor<T> {
    protected Scope scope;

    protected <T> T withinScope(final Scope scope, final Function<Scope, T> within) {
        pushScope(scope);

        T value = within.apply(this.scope);

        this.scope = popScope();

        return value;
    }

    protected Scope pushScope(final Scope scope) {
        return this.scope = scope;
    }

    protected Scope popScope() {
        return scope.getParent();
    }


    /**
     * Sets an attribute given by the attribute facet on the given eobject.
     *
     * @param attributeFacet the attribute facet
     * @param eObject        the object to set the attribute
     */
    protected Object setAttribute(final RAMLParser.AttributeFacetContext attributeFacet, final EObject eObject) {
        final EClass eClass = eObject.eClass();
        final String attributeName = attributeFacet.facet.getText();
        final EAttribute eAttribute = eClass.getEAllAttributes().stream()
                .filter(a -> a.getName().equals(attributeName))
                .findFirst()
                .orElse(null);

        final Object value;
        if (eAttribute == null) {
            scope.addError("Unknown attribute {0}", attributeName);
            value = null;
        } else {
            value = attributeFacet.facetValue().value == null ?
                    attributeFacet.facetValue().values :
                    attributeFacet.facetValue().value;

            if (attributeFacet.facetValue().value != null) {
                setAttribute(eObject, eAttribute, attributeFacet.facetValue().value);
            } else {
                setAttribute(eObject, eAttribute, attributeFacet.facetValue().values);
            }
        }
        return value;
    }

    private void setAttribute(final EObject eObject, final EAttribute eAttribute, final List<RAMLParser.IdContext> valueTokens) {
        if (eAttribute.isMany()) {
            final List<Object> values = valueTokens.stream()
                    .map(RAMLParser.IdContext::getText)
                    .map(v -> EcoreUtil.createFromString(eAttribute.getEAttributeType(), v))
                    .collect(Collectors.toList());

            eObject.eSet(eAttribute, values);
        } else {
            final String messagePattern = "Trying to set attribute {0} with many values";
            if (valueTokens.isEmpty()) {
                scope.addError(messagePattern, eAttribute);
            } else {
                scope.addError(messagePattern + " at {1}", eAttribute, valueTokens.get(0).getStart());
            }
        }
    }

    private void setAttribute(final EObject eObject, final EAttribute eAttribute, final RAMLParser.IdContext valueToken) {
        try {
            final Object value = EcoreUtil.createFromString(eAttribute.getEAttributeType(), valueToken.getText());
            if (eAttribute.isMany()) {
                eObject.eSet(eAttribute, Collections.singletonList(value));
            } else {
                eObject.eSet(eAttribute, value);
            }
        } catch (IllegalArgumentException e) {
            scope.addError(e.getMessage(), valueToken.getStart());
        }
    }
}

package io.vrap.rmf.raml.persistence.constructor;

import io.vrap.rmf.raml.model.util.RAMLTokenAdapter;
import io.vrap.rmf.raml.persistence.antlr.RAMLBaseVisitor;
import io.vrap.rmf.raml.persistence.antlr.RAMLParser;
import io.vrap.rmf.raml.persistence.antlr.RamlToken;
import org.antlr.v4.runtime.Token;
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
            scope.addError("Unknown attribute {0} at {1}", attributeName, attributeFacet.getStart());
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
                    .map(v -> createFromString(eAttribute, v))
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
        final Object value = createFromString(eAttribute, valueToken);
        if (eAttribute.isMany()) {
            eObject.eSet(eAttribute, Collections.singletonList(value));
        } else {
            eObject.eSet(eAttribute, value);
        }
    }

    private Object createFromString(final EAttribute eAttribute, final RAMLParser.IdContext valueToken) {
        try {
            return EcoreUtil.createFromString(eAttribute.getEAttributeType(), valueToken.getText());
        } catch (IllegalArgumentException e) {
            scope.addError("{0} at {1}", e.getMessage(), valueToken.getStart());
            return null;
        }
    }

    protected <T extends EObject> T create(final EClass eClass, final Token token) {
        final T newEObject = (T) EcoreUtil.create(eClass);
        final RAMLTokenAdapter adapter = RAMLTokenAdapter.of((RamlToken) token);
        newEObject.eAdapters().add(adapter);
        return newEObject;
    }
}

package io.vrap.rmf.raml.validation;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Since EMF only allows one validator per EPackage, we have
 * to chain different validators.
 */
public class CompositeValidator implements EValidator {
    private final List<EValidator> validators = new ArrayList<>();

    public void add(final EValidator validator) {
        validators.add(validator);
    }

    @Override
    public boolean validate(final EObject eObject, final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        final boolean isValid = validators.stream()
                .map(eValidator -> eValidator.validate(eObject, diagnostics, context))
                .reduce((r1, r2) -> r1 && r2)
                .orElse(true);
        return isValid;
    }

    @Override
    public boolean validate(EClass eClass, EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
        final boolean isValid = validators.stream()
                .map(eValidator -> eValidator.validate(eClass, eObject, diagnostics, context))
                .reduce((r1, r2) -> r1 && r2)
                .orElse(true);
        return isValid;
    }

    @Override
    public boolean validate(EDataType eDataType, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
        final boolean isValid = validators.stream()
                .map(eValidator -> eValidator.validate(eDataType, value, diagnostics, context))
                .reduce((r1, r2) -> r1 && r2)
                .orElse(true);
        return isValid;
    }
}

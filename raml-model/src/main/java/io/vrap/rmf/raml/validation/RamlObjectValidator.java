package io.vrap.rmf.raml.validation;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A generic validator that checks common constraints.
 */
public class RamlObjectValidator implements EValidator {
    @Override
    public boolean validate(final EObject eObject, final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return validate(eObject.eClass(), eObject, diagnostics, context);
    }

    @Override
    public boolean validate(final EClass eClass, final EObject eObject, final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        final List<Diagnostic> missingRequiredAttributes = requiredAttributesMustBeSet(eClass, eObject, diagnostics);
        missingRequiredAttributes.addAll(requiredStringAttributesMustBeNonEmpty(eClass, eObject, diagnostics));

        return missingRequiredAttributes.isEmpty();
    }

    private List<Diagnostic> requiredAttributesMustBeSet(final EClass eClass, final EObject eObject, final DiagnosticChain diagnostics) {
        final List<Diagnostic> missingRequiredAttributes = eClass.getEAllAttributes().stream()
                .filter(eAttribute -> eAttribute.isRequired() && !eAttribute.isMany() && !eObject.eIsSet(eAttribute))
                .map(eAttribute -> error("Facet '" + eAttribute.getName() + "' is required.", eObject)).collect(Collectors.toList());
        missingRequiredAttributes.forEach(diagnostics::add);
        return missingRequiredAttributes;
    }

    private List<Diagnostic> requiredStringAttributesMustBeNonEmpty(final EClass eClass, final EObject eObject, final DiagnosticChain diagnostics) {
        final List<Diagnostic> missingRequiredAttributes = eClass.getEAllAttributes().stream()
                .filter(eAttribute -> eAttribute.isRequired() && !eAttribute.isMany()
                        && eAttribute.getEAttributeType().getInstanceClass() == String.class
                        && eObject.eIsSet(eAttribute) && ((String) eObject.eGet(eAttribute)).isEmpty())
                .map(eAttribute -> error("Facet '" + eAttribute.getName() + "' must be non-empty.", eObject)).collect(Collectors.toList());
        missingRequiredAttributes.forEach(diagnostics::add);
        return missingRequiredAttributes;
    }

    @Override
    public boolean validate(EDataType eDataType, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    private Diagnostic error(final String message, final EObject eObject) {
        return new BasicDiagnostic(Diagnostic.ERROR, null, -1, message, new Object[] { eObject });
    }
}

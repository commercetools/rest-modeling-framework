package io.vrap.rmf.raml.validation;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;

import java.util.Map;

/**
 * Abstract base class for validators.
 */
public abstract class AbstractRamlValidator implements EValidator {

    @Override
    public final boolean validate(final EObject eObject, final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return validate(eObject.eClass(), eObject, diagnostics, context);
    }

    protected Diagnostic error(final String message, final EObject eObject) {
        return new BasicDiagnostic(Diagnostic.ERROR, null, -1, message, new Object[] { eObject });
    }

    @Override
    public boolean validate(EDataType eDataType, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }
}

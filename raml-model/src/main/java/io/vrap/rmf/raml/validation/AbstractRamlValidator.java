package io.vrap.rmf.raml.validation;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;

import java.util.Map;

/**
 * Abstract base class for validators.
 */
abstract class AbstractRamlValidator implements EValidator, DiagnosticsCreator {

    @Override
    public final boolean validate(final EObject eObject, final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return validate(eObject.eClass(), eObject, diagnostics, context);
    }

    @Override
    public boolean validate(final EDataType eDataType, final Object value,
                            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return true;
    }
}

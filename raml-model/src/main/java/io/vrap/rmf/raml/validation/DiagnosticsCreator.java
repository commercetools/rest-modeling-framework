package io.vrap.rmf.raml.validation;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;

import java.text.MessageFormat;

public interface DiagnosticsCreator {

    default Diagnostic error(final EObject eObject, final String messagePattern, final Object... messageArgs) {
        return create(Diagnostic.ERROR, eObject, messagePattern, messageArgs);
    }

    default Diagnostic warning(final EObject eObject, final String messagePattern, final Object... messageArgs) {
        return create(Diagnostic.WARNING, eObject, messagePattern, messageArgs);
    }

    default Diagnostic create(final int severity, final EObject eObject, final String messagePattern, final Object... messageArgs) {
        final Violation violation = new Violation(severity, (Class<DiagnosticsCreator>) this.getClass(), eObject, messagePattern, messageArgs);
        return new BasicDiagnostic(severity, null, -1, violation.getMessage(), new Object[] { eObject, this.getClass().getSimpleName(), violation });
    }
}

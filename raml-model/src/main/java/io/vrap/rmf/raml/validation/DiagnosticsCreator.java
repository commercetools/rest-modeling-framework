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
        final String message = MessageFormat.format(messagePattern, messageArgs);
        return new BasicDiagnostic(severity, null, -1, message, new Object[] { eObject });
    }
}

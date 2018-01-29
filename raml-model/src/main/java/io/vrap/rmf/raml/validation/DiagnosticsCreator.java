package io.vrap.rmf.raml.validation;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;

import java.text.MessageFormat;

public interface DiagnosticsCreator {

    default Diagnostic error(final EObject eObject, final String messagePattern, final Object... messageArgs) {
        final String message = MessageFormat.format(messagePattern, messageArgs);
        return new BasicDiagnostic(Diagnostic.ERROR, null, -1, message, new Object[] { eObject });
    }
}

package io.vrap.rmf.raml.validation;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;

import java.util.Map;

public interface RamlValidator extends EValidator, DiagnosticsCreator {
    @Override
    boolean validate(EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context);

    @Override
    boolean validate(EDataType eDataType, Object value,
                     DiagnosticChain diagnostics, Map<Object, Object> context);
}

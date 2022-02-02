package io.vrap.rmf.raml.validation;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExampleValidator extends AbstractRamlValidator implements ResolvedRamlValidator {
    private final List<ValidatingTypesSwitch> validators = Arrays.asList(
            new TypesValidator.ExamplesValidator());

    @Override
    public boolean validate(EClass eClass, EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
        final List<Diagnostic> validationResults = validators.stream()
             .flatMap(validator -> validator.doSwitch(eObject).stream())
             .collect(Collectors.toList());
        validationResults.forEach(diagnostics::add);

        return validationResults.isEmpty();

    }
}

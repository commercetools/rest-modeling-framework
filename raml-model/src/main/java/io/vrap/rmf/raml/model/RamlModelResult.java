package io.vrap.rmf.raml.model;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the result of loading a RAML file.
 */
public class RamlModelResult<T extends EObject> {
    private final List<RamlDiagnostic> validationResults;

    private final T rootObject;

    private RamlModelResult(final List<RamlDiagnostic> validationResults, final T rootObject) {
        this.validationResults = validationResults;
        this.rootObject = rootObject;
    }

    public List<RamlDiagnostic> getValidationResults() {
        return validationResults;
    }

    public T getRootObject() {
        return rootObject;
    }

    public static <T extends EObject> RamlModelResult of(final List<Diagnostic> diagnostics, final T eObject) {
        final List<RamlDiagnostic> validationResults = diagnostics.stream()
                .filter(RamlDiagnostic.class::isInstance)
                .map(RamlDiagnostic.class::cast)
                .collect(Collectors.toList());

        return new RamlModelResult(validationResults, eObject);
    }
}

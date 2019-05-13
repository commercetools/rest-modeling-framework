package io.vrap.rmf.raml.validation;

import io.vrap.rmf.raml.model.values.ValuesPackage;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EObjectValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A generic validator that checks common constraints.
 */
class RamlObjectValidator extends AbstractRamlValidator {
    private EObjectValidator eObjectValidator = new EObjectValidator();
    @Override
    public boolean validate(final EClass eClass, final EObject eObject, final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        final List<Diagnostic> validationErrors = new ArrayList<>();

        validationErrors.addAll(requiredAttributesMustBeSet(eClass, eObject, diagnostics));
        validationErrors.addAll(requiredStringAttributesMustBeNonEmpty(eClass, eObject, diagnostics));
        validationErrors.addAll(validatePositiveIntegerAttributes(eClass, eObject, diagnostics));
        validationErrors.addAll(validateUnsignedIntegerAttributes(eClass, eObject, diagnostics));

        validationErrors.forEach(diagnostics::add);

        return validationErrors.isEmpty() && eObjectValidator.validate(eClass, eObject, diagnostics, context);
    }

    private List<Diagnostic> requiredAttributesMustBeSet(final EClass eClass, final EObject eObject, final DiagnosticChain diagnostics) {
        final List<Diagnostic> missingRequiredAttributes = eClass.getEAllAttributes().stream()
                .filter(eAttribute -> eAttribute.isRequired() && !eAttribute.isMany() && !eObject.eIsSet(eAttribute))
                .map(eAttribute -> error( eObject,"Facet ''{0}'' is required.", eAttribute.getName())).collect(Collectors.toList());

        return missingRequiredAttributes;
    }

    private List<Diagnostic> requiredStringAttributesMustBeNonEmpty(final EClass eClass, final EObject eObject, final DiagnosticChain diagnostics) {
        final List<Diagnostic> missingRequiredAttributes = eClass.getEAllAttributes().stream()
                .filter(eAttribute -> eAttribute.isRequired() && !eAttribute.isMany()
                        && eAttribute.getEAttributeType().getInstanceClass() == String.class
                        && eObject.eIsSet(eAttribute) && ((String) eObject.eGet(eAttribute)).isEmpty())
                .map(eAttribute -> error( eObject,"Facet ''{0}'' must be non-empty.", eAttribute.getName())).collect(Collectors.toList());

        return missingRequiredAttributes;
    }

    private List<Diagnostic> validatePositiveIntegerAttributes(final EClass eClass, final EObject eObject, final DiagnosticChain diagnostics) {
        final List<Diagnostic> missingRequiredAttributes = eClass.getEAllAttributes().stream()
                .filter(eAttribute -> !eAttribute.isMany()
                        && eAttribute.getEAttributeType() == ValuesPackage.Literals.POSITIVE_INTEGER
                        && eObject.eIsSet(eAttribute) && ((Integer) eObject.eGet(eAttribute)) <= 0)
                .map(eAttribute -> error(eObject, "Facet ''{0}'' must > 0.", eAttribute.getName())).collect(Collectors.toList());

        return missingRequiredAttributes;
    }

    private List<Diagnostic> validateUnsignedIntegerAttributes(final EClass eClass, final EObject eObject, final DiagnosticChain diagnostics) {
        final List<Diagnostic> missingRequiredAttributes = eClass.getEAllAttributes().stream()
                .filter(eAttribute -> !eAttribute.isMany()
                        && eAttribute.getEAttributeType() == ValuesPackage.Literals.UNSIGNED_INTEGER
                        && eObject.eGet(eAttribute) != null && ((Integer) eObject.eGet(eAttribute)) < 0)
                .map(eAttribute -> error(eObject, "Facet ''{0}'' must >= 0.", eAttribute.getName())).collect(Collectors.toList());

        return missingRequiredAttributes;
    }
}

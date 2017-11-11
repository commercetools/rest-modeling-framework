package io.vrap.rmf.raml.validation;

import io.vrap.rmf.raml.model.facets.*;
import io.vrap.rmf.raml.model.facets.util.FacetsSwitch;
import io.vrap.rmf.raml.model.types.AnyAnnotationType;
import io.vrap.rmf.raml.model.types.AnyType;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class InstanceValidator {

    public List<Diagnostic> validate(final Instance instance, final AnyType anyType) {
        return validateInternal(instance, anyType);
    }

    public List<Diagnostic> validate(final Instance instance, final AnyAnnotationType anyAnnotationType) {
        return validateInternal(instance, anyAnnotationType);
    }

    private List<Diagnostic> validateInternal(final Instance instance, final EObject type) {
        final InstanceValidatingVisitor validatingVisitor = new InstanceValidatingVisitor(type);
        final List<Diagnostic> validationResults = validatingVisitor.doSwitch(instance);
        return validationResults;
    }

    private final static class InstanceValidatingVisitor extends FacetsSwitch<List<Diagnostic>> {
        private final Stack<EObject> types = new Stack<>();

        public InstanceValidatingVisitor(final EObject type) {
            types.push(type);
        }

        @Override
        public List<Diagnostic> defaultCase(EObject object) {
            return Collections.emptyList();
        }

        @Override
        public List<Diagnostic> caseStringInstance(final StringInstance stringInstance) {
            final List<Diagnostic> validationResults = new ArrayList<>();

            if (typeInstanceOf(StringTypeFacet.class)) {
                final StringTypeFacet stringType = (StringTypeFacet) types.peek();
                final String value = stringInstance.getValue();

                if (stringType.getMinLength() != null && value.length() < stringType.getMinLength()) {
                    validationResults.add(createValidationError("Value length < minLength", stringInstance));
                }
                if (stringType.getMaxLength() != null && value.length() > stringType.getMaxLength()) {
                    validationResults.add(createValidationError("Value length > maxLength", stringInstance));
                }
                if (stringType.getPattern() != null && !stringType.getPattern().test(value)) {
                    validationResults.add(createValidationError("Value doesn't match pattern "
                            + stringType.getPattern(), stringInstance));
                }
            } else {
                validationResults.add(createValidationError("Invalid type", stringInstance));
            }
            return validationResults;
        }

        @Override
        public List<Diagnostic> caseNumberInstance(final NumberInstance numberInstance) {
            final List<Diagnostic> validationResults = new ArrayList<>();
            if (typeInstanceOf(NumberTypeFacet.class)) {
                final NumberTypeFacet numberType = (NumberTypeFacet) types.peek();
                final BigDecimal value = numberInstance.getValue();
                if (numberType.getMinimum() != null && value.compareTo(numberType.getMinimum()) < 0) {
                    validationResults.add(createValidationError("Value < minimum", numberInstance));
                }
                if (numberType.getMaximum() != null && value.compareTo(numberType.getMaximum()) > 0) {
                    validationResults.add(createValidationError("Value > maximum", numberInstance));
                }
                if (numberType.getMultipleOf() != null && value.remainder(BigDecimal.valueOf(numberType.getMultipleOf())).compareTo(BigDecimal.ZERO) != 0) {
                    validationResults.add(createValidationError("Value is not a multiple of " + numberType.getMultipleOf(), numberInstance));
                }
            } else {
                validationResults.add(createValidationError("Invalid type", numberInstance));
            }
            return validationResults;
        }

        private boolean typeInstanceOf(final Class<?> clazz) {
            return !types.empty() && clazz.isInstance(types.peek());
        }

        private Diagnostic createValidationError(final String message, final EObject eObject) {
           return new BasicDiagnostic(Diagnostic.ERROR, null, -1, message, new Object[] { eObject });
        }
    }
}

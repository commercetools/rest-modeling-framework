package io.vrap.rmf.raml.validation;

import io.vrap.rmf.raml.model.facets.*;
import io.vrap.rmf.raml.model.types.AnyAnnotationType;
import io.vrap.rmf.raml.model.types.AnyType;
import io.vrap.rmf.raml.model.types.util.TypesSwitch;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import java.util.*;
import java.util.stream.Collectors;

public class TypesValidator extends AbstractRamlValidator {
    private final TypesValidatingVisitor typesValidatingVisitor = new TypesValidatingVisitor();

    private final List<ValidatingTypesSwitch> validators = Arrays.asList(
            new EnumFacetValidator(),
            new DefaultFacetValidator(),
            new ExamplesValidator());

    @Override
    public boolean validate(final EClass eClass, final EObject eObject, final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        final Diagnostic diagnostic = typesValidatingVisitor.doSwitch(eObject);
        if (diagnostic != null) {
            diagnostics.add(diagnostic);
            return false;
        } else {
            final List<Diagnostic> validationResults = validators.stream()
                    .flatMap(validator -> validator.doSwitch(eObject).stream())
                    .collect(Collectors.toList());
            validationResults.forEach(diagnostics::add);

            return validationResults.isEmpty();
        }
    }

    private class TypesValidatingVisitor extends TypesSwitch<Diagnostic> {

        @Override
        public Diagnostic defaultCase(EObject object) {
            return null;
        }

        @Override
        public Diagnostic caseArrayTypeFacet(final ArrayTypeFacet arrayType) {
            boolean rangeIsValid = arrayType.getMinItems() == null
                    || arrayType.getMaxItems() == null
                    || arrayType.getMinItems() <= arrayType.getMaxItems();

            return rangeIsValid ? null : error("Facet 'minItems' must be <= 'maxItems'", arrayType);
        }

        @Override
        public Diagnostic caseStringTypeFacet(final StringTypeFacet stringType) {
            boolean rangeIsValid = stringType.getMinLength() == null
                    || stringType.getMaxLength() == null
                    || stringType.getMinLength() <= stringType.getMaxLength();

            return rangeIsValid ? null : error("Facet 'minLength' must be <= 'maxLength'", stringType);
        }

        @Override
        public Diagnostic caseNumberTypeFacet(final NumberTypeFacet numberType) {
            boolean rangeIsValid = numberType.getMinimum() == null
                    || numberType.getMaximum() == null
                    || numberType.getMinimum().compareTo(numberType.getMaximum()) <= 0;

            return rangeIsValid ? null : error("Facet 'minimum' must be <= 'maximum'", numberType);
        }

        @Override
        public Diagnostic caseIntegerTypeFacet(final IntegerTypeFacet integerType) {
            boolean rangeIsValid = integerType.getMinimum() == null
                    || integerType.getMaximum() == null
                    || integerType.getMinimum().compareTo(integerType.getMaximum()) <= 0;

            return rangeIsValid ? null : error("Facet 'minimum' must be <= 'maximum'", integerType);
        }

        @Override
        public Diagnostic caseFileTypeFacet(final FileTypeFacet fileType) {
            boolean rangeIsValid = fileType.getMinLength() == null
                    || fileType.getMaxLength() == null
                    || fileType.getMinLength() <= fileType.getMaxLength();

            return rangeIsValid ? null : error("Facet 'minLength' must be <= 'maxLength'", fileType);
        }
    }

    private static abstract class ValidatingTypesSwitch extends TypesSwitch<List<Diagnostic>> {

    }

    private static class ExamplesValidator extends ValidatingTypesSwitch {
        private InstanceValidator instanceValidator = new InstanceValidator();

        @Override
        public List<Diagnostic> defaultCase(EObject object) {
            return Collections.emptyList();
        }

        @Override
        public List<Diagnostic> caseAnyType(final AnyType anyType) {
            final List<Diagnostic> validationResults = anyType.getExamples().stream()
                    .flatMap(example -> instanceValidator.validate(example.getValue(), anyType).stream())
                    .collect(Collectors.toList());
            return validationResults;
        }
    }

    private class EnumFacetValidator extends ValidatingTypesSwitch  {
        private InstanceValidator instanceValidator = new InstanceValidator();

        @Override
        public List<Diagnostic> defaultCase(EObject object) {
            return Collections.emptyList();
        }

        @Override
        public List<Diagnostic> caseAnyType(final AnyType anyType) {
            final List<Diagnostic> validationResults = new ArrayList<>();
            validationResults.addAll(anyType.getEnum().stream()
                    .flatMap(value -> instanceValidator.validate(value, anyType).stream())
                    .collect(Collectors.toList()));

            if (validationResults.isEmpty()) {
                final Set<Object> uniqueItems = new HashSet<>();
                // TODO this only works for primitive values, we should extend it for object instance and array instance
                final Set<Instance> duplicateValues = anyType.getEnum().stream()
                        .filter(value -> !uniqueItems.add(value.getValue()))
                        .collect(Collectors.toSet());
                if (duplicateValues.size() > 0) {
                    validationResults.add(error("Enum facet contains duplicate values", anyType));
                }
            }
            return validationResults;
        }

        @Override
        public List<Diagnostic> caseAnyAnnotationType(final AnyAnnotationType anyAnnotationType) {
            final List<Diagnostic> validationResults = new ArrayList<>();
            validationResults.addAll(anyAnnotationType.getEnum().stream()
                    .flatMap(value -> instanceValidator.validate(value, anyAnnotationType).stream())
                    .collect(Collectors.toList()));

            if (validationResults.isEmpty()) {
                final Set<Object> uniqueItems = new HashSet<>();
                // TODO this only works for primitive values, we should extend it for object instance and array instance
                final Set<Instance> duplicateValues = anyAnnotationType.getEnum().stream()
                        .filter(value -> !uniqueItems.add(value.getValue()))
                        .collect(Collectors.toSet());
                if (duplicateValues.size() > 0) {
                    validationResults.add(error("Enum facet contains duplicate values", anyAnnotationType));
                }
            }
            return validationResults;
        }
    }
    
    private class DefaultFacetValidator extends ValidatingTypesSwitch  {
        private InstanceValidator instanceValidator = new InstanceValidator();

        @Override
        public List<Diagnostic> defaultCase(EObject object) {
            return Collections.emptyList();
        }

        @Override
        public List<Diagnostic> caseAnyType(final AnyType anyType) {
            final List<Diagnostic> validationResults = Optional.ofNullable(anyType.getDefault())
                    .map(value -> instanceValidator.validate(value, anyType))
                    .orElse(Collections.emptyList());

            return validationResults;
        }

        @Override
        public List<Diagnostic> caseAnyAnnotationType(final AnyAnnotationType anyAnnotationType) {
            final List<Diagnostic> validationResults = Optional.ofNullable(anyAnnotationType.getDefault())
                    .map(value -> instanceValidator.validate(value, anyAnnotationType))
                    .orElse(Collections.emptyList());

            return validationResults;
        }
    }
}

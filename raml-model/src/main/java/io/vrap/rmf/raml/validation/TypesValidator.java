package io.vrap.rmf.raml.validation;

import com.google.common.base.Strings;
import io.vrap.rmf.raml.model.types.*;
import io.vrap.rmf.raml.model.types.util.TypesSwitch;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import java.util.*;
import java.util.stream.Collectors;

class TypesValidator extends AbstractRamlValidator {
    private final TypeAndAnnoationTypeValidator typeAndAnnoationTypeValidator = new TypeAndAnnoationTypeValidator();

    private final List<ValidatingTypesSwitch> validators = Arrays.asList(
            new AnnotationValidator(),
            new EnumFacetValidator(),
            new DefaultFacetValidator(),
            new ExamplesValidator());

    @Override
    public boolean validate(final EClass eClass, final EObject eObject, final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        final List<Diagnostic> validationResults = validators.stream()
                .flatMap(validator -> validator.doSwitch(eObject).stream())
                .collect(Collectors.toList());
        validationResults.addAll(typeAndAnnoationTypeValidator.doSwitch(eObject));

        validationResults.forEach(diagnostics::add);

        return validationResults.isEmpty();
    }

    private class TypeAndAnnoationTypeValidator extends TypesSwitch<List<Diagnostic>> {

        @Override
        public List<Diagnostic> defaultCase(final EObject object) {
            return Collections.emptyList();
        }

        @Override
        public List<Diagnostic> caseArrayTypeFacet(final ArrayTypeFacet arrayType) {
            final List<Diagnostic> validationResults = new ArrayList<>();
            final boolean rangeIsValid = arrayType.getMinItems() == null
                    || arrayType.getMaxItems() == null
                    || arrayType.getMinItems() <= arrayType.getMaxItems();
            if (!rangeIsValid) {
                validationResults.add(error("Facet 'minItems' must be <= 'maxItems'", arrayType));
            }
            return validationResults;
        }

        @Override
        public List<Diagnostic> caseStringTypeFacet(final StringTypeFacet stringType) {
            final List<Diagnostic> validationResults = new ArrayList<>();
            final boolean rangeIsValid = stringType.getMinLength() == null
                    || stringType.getMaxLength() == null
                    || stringType.getMinLength() <= stringType.getMaxLength();
            if (!rangeIsValid) {
                validationResults.add(error("Facet 'minLength' must be <= 'maxLength'", stringType));
            }
            return validationResults;
        }

        @Override
        public List<Diagnostic> caseNumberTypeFacet(final NumberTypeFacet numberType) {
            final List<Diagnostic> validationResults = new ArrayList<>();
            final boolean rangeIsValid = numberType.getMinimum() == null
                    || numberType.getMaximum() == null
                    || numberType.getMinimum().compareTo(numberType.getMaximum()) <= 0;
            if (!rangeIsValid) {
                validationResults.add(error("Facet 'minimum' must be <= 'maximum'", numberType));
            }
            return validationResults;
        }

        @Override
        public List<Diagnostic> caseIntegerTypeFacet(final IntegerTypeFacet integerType) {
            final List<Diagnostic> validationResults = new ArrayList<>();
            final boolean rangeIsValid = integerType.getMinimum() == null
                    || integerType.getMaximum() == null
                    || integerType.getMinimum().compareTo(integerType.getMaximum()) <= 0;
            if (!rangeIsValid) {
                validationResults.add(error("Facet 'minimum' must be <= 'maximum'", integerType));
            }
            return validationResults;
        }

        @Override
        public List<Diagnostic> caseFileTypeFacet(final FileTypeFacet fileType) {
            final List<Diagnostic> validationResults = new ArrayList<>();
            final boolean rangeIsValid = fileType.getMinLength() == null
                    || fileType.getMaxLength() == null
                    || fileType.getMinLength() <= fileType.getMaxLength();
            if (!rangeIsValid) {
                validationResults.add(error("Facet 'minLength' must be <= 'maxLength'", fileType));
            }
            return validationResults;
        }

        @Override
        public List<Diagnostic> caseProperty(final Property property) {
            final List<Diagnostic> validationResults = new ArrayList<>();
            if (Strings.isNullOrEmpty(property.getName())) {
                validationResults.add(error("Property must have a name", property));
            } else if (property.getType() == null) {
                validationResults.add(error("Property must have a type", property));
            }
            return validationResults;
        }

        @Override
        public List<Diagnostic> caseObjectType(final ObjectType objectType) {
            final List<Diagnostic> validationResults = new ArrayList<>();
            if (objectType.isInlineType()) {
                if (objectType.getDiscriminator() != null) {
                    validationResults.add(error("Facet 'discriminator' can't be defined for an inline type", objectType));
                }
                if (objectType.getDiscriminatorValue() != null) {
                    validationResults.add(error("Facet 'discriminator' can't be defined for an inline type", objectType));
                }
            }
            return validationResults;
        }
    }

    private static abstract class ValidatingTypesSwitch extends TypesSwitch<List<Diagnostic>> {

        @Override
        public final List<Diagnostic> defaultCase(EObject object) {
            return Collections.emptyList();
        }
    }

    private static class AnnotationValidator extends ValidatingTypesSwitch {
        private InstanceValidator instanceValidator = new InstanceValidator();

        @Override
        public List<Diagnostic> caseAnnotation(final Annotation annotation) {
            return instanceValidator.validate(annotation);
        }
    }

    private static class ExamplesValidator extends ValidatingTypesSwitch {
        private InstanceValidator instanceValidator = new InstanceValidator();

        @Override
        public List<Diagnostic> caseAnyType(final AnyType anyType) {
            final List<Diagnostic> validationResults = anyType.getExamples().stream()
                    .flatMap(example -> instanceValidator.validate(example.getValue(), anyType).stream())
                    .collect(Collectors.toList());
            return validationResults;
        }
    }

    private class EnumFacetValidator extends ValidatingTypesSwitch {
        private InstanceValidator instanceValidator = new InstanceValidator();

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

    private class DefaultFacetValidator extends ValidatingTypesSwitch {
        private InstanceValidator instanceValidator = new InstanceValidator();

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

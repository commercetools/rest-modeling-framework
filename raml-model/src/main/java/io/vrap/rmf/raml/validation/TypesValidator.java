package io.vrap.rmf.raml.validation;

import com.google.common.base.Joiner;
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
            new TypeResolutionValidator(),
            new TypeConsistencyCheck(),
            new AnnotationValidator(),
            new EnumFacetValidator(),
            new DefaultFacetValidator(),
            new ExamplesValidator(),
            new AnyTypeFacetValidator());

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
                validationResults.add(error(arrayType,
                        "Facet 'minItems' ''{0}'' must be <= 'maxItems' ''{1}''",
                        arrayType.getMinItems(), arrayType.getMaxItems()));
            }
            return validationResults;
        }

        @Override
        public List<Diagnostic> caseNumberTypeFacet(final NumberTypeFacet numberType) {
            return validateRange(numberType, numberType.getMinimum(), numberType.getMaximum());
        }

        @Override
        public List<Diagnostic> caseIntegerTypeFacet(final IntegerTypeFacet integerType) {
            return validateRange(integerType, integerType.getMinimum(), integerType.getMaximum());
        }

        private <T> List<Diagnostic> validateRange(final AnyTypeFacet typeFacet, final Comparable<T> minimum, final T maximum) {
            final List<Diagnostic> validationResults = new ArrayList<>();
            final boolean rangeIsValid = minimum == null
                    || maximum == null
                    || minimum.compareTo(maximum) <= 0;
            if (!rangeIsValid) {
                validationResults.add(error(typeFacet,
                        "Facet 'minimum' ''{0}'' must be <= 'maximum' ''{1}''",
                        minimum, maximum));
            }
            return validationResults;
        }

        @Override
        public List<Diagnostic> caseStringTypeFacet(final StringTypeFacet stringType) {
            return validateLengthRange(stringType, stringType.getMinLength(), stringType.getMaxLength());
        }

        @Override
        public List<Diagnostic> caseFileTypeFacet(final FileTypeFacet fileType) {
            return validateLengthRange(fileType, fileType.getMinLength(), fileType.getMaxLength());
        }

        private List<Diagnostic> validateLengthRange(final AnyTypeFacet typeFacet, final Integer minLength, final Integer maxLength) {
            final List<Diagnostic> validationResults = new ArrayList<>();
            final boolean rangeIsValid = minLength == null
                    || maxLength == null
                    || minLength <= maxLength;
            if (!rangeIsValid) {
                validationResults.add(error(typeFacet,
                        "Facet 'minLength' ''{0}'' must be <= 'maxLength' ''{1}''",
                        minLength, maxLength));
            }
            return validationResults;
        }

        @Override
        public List<Diagnostic> caseProperty(final Property property) {
            final List<Diagnostic> validationResults = new ArrayList<>();
            if (Strings.isNullOrEmpty(property.getName())) {
                validationResults.add(error(property, "Property must have a name"));
            } else if (property.getType() == null) {
                validationResults.add(error(property, "Property must have a type"));
            }
            return validationResults;
        }

        @Override
        public List<Diagnostic> caseObjectType(final ObjectType objectType) {
            final List<Diagnostic> validationResults = new ArrayList<>();
            final String discriminator = objectType.getDiscriminator();
            if (discriminator != null) {
                final Property discriminatorProperty = objectType.getProperty(discriminator);
                if (discriminatorProperty == null) {
                    validationResults.add(error(objectType,"Type with discriminator ''{0}'' has to define a property for it", discriminator));
                } else if (!(discriminatorProperty.getType() instanceof StringType)) {
                    validationResults.add(error(objectType, "Discriminator property ''{0}'' must be of type 'string'", discriminator));
                } else {
                    final Set<String> discriminatorValues = new HashSet<>();
                    discriminatorValues.add(objectType.discriminatorValueOrDefault());
                    validateDiscriminatorValueUniqueness(objectType, discriminatorValues, validationResults);
                }
            }
            return validationResults;
        }

        private void validateDiscriminatorValueUniqueness(final ObjectType objectType, final Set<String> discriminatorValues, final List<Diagnostic> validationResults) {
            final List<ObjectType> properSubTypes = objectType.getSubTypes().stream()
                    .filter(ObjectType.class::isInstance)
                    .map(ObjectType.class::cast)
                    .filter(o -> !o.isInlineType())
                    .collect(Collectors.toList());
            for (final ObjectType subType : properSubTypes) {
                final String discriminatorValue = subType.discriminatorValueOrDefault();
                if (discriminatorValues.contains(discriminatorValue)) {
                    validationResults.add(error(subType, "Duplicate discriminator value ''{0}'' found", discriminatorValue));
                } else {
                    discriminatorValues.add(discriminatorValue);
                }
                // TODO make sure that inheritance doesn't contain cycles
                validateDiscriminatorValueUniqueness(subType, discriminatorValues, validationResults);
            }
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
                    .filter(example -> example.getStrict() == null || example.getStrict().getValue() == null || example.getStrict().getValue())
                    .flatMap(example -> instanceValidator.validate(example.getValue(), anyType).stream())
                    .collect(Collectors.toList());
            return validationResults;
        }
    }

    private class EnumFacetValidator extends ValidatingTypesSwitch {
        private InstanceValidator instanceValidator = new InstanceValidator();

        @Override
        public List<Diagnostic> caseAnyType(final AnyType anyType) {
            return validateEnum(anyType);
        }

        @Override
        public List<Diagnostic> caseAnyAnnotationType(final AnyAnnotationType anyAnnotationType) {
            return validateEnum(anyAnnotationType);
        }

        private List<Diagnostic> validateEnum(final AnyTypeFacet typeFacet) {
            final List<Diagnostic> validationResults = new ArrayList<>();
            validationResults.addAll(typeFacet.getEnum().stream()
                    .flatMap(value -> instanceValidator.validate(value, typeFacet).stream())
                    .collect(Collectors.toList()));

            if (validationResults.isEmpty()) {
                final Set<Object> uniqueItems = new HashSet<>();
                // TODO this only works for primitive values, we should extend it for object instance and array instance
                final Set<Instance> duplicateValues = typeFacet.getEnum().stream()
                        .filter(value -> !uniqueItems.add(value.getValue()))
                        .collect(Collectors.toSet());
                if (duplicateValues.size() > 0) {
                    validationResults.add(error(typeFacet, "Enum facet contains duplicate values"));
                }
            }
            return validationResults;
        }
    }

    private class DefaultFacetValidator extends ValidatingTypesSwitch {
        private InstanceValidator instanceValidator = new InstanceValidator();

        @Override
        public List<Diagnostic> caseAnyType(final AnyType anyType) {
            return validateDefault(anyType);
        }

        @Override
        public List<Diagnostic> caseAnyAnnotationType(final AnyAnnotationType anyAnnotationType) {
            return validateDefault(anyAnnotationType);
        }

        private List<Diagnostic> validateDefault(final AnyTypeFacet typeFacet) {
            final List<Diagnostic> validationResults = Optional.ofNullable(typeFacet.getDefault())
                    .map(value -> instanceValidator.validate(value, typeFacet))
                    .orElse(Collections.emptyList());

            return validationResults;
        }
    }

    private class TypeConsistencyCheck extends ValidatingTypesSwitch {

        @Override
        public List<Diagnostic> caseAnyTypeFacet(final AnyTypeFacet anyTypeFacet) {
            final AnyType superType = anyTypeFacet.getType();
            if (superType != null && anyTypeFacet.eClass() != superType.eClass()) {
                if (superType instanceof IntersectionType) {
                    return Collections.emptyList();
                }
                return Collections.singletonList(error(anyTypeFacet,
                        "Inconsistent types: Type ''{0}'' has eClass ''{1}'' while super type ''{2}'' has eClass ''{3}''",
                        anyTypeFacet.getName(), anyTypeFacet.eClass().getName(),
                        superType.getName(), superType.eClass().getName()));
            }
            return Collections.emptyList();
        }
    }

    /**
     * This validator checks that all types are resolved properply.
     */
    private class TypeResolutionValidator extends ValidatingTypesSwitch {

        @Override
        public List<Diagnostic> caseAnyType(final AnyType anyType) {
            return validateTypeResolved(anyType);
        }

        @Override
        public List<Diagnostic> caseArrayType(final ArrayType arrayType) {
            final List<Diagnostic> results = new ArrayList<>();
            results.addAll(validateTypeResolved(arrayType));

            if (arrayType.getItems() != null && arrayType.getItems().eIsProxy()) {
                results.add(error(arrayType, "Items type {0} can''t be resolved",
                        getNameFromProxy(arrayType.getItems())));
            }
            return results;
        }

        @Override
        public List<Diagnostic> caseTypedElement(final TypedElement typedElement) {
            return validateTypeResolved(typedElement);
        }

        @Override
        public List<Diagnostic> caseUnionType(final UnionType unionType) {
            final List<Diagnostic> results = new ArrayList<>();
            results.addAll(validateTypeResolved(unionType));

            for (final AnyType oneOf : unionType.getOneOf()) {
                if (oneOf.eIsProxy()) {
                    results.add(error(unionType, "Type ''{0}'' can''t be resolved",
                            getNameFromProxy(oneOf)));
                }
            }
            return results;
        }

        @Override
        public List<Diagnostic> caseIntersectionType(final IntersectionType intersectionType) {
            final List<Diagnostic> results = new ArrayList<>();
            results.addAll(validateTypeResolved(intersectionType));

            for (final AnyType allOf : intersectionType.getAllOf()) {
                if (allOf.eIsProxy()) {
                    results.add(error(intersectionType, "Type ''{0}'' can''t be resolved",
                            getNameFromProxy(allOf)));
                }
            }
            return results;
        }

        private List<Diagnostic> validateTypeResolved(final AnyTypeFacet anyType) {
            if (anyType.getType() != null && anyType.getType().eIsProxy()) {
                return Collections.singletonList(error(anyType, "Type ''{0}'' can''t be resolved",
                        getNameFromProxy(anyType.getType())));
            } else {
                return Collections.emptyList();
            }
        }

        private List<Diagnostic> validateTypeResolved(final TypedElement typedElement) {
            if (typedElement.getType() != null && typedElement.getType().eIsProxy()) {
                return Collections.singletonList(error(typedElement, "Type ''{0}'' can''t be resolved",
                        getNameFromProxy(typedElement.getType())));
            } else {
                return Collections.emptyList();
            }
        }
    }

    private class AnyTypeFacetValidator extends ValidatingTypesSwitch {
        @Override
        public List<Diagnostic> caseAnyTypeFacet(final AnyTypeFacet anyTypeFacet) {
            final List<Diagnostic> validationResults;
            if (anyTypeFacet.getType() instanceof IntersectionType) {
                final IntersectionType intersectionType = (IntersectionType) anyTypeFacet.getType();
                final Set<String> primitiveTypes = intersectionType.getAllOf().stream()
                        .map(EObject::eClass).map(BuiltinType::of)
                        .filter(Optional::isPresent).map(Optional::get).map(BuiltinType::getName)
                        .collect(Collectors.toSet());
                if (primitiveTypes.size() > 1) {
                    validationResults = new ArrayList<>();
                    final String primitiveTypesAsString = Joiner.on(",").join(primitiveTypes);
                    validationResults.add(error(anyTypeFacet, "Intersection type has different primitive type [{0}]", primitiveTypesAsString));
                } else {
                    validationResults = Collections.emptyList();
                }
            } else {
                validationResults = Collections.emptyList();
            }
            return validationResults;
        }
    }
}

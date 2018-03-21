package io.vrap.rmf.raml.validation;

import com.google.common.base.Strings;
import io.vrap.rmf.raml.model.types.*;
import io.vrap.rmf.raml.model.types.util.TypesSwitch;
import io.vrap.rmf.raml.model.util.InstanceHelper;
import io.vrap.rmf.raml.model.util.ModelHelper;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static io.vrap.rmf.raml.model.types.TypesPackage.Literals.*;

/**
 * A validator for {@link Instance}s.
 */
public class InstanceValidator implements DiagnosticsCreator {

    /**
     * Validates the given instance against the given type.
     *
     * @param instance the instance to validate
     * @param type     the type to validate the instance against
     * @return the validation diagnostics
     */
    public List<Diagnostic> validate(final Instance instance, final AnyType type) {
        return validateInternal(instance, type);
    }

    /**
     * Validates the given instance against the given annotation type.
     *
     * @param instance       the instance to validate
     * @param annotationType the annotation type to validate the instance against
     * @return the validation diagnostics
     */
    public List<Diagnostic> validate(final Instance instance, final AnyAnnotationType annotationType) {
        return validateInternal(instance, annotationType);
    }

    /**
     * Validates the given annotation.
     *
     * @param annotation the annotation to validate
     * @return the validation diagnostics
     */
    public List<Diagnostic> validate(final Annotation annotation) {
        return validateInternal(annotation.getValue(), annotation.getType());
    }

    private List<Diagnostic> validateInternal(final Instance instance, final EObject type) {
        final InstanceValidatingVisitor validatingVisitor = new InstanceValidatingVisitor(type);
        final List<Diagnostic> validationResults = validatingVisitor.doSwitch(instance);
        return validationResults;
    }

    private List<Diagnostic> validateEnumFacet(final AnyTypeFacet anyTypeFacet, final Object value) {
        final List<Diagnostic> validationResults = new ArrayList<>();
        final Optional<Instance> enumInstance = anyTypeFacet.getEnum().stream()
                .filter(enumValue -> enumValue.getValue().equals(value))
                .findFirst();
        if (anyTypeFacet.getEnum().size() > 0 && !enumInstance.isPresent()) {
            validationResults.add(error(anyTypeFacet,"Value {0} is not defined in enum facet", value));
        }
        return validationResults;
    }

    private class InstanceValidatingVisitor extends TypesSwitch<List<Diagnostic>> {
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

            final String value = stringInstance.getValue();
            if (typeInstanceOf(StringTypeFacet.class)) {
                final StringTypeFacet stringType = (StringTypeFacet) types.peek();
                if (stringType.getMinLength() != null && value.length() < stringType.getMinLength()) {
                    validationResults.add(error(stringInstance, "Value length {0} < minLength {1}",
                            value.length(), stringType.getMinLength()));
                }
                if (stringType.getMaxLength() != null && value.length() > stringType.getMaxLength()) {
                    validationResults.add(error(stringInstance, "Value length {0} > maxLength {1}",
                            value.length(), stringType.getMaxLength()));
                }
                if (stringType.getPattern() != null && !stringType.getPattern().test(value)) {
                    validationResults.add(error(stringInstance, "Value {0} doesn't match pattern {1}",
                            value, stringType.getPattern()));
                }

                validationResults.addAll(validateEnumFacet(stringType, value));
            } else if (typeInstanceOf(NilType.class)) {
                if (!Strings.isNullOrEmpty(value)) {
                    validationResults.add(error(stringInstance, "Value must be empty"));
                }
            // try to parse and validate the string instance as array or object if applicable
            } else if (typeIs(ARRAY_TYPE) && value.trim().startsWith("[") && value.trim().endsWith("]")) {
                return doSwitch(InstanceHelper.parseJson(value, stringInstance.eResource().getURI().toFileString()));
            } else if (typeIs(OBJECT_TYPE) && value.trim().startsWith("{") && value.trim().endsWith("}")) {
                return doSwitch(InstanceHelper.parseJson(value, stringInstance.eResource().getURI().toFileString()));
            } else if (!typeIs(ANY_TYPE) && !typeInstanceOf(DateTimeTypeFacet.class) && !typeInstanceOf(TypeTemplate.class)) {
                validationResults.add(error(stringInstance, "Invalid type"));
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
                    validationResults.add(error(numberInstance, "Value {0} < minimum {1}",
                            numberInstance.getValue(), numberType.getMinimum()));
                }
                if (numberType.getMaximum() != null && value.compareTo(numberType.getMaximum()) > 0) {
                    validationResults.add(error(numberInstance, "Value {0} > maximum {0}",
                            numberInstance.getValue(), numberType.getMaximum()));
                }
                if (numberType.getMultipleOf() != null && value.remainder(BigDecimal.valueOf(numberType.getMultipleOf())).compareTo(BigDecimal.ZERO) != 0) {
                    validationResults.add(error(numberInstance, "Value {0} is not a multiple of {1}",
                            value, numberType.getMultipleOf()));
                }

                validationResults.addAll(validateEnumFacet(numberType, value));
            } else if (!typeIs(ANY_TYPE)) {
                validationResults.add(error(numberInstance, "Invalid type"));
            }
            return validationResults;
        }

        @Override
        public List<Diagnostic> caseIntegerInstance(final IntegerInstance integerInstance) {
            final List<Diagnostic> validationResults = new ArrayList<>();
            final Integer value = integerInstance.getValue();

            if (typeInstanceOf(CommonNumberTypeFacet.class)) {
                final CommonNumberTypeFacet commonNumberType = (CommonNumberTypeFacet) types.peek();
                if (commonNumberType.getMultipleOf() != null && value % commonNumberType.getMultipleOf() != 0) {
                    validationResults.add(error(integerInstance, "Value {0} is not a multiple of {1}",
                            value, commonNumberType.getMultipleOf()));
                }
                validationResults.addAll(validateEnumFacet(commonNumberType, value));
            }
            if (typeInstanceOf(IntegerTypeFacet.class)) {
                final IntegerTypeFacet integerType = (IntegerTypeFacet) types.peek();
                if (integerType.getMinimum() != null && value.compareTo(integerType.getMinimum()) < 0) {
                    validationResults.add(error(integerInstance,"Value {0} < minimum {1}",
                            value, integerType.getMinimum()));
                }
                if (integerType.getMaximum() != null && value.compareTo(integerType.getMaximum()) > 0) {
                    validationResults.add(error(integerInstance,"Value {0} > maximum {1}",
                            value, integerType.getMaximum()));
                }
            } else if (typeInstanceOf(NumberTypeFacet.class)) {
                final NumberTypeFacet numberType = (NumberTypeFacet) types.peek();
                if (numberType.getMinimum() != null && value.compareTo(numberType.getMinimum().intValue()) < 0) {
                    validationResults.add(error(integerInstance,"Value {0} < minimum {1}",
                            value, numberType.getMinimum()));
                }
                if (numberType.getMaximum() != null && value.compareTo(numberType.getMaximum().intValue()) > 0) {
                    validationResults.add(error(integerInstance,"Value {0} > maximum {1}",
                            value, numberType.getMaximum()));
                }
            } else if (!typeIs(ANY_TYPE)) {
                validationResults.add(error(integerInstance,"Invalid type"));
            }
            return validationResults;
        }

        @Override
        public List<Diagnostic> caseArrayInstance(final ArrayInstance arrayInstance) {
            final List<Diagnostic> validationResults = new ArrayList<>();
            if (typeInstanceOf(ArrayTypeFacet.class)) {
                final ArrayTypeFacet arrayType = (ArrayTypeFacet) types.peek();
                final EList<Instance> values = arrayInstance.getValue();
                if (arrayType.getMinItems() != null && values.size() < arrayType.getMinItems()) {
                    validationResults.add(error(arrayInstance, "Array size {0} < minItems {1}",
                            values.size(), arrayType.getMinItems()));
                }
                if (arrayType.getMaxItems() != null && values.size() > arrayType.getMaxItems()) {
                    validationResults.add(error(arrayInstance, "Array size  {0} > maxItems {1}",
                            values.size(), arrayType.getMaxItems()));
                }
                if (arrayType.getUniqueItems() != null && arrayType.getUniqueItems()) {
                    final Set<Object> uniqueItems = new HashSet<>();
                    // TODO this only works for primitive values, we should extend it for object instance and array instance
                    final Set<Instance> duplicateValues = values.stream()
                            .filter(value -> !uniqueItems.add(value.getValue()))
                            .collect(Collectors.toSet());
                    if (duplicateValues.size() > 0) {
                        validationResults.add(error(arrayInstance, "Array instance contains duplicate values"));
                    }
                }
                if (arrayType.getItems() != null) {
                    try {
                        types.push(arrayType.getItems());
                        values.stream()
                                .flatMap(instance -> doSwitch(instance).stream())
                                .forEach(validationResults::add);
                    } finally {
                        types.pop();
                    }
                }
            } else if (!typeIs(ANY_TYPE)) {
                validationResults.add(error(arrayInstance, "Invalid type"));
            }
            return validationResults;
        }

        @Override
        public List<Diagnostic> caseObjectInstance(final ObjectInstance objectInstance) {
            final List<Diagnostic> validationResults = new ArrayList<>();

            if (typeInstanceOf(ObjectTypeFacet.class)) {
                final ObjectTypeFacet objectTypeFacet = (ObjectTypeFacet) types.peek();
                final ObjectTypeFacet actualObjectTypeFacet;
                final String discriminator = objectTypeFacet.discriminator();
                if (Strings.isNullOrEmpty(discriminator)) {
                    actualObjectTypeFacet = objectTypeFacet;
                } else {
                    final Instance discriminatorValueInstance = objectInstance.getValue(discriminator);
                    if (discriminatorValueInstance instanceof StringInstance) {
                        final String discriminatorValue = ((StringInstance) discriminatorValueInstance).getValue();
                        final ObjectType subType = objectTypeFacet.getType(discriminatorValue);
                        actualObjectTypeFacet = subType == null ? objectTypeFacet : subType;
                    } else {
                        actualObjectTypeFacet = objectTypeFacet;
                    }
                }

                for (final PropertyValue propertyValue : objectInstance.getValue()) {
                    final String name = propertyValue.getName();
                    final Property property = actualObjectTypeFacet.getProperty(name);

                    if (property != null) {
                        try {
                            types.push(property.getType());
                            final List<Diagnostic> propertyValidationResults = doSwitch(propertyValue.getValue());
                            validationResults.addAll(propertyValidationResults);
                        } finally {
                            types.pop();
                        }
                    } else if (objectTypeFacet.additionalPropertiesInherited() == Boolean.FALSE) {
                        validationResults.add(error(objectInstance,"Property {0} not defined", name));
                    }
                }

                final Map<String, Property> allProperties = ModelHelper.getAllPropertiesAsMap(actualObjectTypeFacet);
                final List<Diagnostic> missingRequiredPropertyErrors = allProperties.values().stream()
                        .filter(property -> property.getRequired() != null && property.getRequired())
                        .filter(property -> objectInstance.getValue(property.getName()) == null)
                        .map(property -> error(objectInstance, "Required property {0} is missing",  property.getName()))
                        .collect(Collectors.toList());
                validationResults.addAll(missingRequiredPropertyErrors);
            }
            return validationResults;
        }

        private boolean typeInstanceOf(final Class<?> clazz) {
            return !types.empty() && clazz.isInstance(types.peek());
        }

        private boolean typeIs(final EClass eClass) {
            return !types.empty() && types.peek().eClass() == eClass;
        }
    }
}

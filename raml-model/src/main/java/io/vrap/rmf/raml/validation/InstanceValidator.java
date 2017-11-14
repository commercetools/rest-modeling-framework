package io.vrap.rmf.raml.validation;

import com.google.common.base.Strings;
import io.vrap.rmf.raml.model.types.*;
import io.vrap.rmf.raml.model.util.ModelHelper;
import io.vrap.rmf.raml.model.values.*;
import io.vrap.rmf.raml.model.values.util.ValuesSwitch;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static io.vrap.rmf.raml.model.types.TypesPackage.Literals.ANY_TYPE;

public class InstanceValidator {

    public List<Diagnostic> validate(final Instance instance, final AnyType anyType) {
        return validateInternal(instance, anyType);
    }

    public List<Diagnostic> validate(final Annotation annotation) {
        return validateInternal(annotation.getValue(), annotation.getType());
    }

    public List<Diagnostic> validate(final Instance instance, final AnyAnnotationType anyAnnotationType) {
        return validateInternal(instance, anyAnnotationType);
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
            validationResults.add(error("Value is not defined in enum facet", anyTypeFacet));
        }
        return validationResults;
    }

    private Diagnostic error(final String message, final EObject eObject) {
        return new BasicDiagnostic(Diagnostic.ERROR, null, -1, message, new Object[]{eObject});
    }

    private class InstanceValidatingVisitor extends ValuesSwitch<List<Diagnostic>> {
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
                final String value = stringInstance.getValue();

                final StringTypeFacet stringType = (StringTypeFacet) types.peek();
                if (stringType.getMinLength() != null && value.length() < stringType.getMinLength()) {
                    validationResults.add(error("Value length < minLength", stringInstance));
                }
                if (stringType.getMaxLength() != null && value.length() > stringType.getMaxLength()) {
                    validationResults.add(error("Value length > maxLength", stringInstance));
                }
                if (stringType.getPattern() != null && !stringType.getPattern().test(value)) {
                    validationResults.add(error("Value doesn't match pattern "
                            + stringType.getPattern(), stringInstance));
                }

                validationResults.addAll(validateEnumFacet(stringType, value));
            } else if (!typeIs(ANY_TYPE) && !typeInstanceOf(DateTimeTypeFacet.class) && !typeInstanceOf(TypeTemplate.class)) {
                validationResults.add(error("Invalid type", stringInstance));
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
                    validationResults.add(error("Value < minimum", numberInstance));
                }
                if (numberType.getMaximum() != null && value.compareTo(numberType.getMaximum()) > 0) {
                    validationResults.add(error("Value > maximum", numberInstance));
                }
                if (numberType.getMultipleOf() != null && value.remainder(BigDecimal.valueOf(numberType.getMultipleOf())).compareTo(BigDecimal.ZERO) != 0) {
                    validationResults.add(error("Value is not a multiple of " + numberType.getMultipleOf(), numberInstance));
                }

                validationResults.addAll(validateEnumFacet(numberType, value));
            } else if (!typeIs(ANY_TYPE)) {
                validationResults.add(error("Invalid type", numberInstance));
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
                    validationResults.add(error("Value is not a multiple of " + commonNumberType.getMultipleOf(), integerInstance));
                }
                validationResults.addAll(validateEnumFacet(commonNumberType, value));
            }
            if (typeInstanceOf(IntegerTypeFacet.class)) {
                final IntegerTypeFacet integerType = (IntegerTypeFacet) types.peek();
                if (integerType.getMinimum() != null && value.compareTo(integerType.getMinimum()) < 0) {
                    validationResults.add(error("Value < minimum", integerInstance));
                }
                if (integerType.getMaximum() != null && value.compareTo(integerType.getMaximum()) > 0) {
                    validationResults.add(error("Value > maximum", integerInstance));
                }
            } else if (typeInstanceOf(NumberTypeFacet.class)) {
                final NumberTypeFacet numberType = (NumberTypeFacet) types.peek();
                if (numberType.getMinimum() != null && value.compareTo(numberType.getMinimum().intValue()) < 0) {
                    validationResults.add(error("Value < minimum", integerInstance));
                }
                if (numberType.getMaximum() != null && value.compareTo(numberType.getMaximum().intValue()) > 0) {
                    validationResults.add(error("Value > maximum", integerInstance));
                }
            } else if (!typeIs(ANY_TYPE)) {
                validationResults.add(error("Invalid type", integerInstance));
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
                    validationResults.add(error("Array size < minItems", arrayInstance));
                }
                if (arrayType.getMaxItems() != null && values.size() > arrayType.getMaxItems()) {
                    validationResults.add(error("Array size > maxItems", arrayInstance));
                }
                if (arrayType.getUniqueItems() != null && arrayType.getUniqueItems()) {
                    final Set<Object> uniqueItems = new HashSet<>();
                    // TODO this only works for primitive values, we should extend it for object instance and array instance
                    final Set<Instance> duplicateValues = values.stream()
                            .filter(value -> !uniqueItems.add(value.getValue()))
                            .collect(Collectors.toSet());
                    if (duplicateValues.size() > 0) {
                        validationResults.add(error("Array instance contains duplicate values", arrayInstance));
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
                validationResults.add(error("Invalid type", arrayInstance));
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
                    final String discriminatorValue = objectTypeFacet.discriminatorValueOrDefault();
                    final ObjectType subType = objectTypeFacet.getSubType(discriminatorValue);
                    actualObjectTypeFacet = subType == null ? objectTypeFacet : subType;
                }

                final Map<String, Property> allProperties = ModelHelper.getAllPropertiesAsMap(actualObjectTypeFacet);

                for (final PropertyValue propertyValue : objectInstance.getValue()) {
                    final String name = propertyValue.getName();
                    final Property property = allProperties.get(name);

                    if (property != null) {
                        try {
                            types.push(property.getType());
                            final List<Diagnostic> propertyValidationResults = doSwitch(propertyValue.getValue());
                            validationResults.addAll(propertyValidationResults);
                        } finally {
                            types.pop();
                        }
                    } else if (objectTypeFacet.getAdditionalProperties() == Boolean.FALSE) {
                        error("Property '" + name + "' not defined", objectInstance);
                    }
                }
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

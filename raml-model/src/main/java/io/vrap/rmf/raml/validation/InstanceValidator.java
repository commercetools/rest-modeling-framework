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
import java.math.BigInteger;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
    public List<Diagnostic> validate(final Instance instance, final AnyTypeFacet type) {
        return validateInternal(instance, type, false);
    }

    public List<Diagnostic> validate(final Instance instance, final AnyTypeFacet type, final Boolean strict) {
        return validateInternal(instance, type, strict);
    }

    /**
     * Validates the given annotation.
     *
     * @param annotation the annotation to validate
     * @return the validation diagnostics
     */
    public List<Diagnostic> validate(final Annotation annotation) {
        return validateInternal(annotation.getValue(), annotation.getType(), false);
    }

    private List<Diagnostic> validateInternal(final Instance instance, final EObject type, final Boolean strict) {
        final InstanceValidatingVisitor validatingVisitor = new InstanceValidatingVisitor(type, strict);
        final List<Diagnostic> validationResults = validatingVisitor.doSwitch(instance);
        return validationResults;
    }

    private List<Diagnostic> validateEnumFacet(final AnyTypeFacet anyTypeFacet, final Instance instance) {
        final Object value = instance.getValue();
        final List<Diagnostic> validationResults = new ArrayList<>();
        if (anyTypeFacet.getEnum().isEmpty() && anyTypeFacet.getType() != null) {
            return validateEnumFacet(anyTypeFacet.getType(), instance);
        } else {
            final Optional<Instance> enumInstance = anyTypeFacet.getEnum().stream()
                    .filter(enumValue -> enumValue.getValue().equals(value))
                    .findFirst();
            if (anyTypeFacet.getEnum().size() > 0 && !enumInstance.isPresent()) {
                final String enumValues = anyTypeFacet.getEnum().stream()
                        .map(e -> e.getValue().toString())
                        .collect(Collectors.joining(",", "[", "]"));
                validationResults.add(error(instance,"Value ''{0}'' is not defined in enum facet ''{1}''",
                        value, enumValues));
            }
            return validationResults;
        }
    }

    private class InstanceValidatingVisitor extends TypesSwitch<List<Diagnostic>> {

        private final Boolean strictMode;
        private final Stack<EObject> types = new Stack<>();

        public InstanceValidatingVisitor(final EObject type, final Boolean strictMode) {
            types.push(type);
            this.strictMode = strictMode;
        }

        @Override
        public List<Diagnostic> defaultCase(EObject object) {
            return Collections.emptyList();
        }

        @Override
        public List<Diagnostic> caseStringInstance(final StringInstance stringInstance) {
            final List<Diagnostic> validationResults = new ArrayList<>();

            if (typeIs(UNION_TYPE)) {
                return validationResults;
            }

            final String value = stringInstance.getValue();
            if (typeInstanceOf(StringTypeFacet.class)) {
                final StringTypeFacet stringType = (StringTypeFacet) types.peek();
                if (stringType.getMinLength() != null && value.length() < stringType.getMinLength()) {
                    validationResults.add(error(stringInstance, "Value length ''{0}'' < minLength ''{1}''",
                            value.length(), stringType.getMinLength()));
                }
                if (stringType.getMaxLength() != null && value.length() > stringType.getMaxLength()) {
                    validationResults.add(error(stringInstance, "Value length ''{0}'' > maxLength ''{1}''",
                            value.length(), stringType.getMaxLength()));
                }
                if (stringType.getPattern() != null && !stringType.getPattern().test(value)) {
                    validationResults.add(error(stringInstance, "Value ''{0}'' doesn't match pattern ''{1}''",
                            value, stringType.getPattern()));
                }

                validationResults.addAll(validateEnumFacet(stringType, stringInstance));
            } else if (typeInstanceOf(NilType.class)) {
                if (!Strings.isNullOrEmpty(value)) {
                    validationResults.add(error(stringInstance, "Value must be empty"));
                }
            // try to parse and validate the string instance as array or object if applicable
            } else if (typeIs(ARRAY_TYPE) && value.trim().startsWith("[") && value.trim().endsWith("]")) {
                return doSwitch(InstanceHelper.parseJson(value, InstanceHelper.resourceFile(stringInstance)));
            } else if (typeIs(OBJECT_TYPE) && value.trim().startsWith("{") && value.trim().endsWith("}")) {
                return doSwitch(InstanceHelper.parseJson(value, InstanceHelper.resourceFile(stringInstance)));
            } else if (typeInstanceOf(DateTime.class)) {
                final Diagnostic diagnostic = new DateValidator(stringInstance).doSwitch(types.peek());
                if (diagnostic != null) {
                    validationResults.add(diagnostic);
                }
            } else if (!typeIs(ANY_TYPE) && !typeInstanceOf(DateTimeTypeFacet.class) && !typeInstanceOf(TypeTemplate.class) && !typeIs(ANY_ANNOTATION_TYPE)) {
                validationResults.add(error(stringInstance, "Invalid type"));
            }
            return validationResults;
        }

        @Override
        public List<Diagnostic> caseNumberInstance(final NumberInstance numberInstance) {
            final List<Diagnostic> validationResults = new ArrayList<>();

            if (typeIs(UNION_TYPE)) {
                return validationResults;
            }

            if (typeInstanceOf(NumberTypeFacet.class)) {
                final NumberTypeFacet numberType = (NumberTypeFacet) types.peek();
                final BigDecimal value = numberInstance.getValue();
                if (numberType.getMinimum() != null && value.compareTo(numberType.getMinimum()) < 0) {
                    validationResults.add(error(numberInstance, "Value ''{0}'' < minimum ''{1}''",
                            numberInstance.getValue(), numberType.getMinimum()));
                }
                if (numberType.getMaximum() != null && value.compareTo(numberType.getMaximum()) > 0) {
                    validationResults.add(error(numberInstance, "Value ''{0}'' > maximum ''{1}''",
                            numberInstance.getValue(), numberType.getMaximum()));
                }
                if (numberType.getMultipleOf() != null && value.remainder(BigDecimal.valueOf(numberType.getMultipleOf())).compareTo(BigDecimal.ZERO) != 0) {
                    validationResults.add(error(numberInstance, "Value ''{0}'' is not a multiple of ''{1}''",
                            value, numberType.getMultipleOf()));
                }

                validationResults.addAll(validateEnumFacet(numberType, numberInstance));
            } else if (!typeIs(ANY_TYPE) && !typeIs(ANY_ANNOTATION_TYPE)) {
                validationResults.add(error(numberInstance, "Invalid type"));
            }
            return validationResults;
        }

        @Override
        public List<Diagnostic> caseIntegerInstance(final IntegerInstance integerInstance) {
            final List<Diagnostic> validationResults = new ArrayList<>();
            final BigInteger value = integerInstance.getValue();

            if (typeIs(UNION_TYPE)) {
                return validationResults;
            }

            if (typeInstanceOf(CommonNumberTypeFacet.class)) {
                final CommonNumberTypeFacet commonNumberType = (CommonNumberTypeFacet) types.peek();
                if (commonNumberType.getMultipleOf() != null && !value.mod(BigInteger.valueOf(commonNumberType.getMultipleOf())).equals(BigInteger.ZERO)) {
                    validationResults.add(error(integerInstance, "Value ''{0}'' is not a multiple of ''{1}''",
                            value, commonNumberType.getMultipleOf()));
                }
                validationResults.addAll(validateEnumFacet(commonNumberType, integerInstance));
            }
            if (typeInstanceOf(IntegerTypeFacet.class)) {
                final IntegerTypeFacet integerType = (IntegerTypeFacet) types.peek();
                if (integerType.getMinimum() != null && value.compareTo(BigInteger.valueOf(integerType.getMinimum())) < 0) {
                    validationResults.add(error(integerInstance,"Value ''{0}'' < minimum ''{1}''",
                            value, integerType.getMinimum()));
                }
                if (integerType.getMaximum() != null && value.compareTo(BigInteger.valueOf(integerType.getMaximum())) > 0) {
                    validationResults.add(error(integerInstance,"Value ''{0}'' > maximum ''{1}''",
                            value, integerType.getMaximum()));
                }
            } else if (typeInstanceOf(NumberTypeFacet.class)) {
                final NumberTypeFacet numberType = (NumberTypeFacet) types.peek();
                if (numberType.getMinimum() != null && value.compareTo(BigInteger.valueOf(numberType.getMinimum().longValue())) < 0) {
                    validationResults.add(error(integerInstance,"Value ''{0}'' < minimum ''{1}''",
                            value, numberType.getMinimum()));
                }
                if (numberType.getMaximum() != null && value.compareTo(BigInteger.valueOf(numberType.getMaximum().longValue())) > 0) {
                    validationResults.add(error(integerInstance,"Value ''{0}'' > maximum ''{1}''",
                            value, numberType.getMaximum()));
                }
            } else if (!typeIs(ANY_TYPE) && !typeIs(ANY_ANNOTATION_TYPE)) {
                validationResults.add(error(integerInstance,"Invalid type"));
            }
            return validationResults;
        }

        @Override
        public List<Diagnostic> caseArrayInstance(final ArrayInstance arrayInstance) {
            final List<Diagnostic> validationResults = new ArrayList<>();

            if (typeIs(UNION_TYPE)) {
                return validationResults;
            }
            if (typeInstanceOf(ArrayTypeFacet.class)) {
                final ArrayTypeFacet arrayType = (ArrayTypeFacet) types.peek();
                final EList<Instance> values = arrayInstance.getValue();
                if (arrayType.getMinItems() != null && values.size() < arrayType.getMinItems()) {
                    validationResults.add(error(arrayInstance, "Array size ''{0}'' < minItems ''{1}''",
                            values.size(), arrayType.getMinItems()));
                }
                if (arrayType.getMaxItems() != null && values.size() > arrayType.getMaxItems()) {
                    validationResults.add(error(arrayInstance, "Array size  ''{0}'' > maxItems ''{1}''",
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
            } else if (!typeIs(ANY_TYPE) && !typeIs(ANY_ANNOTATION_TYPE)) {
                validationResults.add(error(arrayInstance, "Invalid type"));
            }
            return validationResults;
        }

        @Override
        public List<Diagnostic> caseObjectInstance(final ObjectInstance objectInstance) {
            final List<Diagnostic> validationResults = new ArrayList<>();

            if (typeIs(UNION_TYPE)) {
                return validationResults;
            }

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
                        if (subType == null) {
                            validationResults.add(error(objectInstance, "Invalid discriminator value ''{0}'' for type ''{1}''", discriminatorValue, objectTypeFacet.getName()));
                        }
                        actualObjectTypeFacet = subType == null ? objectTypeFacet : subType;
                    } else {
                        actualObjectTypeFacet = objectTypeFacet;
                    }
                }

                if (BuiltinType.OBJECT.getName().equals(actualObjectTypeFacet.getName()) && actualObjectTypeFacet.getProperties().size() == 0) {
                    return validationResults;
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
                    } else if (objectTypeFacet.additionalPropertiesInherited() == Boolean.FALSE || strictMode == Boolean.TRUE) {
                        validationResults.add(error(objectInstance,"Property ''{0}'' not defined", name));
                    }
                }

                final Map<String, Property> allProperties = ModelHelper.getAllPropertiesAsMap(actualObjectTypeFacet);
                final List<Diagnostic> missingRequiredPropertyErrors = allProperties.values().stream()
                        .filter(property -> property.getRequired() != null && property.getRequired())
                        .filter(property -> objectInstance.getValue(property.getName()) == null)
                        .map(property -> error(objectInstance, "Required property ''{0}'' is missing",  property.getName()))
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

    private static final DateTimeFormatter rfc3339 = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    private static final DateTimeFormatter rfc2616 = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss zzz").withLocale(Locale.ENGLISH);

    private class DateValidator extends TypesSwitch<Diagnostic> {
        private final StringInstance value;

        private DateValidator(final StringInstance value) {
            this.value = value;
        }

        @Override
        public Diagnostic caseDateOnlyType(final DateOnlyType dateOnlyType) {
            return validate(DateTimeFormatter.ISO_LOCAL_DATE, dateOnlyType);
        }

        @Override
        public Diagnostic caseDateTimeOnlyType(final DateTimeOnlyType dateTimeOnlyType) {
            return validate(DateTimeFormatter.ISO_LOCAL_DATE_TIME, dateTimeOnlyType);
        }

        @Override
        public Diagnostic caseTimeOnlyType(final TimeOnlyType timeOnlyType) {
            return validate(DateTimeFormatter.ISO_LOCAL_TIME, timeOnlyType);
        }

        @Override
        public Diagnostic caseDateTimeType(final DateTimeType dateTimeType) {
            final DateTimeFormatter dateTimeFormatter = dateTimeType.getFormat().equals(DateTimeFormat.RFC2616)
                    ? rfc2616 : rfc3339;
            return validate(dateTimeFormatter, dateTimeType);
        }

        private Diagnostic validate(final DateTimeFormatter dateTimeFormatter, final DateTime type) {
            try {
                dateTimeFormatter.parse(value.getValue());
                return null;
            } catch (final DateTimeParseException e) {
                return error(value,e.getMessage());
            }
        }
    }
}

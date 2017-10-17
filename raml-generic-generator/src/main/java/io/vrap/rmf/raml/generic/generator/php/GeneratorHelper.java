package io.vrap.rmf.raml.generic.generator.php;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import io.vrap.rmf.raml.model.resources.*;
import io.vrap.rmf.raml.model.types.*;
import io.vrap.rmf.raml.model.types.util.TypesSwitch;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

public class GeneratorHelper {
    static Property getBaseProperty(final Property property) {
        final AnyType anyType = (AnyType)property.eContainer();
        if (!(anyType instanceof ObjectType)) {
            return property;
        }
        final List<ObjectType> t = getParentTypes(anyType).stream().map(ObjectType.class::cast).collect(Collectors.toList());
        if (t.size() <= 1) {
            return property;
        }
        return t.stream()
                .filter(anyType1 -> anyType1.getProperty(property.getName()) != null)
                .map(objectType -> objectType.getProperty(property.getName()))
                .findFirst()
                .orElse(property);
    }

    static String toParamName(final UriTemplate uri, final String delimiter) {
        return toParamName(uri, delimiter, "");
    }

    static String toParamName(final UriTemplate uri, final String delimiter, final String suffix) {
        return StringUtils.capitalize(uri.getParts().stream().map(
                uriTemplatePart -> {
                    if (uriTemplatePart instanceof UriTemplateExpression) {
                        return ((UriTemplateExpression)uriTemplatePart).getVariables().stream()
                                .map(s -> delimiter + StringUtils.capitalize(s) + suffix).collect(Collectors.joining());
                    }
                    return CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, uriTemplatePart.toString().replace("/", "-"));
                }
        ).collect(Collectors.joining())).replaceAll("[^\\p{L}\\p{Nd}]+", "");
    }

    static String toRequestName(UriTemplate uri, Method method) {
        return toParamName(uri, "By") + StringUtils.capitalize(method.getMethod().toString());
    }

    @Nullable
    static <T> T getParent(EObject object, Class<T> parentClass)
    {
        if (object.eContainer() == null) {
            return null;
        }
        if (parentClass.isInstance(object.eContainer())) {
            @SuppressWarnings("unchecked")
            T parent = (T)object.eContainer();
            return parent;
        }
        return getParent(object.eContainer(), parentClass);
    }

    static UriTemplate absoluteUri(final Resource resource)
    {
        final UriTemplate uri = ResourcesFactory.eINSTANCE.createUriTemplate();
        uri.getParts().addAll(absoluteUriParts(resource));
        return uri;
    }

    static List<ResourceGenModel> flattenResources(final List<Resource> resources)
    {
        final List<Resource> r = flatten(resources);
        final List<ResourceGenModel> m = Lists.newArrayList();

        return r.stream().map(resource -> new ResourceGenModel(resource, r)).collect(Collectors.toList());
    }

    private static List<Resource> flatten(final List<Resource> resources)
    {
        final List<Resource> r = Lists.newArrayList();
        for (final Resource resource : resources) {
            r.add(resource);
            if (resource.getResources() != null) {
                r.addAll(flatten(resource.getResources()));
            }
        }
        return r;
    }

    private static List<UriTemplatePart> absoluteUriParts(final Resource resource)
    {
        if (!(resource.eContainer() instanceof Resource)) {
            return (List<UriTemplatePart>) EcoreUtil.copyAll(resource.getRelativeUri().getParts());
        }
        final List<UriTemplatePart> parts = absoluteUriParts((Resource)resource.eContainer());
        parts.addAll(EcoreUtil.copyAll(resource.getRelativeUri().getParts()));
        return parts;
    }

    private static List<AnyType> getParentTypes(final AnyType anyType) {
        if (anyType == null) {
            return Lists.newArrayList();
        }
        if (BuiltinType.of(anyType.getName()).isPresent()) {
            return Lists.newArrayList();
        }
        List<AnyType> t = getParentTypes(anyType.getType());
        t.add(anyType);

        return t;
    }

    static abstract class TypeVisitor extends TypesSwitch<String> {
        @Override
        public String caseStringType(StringType object) {
            return scalarMapper("string");
        }

        @Override
        public String caseNumberType(NumberType object) {
            switch (object.getFormat()) {
                case INT:
                case INT8:
                case INT16:
                case INT32:
                case INT64:
                    return scalarMapper("int");
                default:
                    return scalarMapper("float");
            }
        }

        public String caseIntegerType(IntegerType object) {
            return scalarMapper("int");
        }

        abstract String scalarMapper(final String scalarType);
    }

    static class TypeNameVisitor extends TypeVisitor {

        @Override
        public String caseTimeOnlyType(TimeOnlyType object) {
            return dateTimeMapper();
        }

        @Override
        public String caseDateOnlyType(DateOnlyType object) {
            return dateTimeMapper();
        }

        @Override
        public String caseDateTimeType(DateTimeType object) {
            return dateTimeMapper();
        }

        String dateTimeMapper()
        {
            return "\\DateTimeImmutable";
        }

        String scalarMapper(final String scalarType)
        {
            return scalarType;
        }

        @Override
        public String caseArrayType(final ArrayType arrayType) {
            if (arrayType.getItems() == null || arrayType.getItems().getName() == null || BuiltinType.of(arrayType.getItems().getName()).isPresent()) {
                return "array";
            } else {
                return  arrayType.getItems().getName() + "Collection";
            }
        }

        @Override
        public String caseObjectType(final ObjectType objectType) {
            if (objectType.getName() == null) {
                return null;
            } else {
                return objectType.getName();
            }
        }

        @Override
        public String defaultCase(EObject object) {
            return "mixed";
        }
    }

    static class ParamVisitor extends TypeVisitor {
        final Property property;

        ParamVisitor(final Property property) {
            this.property = property;
        }

        String scalarMapper(final String scalarType)
        {
            return null;
        }

        @Override
        public String caseArrayType(final ArrayType arrayType) {
            if (arrayType.getItems() == null || BuiltinType.of(arrayType.getItems().getName()).isPresent()) {
                return null;
            } else {
                if (BuiltinType.of(arrayType.getItems().getName()).isPresent()) {
                    return "array";
                } else {
                    final Property t = getBaseProperty(property);
                    return new TypeNameVisitor().doSwitch(t.getType());
                }
            }
        }

        @Override
        public String caseObjectType(final ObjectType objectType) {
            if (BuiltinType.of(objectType.getName()).isPresent()) {
                return null;
            } else {
                final Property t = getBaseProperty(property);
                return new TypeNameVisitor().doSwitch(t.getType());
            }
        }

        @Override
        public String defaultCase(EObject object) {
            return null;
        }
    }

    static class SerializerVisitor extends TypesSwitch<SerializerGenModel> {
        final PropertyGenModel property;

        SerializerVisitor(final PropertyGenModel property) {
            this.property = property;
        }

        @Override
        public SerializerGenModel caseTimeOnlyType(TimeOnlyType object) {
            return new SerializerGenModel(property, "timeSerializer", "H:i:s.u");
        }

        @Override
        public SerializerGenModel caseDateOnlyType(DateOnlyType object) {
            return new SerializerGenModel(property,"dateSerializer", "Y-m-d");
        }

        @Override
        public SerializerGenModel caseDateTimeType(DateTimeType object) {
            return new SerializerGenModel(property,"dateTimeSerializer", "c");
        }
    }

    static class PropertyGetterVisitor extends TypesSwitch<GetterGenModel> {
        final PropertyGenModel property;

        PropertyGetterVisitor(final PropertyGenModel property) {
            this.property = property;
        }

        @Override
        public GetterGenModel caseTimeOnlyType(TimeOnlyType object) {
            return new GetterGenModel("dateTimeGetter", property, "H:i:s.u");
        }

        @Override
        public GetterGenModel caseDateOnlyType(DateOnlyType object) {
            return new GetterGenModel("dateTimeGetter", property, "Y-m-d");
        }

        @Override
        public GetterGenModel caseDateTimeType(DateTimeType object) {
            return new GetterGenModel("dateTimeGetter", property, "Y-m-d?H:i:s.uT");
        }

        @Override
        public GetterGenModel caseStringType(StringType object) {
            return new GetterGenModel("scalarGetter", property, "string");
        }

        @Override
        public GetterGenModel caseNumberType(NumberType object) {
            switch (object.getFormat()) {
                case INT:
                case INT8:
                case INT16:
                case INT32:
                case INT64:
                    return new GetterGenModel("scalarGetter", property, "int");
                default:
                    return new GetterGenModel("scalarGetter", property, "float");
            }
        }

        public GetterGenModel caseIntegerType(IntegerType object) {
            return new GetterGenModel("scalarGetter", property, "int");
        }

        @Override
        public GetterGenModel caseArrayType(final ArrayType arrayType) {
            if (arrayType.getItems() == null || BuiltinType.of(arrayType.getItems().getName()).isPresent()) {
                return null;
            } else if (arrayType.getItems() instanceof UnionType) {
                return new GetterGenModel("scalarGetter", property, "array");
            } else {
                if (BuiltinType.of(arrayType.getItems().getName()).isPresent()) {
                    return new GetterGenModel("scalarGetter", property, "array");
                }
                return new GetterGenModel("arrayGetter", property, "array");
            }
        }

        @Override
        public GetterGenModel caseObjectType(final ObjectType objectType) {
            if (BuiltinType.of(objectType.getName()).isPresent()) {
                return null;
            } else {
                return new GetterGenModel("classGetter", property);
            }
        }

        @Override
        public GetterGenModel defaultCase(EObject object) {
            return new GetterGenModel("defaultGetter", property);
        }
    }

    static class PropertySetterVisitor extends TypesSwitch<SetterGenModel> {
        final PropertyGenModel property;

        PropertySetterVisitor(final PropertyGenModel property) {
            this.property = property;
        }
        @Override
        public SetterGenModel caseTimeOnlyType(TimeOnlyType object) {
            return dateTimeMapper();
        }

        @Override
        public SetterGenModel caseDateOnlyType(DateOnlyType object) {
            return dateTimeMapper();
        }

        @Override
        public SetterGenModel caseDateTimeType(DateTimeType object) {
            return dateTimeMapper();
        }

        private SetterGenModel dateTimeMapper()
        {
            return new SetterGenModel("dateTimeSetter", property, null);
        }

        @Override
        public SetterGenModel caseStringType(StringType object) {
            return scalarMapper("string");
        }

        @Override
        public SetterGenModel caseNumberType(NumberType object) {
            switch (object.getFormat()) {
                case INT:
                case INT8:
                case INT16:
                case INT32:
                case INT64:
                    return scalarMapper("int");
                default:
                    return scalarMapper("float");
            }
        }

        public SetterGenModel caseIntegerType(IntegerType object) {
            return scalarMapper("int");
        }

        private SetterGenModel scalarMapper(final String scalarType)
        {
            return new SetterGenModel("scalarSetter", property, scalarType);
        }

        @Override
        public SetterGenModel caseArrayType(final ArrayType arrayType) {
            if (arrayType.getItems() == null || BuiltinType.of(arrayType.getItems().getName()).isPresent()) {
                return null;
            } else if (arrayType.getItems() instanceof UnionType) {
                return new SetterGenModel("arraySetter", property, null);
            } else {
                if (BuiltinType.of(arrayType.getItems().getName()).isPresent()) {
                    return new SetterGenModel("arraySetter", property, "array", "array");
                } else {
                    final Property t = getBaseProperty(property.getProperty());
                    final String paramType = new ParamVisitor(property.getProperty()).doSwitch(t.getType());
                    final String docType = new ParamVisitor(property.getProperty()).doSwitch(arrayType);
                    return new SetterGenModel("arraySetter", property, docType, paramType);
                }
            }
        }

        @Override
        public SetterGenModel caseObjectType(final ObjectType objectType) {
            if (BuiltinType.of(objectType.getName()).isPresent()) {
                return null;
            } else {
                final Property t = getBaseProperty(property.getProperty());
                final String paramType = new ParamVisitor(property.getProperty()).doSwitch(t.getType());
                final String docType = new ParamVisitor(property.getProperty()).doSwitch(objectType);
                return new SetterGenModel("classSetter", property, docType, paramType);
            }
        }

        @Override
        public SetterGenModel defaultCase(EObject object) {
            return new SetterGenModel("defaultSetter", property, null);
        }
    }
}

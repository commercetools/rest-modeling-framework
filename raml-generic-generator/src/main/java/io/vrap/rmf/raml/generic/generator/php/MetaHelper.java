package io.vrap.rmf.raml.generic.generator.php;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import io.vrap.rmf.raml.model.resources.*;
import io.vrap.rmf.raml.model.types.*;
import io.vrap.rmf.raml.model.types.util.TypesSwitch;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.List;
import java.util.stream.Collectors;

public class MetaHelper {
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
        return StringUtils.capitalize(uri.getParts().stream().map(
                uriTemplatePart -> {
                    if (uriTemplatePart instanceof UriTemplateExpression) {
                        return ((UriTemplateExpression)uriTemplatePart).getVariables().stream()
                                .map(s -> delimiter + StringUtils.capitalize(s)).collect(Collectors.joining());
                    }
                    return CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, uriTemplatePart.toString().replace("/", "-"));
                }
        ).collect(Collectors.joining())).replaceAll("[^\\p{L}\\p{Nd}]+", "");
    }

    static String toRequestName(UriTemplate uri, Method method) {
        return toParamName(uri, "By") + StringUtils.capitalize(method.getMethod().toString());
    }

    static UriTemplate absoluteUri(final Resource resource)
    {
        final UriTemplate uri = ResourcesFactory.eINSTANCE.createUriTemplate();
        uri.getParts().addAll(absoluteUriParts(resource));
        return uri;
    }

    static List<MetaResource> flattenResources(final List<Resource> resources)
    {
        final List<Resource> r = flatten(resources);
        final List<MetaResource> m = Lists.newArrayList();

        return r.stream().map(resource -> new MetaResource(resource, r)).collect(Collectors.toList());
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

    static class SerializerVisitor extends TypesSwitch<MetaSerializer> {
        final MetaProperty property;

        SerializerVisitor(final MetaProperty property) {
            this.property = property;
        }

        @Override
        public MetaSerializer caseTimeOnlyType(TimeOnlyType object) {
            return new MetaSerializer(property, "timeSerializer", "H:i:s.u");
        }

        @Override
        public MetaSerializer caseDateOnlyType(DateOnlyType object) {
            return new MetaSerializer(property,"dateSerializer", "Y-m-d");
        }

        @Override
        public MetaSerializer caseDateTimeType(DateTimeType object) {
            return new MetaSerializer(property,"dateTimeSerializer", "c");
        }
    }

    static class PropertyGetterVisitor extends TypesSwitch<MetaGetter> {
        final MetaProperty property;

        PropertyGetterVisitor(final MetaProperty property) {
            this.property = property;
        }

        @Override
        public MetaGetter caseTimeOnlyType(TimeOnlyType object) {
            return new MetaGetter("dateTimeGetter", property, "H:i:s.u");
        }

        @Override
        public MetaGetter caseDateOnlyType(DateOnlyType object) {
            return new MetaGetter("dateTimeGetter", property, "Y-m-d");
        }

        @Override
        public MetaGetter caseDateTimeType(DateTimeType object) {
            return new MetaGetter("dateTimeGetter", property, "Y-m-d?H:i:s.uT");
        }

        @Override
        public MetaGetter caseStringType(StringType object) {
            return new MetaGetter("scalarGetter", property, "string");
        }

        @Override
        public MetaGetter caseNumberType(NumberType object) {
            switch (object.getFormat()) {
                case INT:
                case INT8:
                case INT16:
                case INT32:
                case INT64:
                    return new MetaGetter("scalarGetter", property, "int");
                default:
                    return new MetaGetter("scalarGetter", property, "float");
            }
        }

        public MetaGetter caseIntegerType(IntegerType object) {
            return new MetaGetter("scalarGetter", property, "int");
        }

        @Override
        public MetaGetter caseArrayType(final ArrayType arrayType) {
            if (arrayType.getItems() == null || BuiltinType.of(arrayType.getItems().getName()).isPresent()) {
                return null;
            } else if (arrayType.getItems() instanceof UnionType) {
                return new MetaGetter("scalarGetter", property, "array");
            } else {
                if (BuiltinType.of(arrayType.getItems().getName()).isPresent()) {
                    return new MetaGetter("scalarGetter", property, "array");
                }
                return new MetaGetter("arrayGetter", property, "array");
            }
        }

        @Override
        public MetaGetter caseObjectType(final ObjectType objectType) {
            if (BuiltinType.of(objectType.getName()).isPresent()) {
                return null;
            } else {
                return new MetaGetter("classGetter", property);
            }
        }

        @Override
        public MetaGetter defaultCase(EObject object) {
            return new MetaGetter("defaultGetter", property);
        }
    }

    static class PropertySetterVisitor extends TypesSwitch<MetaSetter> {
        final MetaProperty property;

        PropertySetterVisitor(final MetaProperty property) {
            this.property = property;
        }
        @Override
        public MetaSetter caseTimeOnlyType(TimeOnlyType object) {
            return dateTimeMapper();
        }

        @Override
        public MetaSetter caseDateOnlyType(DateOnlyType object) {
            return dateTimeMapper();
        }

        @Override
        public MetaSetter caseDateTimeType(DateTimeType object) {
            return dateTimeMapper();
        }

        private MetaSetter dateTimeMapper()
        {
            return new MetaSetter("dateTimeSetter", property, null);
        }

        @Override
        public MetaSetter caseStringType(StringType object) {
            return scalarMapper("string");
        }

        @Override
        public MetaSetter caseNumberType(NumberType object) {
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

        public MetaSetter caseIntegerType(IntegerType object) {
            return scalarMapper("int");
        }

        private MetaSetter scalarMapper(final String scalarType)
        {
            return new MetaSetter("scalarSetter", property, scalarType);
        }

        @Override
        public MetaSetter caseArrayType(final ArrayType arrayType) {
            if (arrayType.getItems() == null || BuiltinType.of(arrayType.getItems().getName()).isPresent()) {
                return null;
            } else if (arrayType.getItems() instanceof UnionType) {
                return new MetaSetter("arraySetter", property, null);
            } else {
                if (BuiltinType.of(arrayType.getItems().getName()).isPresent()) {
                    return new MetaSetter("arraySetter", property, "array", "array");
                } else {
                    final Property t = getBaseProperty(property.getProperty());
                    final String paramType = new ParamVisitor(property.getProperty()).doSwitch(t.getType());
                    final String docType = new ParamVisitor(property.getProperty()).doSwitch(arrayType);
                    return new MetaSetter("arraySetter", property, docType, paramType);
                }
            }
        }

        @Override
        public MetaSetter caseObjectType(final ObjectType objectType) {
            if (BuiltinType.of(objectType.getName()).isPresent()) {
                return null;
            } else {
                final Property t = getBaseProperty(property.getProperty());
                final String paramType = new ParamVisitor(property.getProperty()).doSwitch(t.getType());
                final String docType = new ParamVisitor(property.getProperty()).doSwitch(objectType);
                return new MetaSetter("classSetter", property, docType, paramType);
            }
        }

        @Override
        public MetaSetter defaultCase(EObject object) {
            return new MetaSetter("defaultSetter", property, null);
        }
    }
}

package io.vrap.rmf.raml.generic.generator.php;

import com.google.common.collect.Lists;
import io.vrap.rmf.raml.model.types.*;
import io.vrap.rmf.raml.model.types.util.TypesSwitch;
import org.eclipse.emf.ecore.EObject;

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
}

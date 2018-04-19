package io.vrap.rmf.raml.generic.generator.php;

import io.vrap.rmf.raml.generic.generator.*;
import io.vrap.rmf.raml.model.types.*;
import org.eclipse.emf.ecore.EObject;

public class PhpGeneratorHelper extends GeneratorHelper {
    @Override
    public TypeNameVisitor typeNameVisitor()
    {
        return new TypeNameVisitor();
    }

    @Override
    public ParamVisitor paramVisitor(final Property property)
    {
        return new ParamVisitor(property);
    }

    @Override
    public SerializerVisitor serializerVisitor(final PropertyGenModel propertyGenModel)
    {
        return new SerializerVisitor(propertyGenModel);
    }

    @Override
    public PropertyGetterVisitor propertyGetterVisitor(final PropertyGenModel propertyGenModel)
    {
        return new PropertyGetterVisitor(propertyGenModel);
    }

    @Override
    protected PropertyMapperVisitor propertyMapperVisitor() {
        return new PropertyMapperVisitor();
    }

    @Override
    public PropertySetterVisitor propertySetterVisitor(final PropertyGenModel propertyGenModel)
    {
        return new PropertySetterVisitor(propertyGenModel);
    }

    static class TypeNameVisitor extends GeneratorHelper.TypeNameVisitor {
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
            if (
                    arrayType.getItems() == null ||
                    arrayType.getItems().getName() == null ||
                    BuiltinType.of(arrayType.getItems().getName()).isPresent() ||
                    arrayType.getItems() instanceof StringType
            ) {
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

    static class ParamVisitor extends GeneratorHelper.ParamVisitor {
        final Property property;

        ParamVisitor(final Property property) {
            super(property);
            this.property = property;
        }

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

        String scalarMapper(final String scalarType)
        {
            return scalarType;
        }

        @Override
        public String caseArrayType(final ArrayType arrayType) {
            if (arrayType.getItems() == null || BuiltinType.of(arrayType.getItems().getName()).isPresent()) {
                return null;
            } else {
                if (BuiltinType.of(arrayType.getItems().getName()).isPresent()) {
                    return "array";
                } else {
//                    final Property t = getBaseProperty(property);
                    return new TypeNameVisitor().doSwitch(arrayType);
                }
            }
        }

        @Override
        public String caseObjectType(final ObjectType objectType) {
            if (BuiltinType.of(objectType.getName()).isPresent()) {
                return null;
            } else {
//                final Property t = getBaseProperty(property);
                return new TypeNameVisitor().doSwitch(objectType);
            }
        }

        @Override
        public String defaultCase(EObject object) {
            return null;
        }
    }

    static class SerializerVisitor extends GeneratorHelper.SerializerVisitor {
        final PropertyGenModel property;

        SerializerVisitor(final PropertyGenModel property) {
            super(property);
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

    static class PropertyGetterVisitor extends GeneratorHelper.PropertyGetterVisitor {
        final PropertyGenModel property;

        PropertyGetterVisitor(final PropertyGenModel property) {
            super(property);
            this.property = property;
        }

        @Override
        public GetterGenModel caseTimeOnlyType(TimeOnlyType object) {
            return new GetterGenModel("dateTimeGetter", object, property, "H:i:s.u");
        }

        @Override
        public GetterGenModel caseDateOnlyType(DateOnlyType object) {
            return new GetterGenModel("dateTimeGetter", object, property, "Y-m-d");
        }

        @Override
        public GetterGenModel caseDateTimeType(DateTimeType object) {
            return new GetterGenModel("dateTimeGetter", object, property, "Y-m-d?H:i:s.uT");
        }

        @Override
        public GetterGenModel caseStringType(StringType object) {
            return new GetterGenModel("scalarGetter", object, property, "string");
        }

        @Override
        public GetterGenModel caseNumberType(NumberType object) {
            switch (object.getFormat()) {
                case INT:
                case INT8:
                case INT16:
                case INT32:
                case INT64:
                    return new GetterGenModel("scalarGetter", object, property, "int");
                default:
                    return new GetterGenModel("scalarGetter", object, property, "float");
            }
        }

        public GetterGenModel caseIntegerType(IntegerType object) {
            return new GetterGenModel("scalarGetter", object, property, "int");
        }

        @Override
        public GetterGenModel caseArrayType(final ArrayType arrayType) {
            if (arrayType.getItems() == null) {
                return null;
            } else if (arrayType.getItems() instanceof UnionType || arrayType.getItems() instanceof StringType || BuiltinType.of(arrayType.getItems().getName()).isPresent()) {
                return new GetterGenModel("scalarGetter", arrayType, property, "array");
            } else {
                return new GetterGenModel("arrayGetter", arrayType, property, "array");
            }
        }

        @Override
        public GetterGenModel caseObjectType(final ObjectType objectType) {
            if (BuiltinType.of(objectType.getName()).isPresent()) {
                return null;
            } else {
                return new GetterGenModel("classGetter", objectType, property);
            }
        }

        @Override
        public GetterGenModel defaultCase(EObject object) {
            return new GetterGenModel("defaultGetter", (AnyType)object, property);
        }
    }

    static class PropertyMapperVisitor extends GeneratorHelper.PropertyMapperVisitor {
        @Override
        public GetterGenModel caseTimeOnlyType(TimeOnlyType object) {
            return new GetterGenModel("dateTimeMapper", object, null, "H:i:s.u");
        }

        @Override
        public GetterGenModel caseDateOnlyType(DateOnlyType object) {
            return new GetterGenModel("dateTimeMapper", object, null, "Y-m-d");
        }

        @Override
        public GetterGenModel caseDateTimeType(DateTimeType object) {
            return new GetterGenModel("dateTimeMapper", object, null, "Y-m-d?H:i:s.uT");
        }

        @Override
        public GetterGenModel caseStringType(StringType object) {
            return new GetterGenModel("scalarMapper", object, null, "string");
        }

        @Override
        public GetterGenModel caseNumberType(NumberType object) {
            switch (object.getFormat()) {
                case INT:
                case INT8:
                case INT16:
                case INT32:
                case INT64:
                    return new GetterGenModel("scalarMapper", object, null, "int");
                default:
                    return new GetterGenModel("scalarMapper", object, null, "float");
            }
        }

        public GetterGenModel caseIntegerType(IntegerType object) {
            return new GetterGenModel("scalarMapper", object, null, "int");
        }

        @Override
        public GetterGenModel caseArrayType(final ArrayType arrayType) {
            if (arrayType.getItems() == null) {
                return null;
            } else if (arrayType.getItems() instanceof UnionType || arrayType.getItems() instanceof StringType || BuiltinType.of(arrayType.getItems().getName()).isPresent()) {
                return new GetterGenModel("scalarMapper", arrayType, null, "array");
            } else {
                return new GetterGenModel("arrayMapper", arrayType, null, "array");
            }
        }

        @Override
        public GetterGenModel caseObjectType(final ObjectType objectType) {
            if (BuiltinType.of(objectType.getName()).isPresent()) {
                return null;
            } else {
                return new GetterGenModel("classMapper", objectType, null);
            }
        }

        @Override
        public GetterGenModel defaultCase(EObject object) {
            return new GetterGenModel("defaultMapper", (AnyType)object, null);
        }
    }

    static class PropertySetterVisitor extends GeneratorHelper.PropertySetterVisitor {
        final PropertyGenModel property;

        PropertySetterVisitor(final PropertyGenModel property) {
            super(property);
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
            return new SetterGenModel("scalarSetter", property, scalarType, scalarType);
        }

        @Override
        public SetterGenModel caseArrayType(final ArrayType arrayType) {
            if (arrayType.getItems() == null || BuiltinType.of(arrayType.getItems().getName()).isPresent()) {
                return null;
            } else if (arrayType.getItems() instanceof UnionType) {
                return new SetterGenModel("arraySetter", property, "array", "array");
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

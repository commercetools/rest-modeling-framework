package io.vrap.rmf.raml.generic.generator.php;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import io.vrap.rmf.raml.model.types.*;
import io.vrap.rmf.raml.model.types.impl.TypesFactoryImpl;
import io.vrap.rmf.raml.model.types.util.TypesSwitch;
import org.eclipse.emf.ecore.EObject;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.capitalize;

public class TypesGenerator extends AbstractGenerator {

    private static final URL RESOURCE = Resources.getResource("./templates/php/type.stg");
    static final String TYPE_MODEL = "model";
    static final String TYPE_INTERFACE = "interface";
    static final String TYPE_COLLECTION_MODEL = "collectionModel";
    static final String TYPE_COLLECTION_INTERFACE = "collectionInterface";
    static final String TYPE_MODEL_MAP = "modelMap";
    static final String TYPE_DISCRIMINATOR_RESOLVER = "discriminatorResolver";
    static final String PACKAGE_NAME = "types";
    public final String vendorName;

    TypesGenerator(final String vendorName) {
        this.vendorName = vendorName;
    }

    public void generate(final List<AnyType> types, final File outputPath) throws IOException {

        generateStatics(outputPath);
        generateTypes(outputPath, types);
        generateMap(outputPath, types);
        generateDiscriminatorResolver(outputPath, types);
        generateCollections(outputPath, types);
    }

    private void generateStatics(final File outputPath) throws IOException{
        final List<String> staticFiles = Lists.newArrayList(
                "jsonObject",
                "jsonCollection",
                "collection",
                "mapIterator",
                "classMap",
                "resourceClassMap"
        );

        for(String staticFile: staticFiles) {
            generateFile(generateStatic(staticFile), new File(outputPath, capitalize(staticFile) + ".php"));
        }
    }

    private void generateTypes(final File outputPath, List<AnyType> types) throws IOException {
        final TypeGeneratingVisitor interfaceGeneratingVisitor = createVisitor(PACKAGE_NAME, TYPE_INTERFACE);
        final TypeGeneratingVisitor modelGeneratingVisitor =  createVisitor(PACKAGE_NAME, TYPE_MODEL);

        for (final AnyType anyType : types) {
            final File interfaceFile = new File(outputPath, anyType.getName().concat(".php"));
            final File modelFile = new File(outputPath, anyType.getName().concat("Model.php"));

            generateFile(generateType(interfaceGeneratingVisitor, anyType), interfaceFile);
            generateFile(generateType(modelGeneratingVisitor, anyType), modelFile);
        }
    }

    private void generateCollections(final File outputPath, final List<AnyType> types) throws IOException {
        final TypeGeneratingVisitor collectionInterfaceGeneratingVisitor = createVisitor(PACKAGE_NAME, TYPE_COLLECTION_INTERFACE);
        final TypeGeneratingVisitor collectionModelGeneratingVisitor =  createVisitor(PACKAGE_NAME, TYPE_COLLECTION_MODEL);

        for (final AnyType anyType : types) {
            if (anyType instanceof ObjectType) {
                for( final Property property : ((ObjectType) anyType).getProperties()) {
                    if (property.getType() instanceof ArrayType) {
                        ArrayType arrayType = (ArrayType)property.getType();
                        if (arrayType.getItems() != null && arrayType.getItems() instanceof ObjectType && arrayType.getItems().getName() != null) {
                            final File interfaceFile = new File(outputPath, arrayType.getItems().getName().concat("Collection.php"));
                            final File modelFile = new File(outputPath, arrayType.getItems().getName().concat("CollectionModel.php"));

                            generateFile(generateType(collectionInterfaceGeneratingVisitor, arrayType), interfaceFile);
                            generateFile(generateType(collectionModelGeneratingVisitor, arrayType), modelFile);
                        }
                    }
                }
            }
        }
    }

    private void generateDiscriminatorResolver(final File outputPath, final List<AnyType> types) throws IOException {
        final List<ObjectType> discriminatorTypes = types.stream().filter(anyType -> anyType instanceof ObjectType && ((ObjectType) anyType).getDiscriminator() != null)
                .map(anyType -> (ObjectType)anyType)
                .collect(Collectors.toList());
        final STGroupFile stGroup = createSTGroup(RESOURCE);
        for (final ObjectType objectType : discriminatorTypes) {
            final ST st = stGroup.getInstanceOf(TYPE_DISCRIMINATOR_RESOLVER);
            st.add("package", PACKAGE_NAME);
            st.add("vendorName", vendorName);
            st.add("type", objectType);
            st.add("subTypes", objectType.subTypes());
            generateFile(st.render(), new File(outputPath, objectType.getName() + "DiscriminatorResolver.php"));
        }
    }
    private void generateMap(final File outputPath, final List<AnyType> types) throws IOException {
        final List<String> objectTypes = types.stream().filter(anyType -> anyType instanceof ObjectType).map(AnyType::getName).collect(Collectors.toList());
        for (final AnyType anyType : types) {
            if (anyType instanceof ObjectType) {
                for (final Property property : ((ObjectType) anyType).getProperties()) {
                    if (property.getType() instanceof ArrayType) {
                        ArrayType arrayType = (ArrayType) property.getType();
                        if (arrayType.getItems() != null && arrayType.getItems() instanceof ObjectType && arrayType.getItems().getName() != null) {
                            String collectionName = arrayType.getItems().getName() + "Collection";
                            if (!objectTypes.contains(collectionName)) {
                                objectTypes.add(collectionName);
                            }
                        }
                    }
                }
            }
        }

        final STGroupFile stGroup = createSTGroup(RESOURCE);
        final ST st = stGroup.getInstanceOf(TYPE_MODEL_MAP);
        st.add("vendorName", vendorName);
        st.add("package", PACKAGE_NAME);

        objectTypes.sort(Comparator.naturalOrder());
        st.add("types", objectTypes);
        generateFile(st.render(), new File(outputPath, "ModelClassMap.php"));
    }

    @VisibleForTesting
    String generateStatic(final String name) {
        final TypesFactory f = new TypesFactoryImpl();
        ObjectType dummy = f.createObjectType();
        dummy.setName(name);
        return generateType(createVisitor(PACKAGE_NAME, name), dummy);
    }

    @VisibleForTesting
    TypeGeneratingVisitor createVisitor(final String packageName, final String type) {
        return new TypeGeneratingVisitor(vendorName, packageName, createSTGroup(RESOURCE), type);
    }

    @VisibleForTesting
    String generateType(final TypeGeneratingVisitor visitor, final AnyType type) {
        return visitor.doSwitch(type);
    }

    private class SerializerGeneratingVisitor extends TypesSwitch<String> {
        private final STGroupFile stGroup;
        private final Property property;

        public SerializerGeneratingVisitor(final STGroupFile stGroup, final Property property) {
            this.stGroup = stGroup;
            this.property = property;
        }

        @Override
        public String caseTimeOnlyType(TimeOnlyType object) {
            return dateTimeMapper("timeSerializer", "H:i:s.u");
        }

        @Override
        public String caseDateOnlyType(DateOnlyType object) {
            return dateTimeMapper("dateSerializer", "Y-m-d");
        }

        @Override
        public String caseDateTimeType(DateTimeType object) {
            return dateTimeMapper("dateTimeSerializer", "c");
        }

        private String dateTimeMapper(final String serializer, final String dateTimeFormat)
        {
            final ST st = stGroup.getInstanceOf(serializer);
            st.add("property", property);
            st.add("dateTimeFormat", dateTimeFormat);
            return st.render();
        }
    }

    private class PropertyGeneratingVisitor extends TypesSwitch<String> {
        private final STGroupFile stGroup;
        private final Property property;

        public PropertyGeneratingVisitor(final STGroupFile stGroup, final Property property) {
            this.stGroup = stGroup;
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

        @Override
        public String caseTimeOnlyType(TimeOnlyType object) {
            return dateTimeMapper("H:i:s.u");
        }

        @Override
        public String caseDateOnlyType(DateOnlyType object) {
            return dateTimeMapper("Y-m-d");
        }

        @Override
        public String caseDateTimeType(DateTimeType object) {
            return dateTimeMapper("Y-m-d?H:i:s.uT");
        }

        private String dateTimeMapper(final String dateTimeFormat)
        {
            final ST st = stGroup.getInstanceOf("dateTimeMapper");
            st.add("property", property);
            st.add("dateTimeFormat", dateTimeFormat);
            return st.render();
        }

        private String scalarMapper(final String scalarType)
        {
            final ST st = stGroup.getInstanceOf("scalarMapper");
            st.add("property", property);
            st.add("scalarType", scalarType);
            return st.render();
        }

        @Override
        public String caseArrayType(final ArrayType arrayType) {
            if (arrayType.getItems() == null || arrayType.getItems().getName() == null) {
                return null;
            } else {
                final ST st = stGroup.getInstanceOf("arrayMapper");
                st.add("property", property);
                return st.render();
            }
        }

        @Override
        public String caseObjectType(final ObjectType objectType) {
            if (objectType.getName() == null) {
                return null;
            } else {
                final ST st = stGroup.getInstanceOf("classMapper");
                st.add("property", property);
                return st.render();
            }
        }

        @Override
        public String defaultCase(EObject object) {
            final ST st = stGroup.getInstanceOf("defaultMapper");
            st.add("property", property);
            return st.render();
        }
    }
    private class TypeGeneratingVisitor extends TypesSwitch<String> {
        private final String vendorName;
        private final String packageName;
        private final STGroupFile stGroup;
        private final String type;

        TypeGeneratingVisitor(final String namespace, final String packageName, final STGroupFile stGroup, final String type) {
            this.stGroup = stGroup;
            this.vendorName = namespace;
            this.packageName = packageName;
            this.type = type;
        }

        @Override
        public String caseStringType(final StringType stringType) {
            if (stringType.getEnum().isEmpty()) {
                return null;
            }
            return null;
        }

        @Override
        public String caseArrayType(final ArrayType arrayType) {
            final AnyType items = arrayType.getItems();
            if (items == null || items.getName() == null) {
                return null;
            }
            final ST st = stGroup.getInstanceOf(type);
            st.add("vendorName", vendorName);
            st.add("type", items);
            final Boolean builtInParentType = items.getType() == null || BuiltinType.of(items.getName()).isPresent();
            st.add("builtInParent", builtInParentType);
            st.add("package", packageName);
            return st.render();
        }

        @Override
        public String caseObjectType(final ObjectType objectType) {
            if (objectType.getName() == null) {
                return null;
            } else {
                final ST st = stGroup.getInstanceOf(type);
                st.add("vendorName", vendorName);
                st.add("type", objectType);
//                objectType.getProperties().stream().map(property -> property.getType().subTypes().)
                final Boolean builtInParentType = objectType.getType() == null || BuiltinType.of(objectType.getType().getName()).isPresent();
                st.add("builtInParent", builtInParentType);
                st.add("package", packageName);

                if (type.equals(TYPE_MODEL)) {
                    List<String> properties = objectType.getProperties().stream().map(property -> {
                        PropertyGeneratingVisitor visitor = new PropertyGeneratingVisitor(stGroup, property);
                        return visitor.doSwitch(property.getType());
                    }).collect(Collectors.toList());

                    st.add("properties", properties);

                    List<String> serializers = objectType.getProperties().stream().map(property -> {
                        SerializerGeneratingVisitor visitor = new SerializerGeneratingVisitor(stGroup, property);
                        return visitor.doSwitch(property.getType());
                    }).filter(Objects::nonNull).collect(Collectors.toList());
                    st.add("serializers", serializers);

                }
                return st.render();
            }
        }
    }
}

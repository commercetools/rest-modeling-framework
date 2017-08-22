package io.vrap.rmf.raml.generic.generator.php;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import io.vrap.rmf.raml.generic.generator.AbstractTemplateGenerator;
import io.vrap.rmf.raml.model.facets.StringInstance;
import io.vrap.rmf.raml.model.types.*;
import io.vrap.rmf.raml.model.types.impl.TypesFactoryImpl;
import io.vrap.rmf.raml.model.types.util.TypesSwitch;
import org.eclipse.emf.ecore.EObject;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.capitalize;

public class TypesGenerator extends AbstractTemplateGenerator {

    private static final URL RESOURCE = Resources.getResource("./templates/php/type.stg");
    static final String TYPE_MODEL = "model";
    static final String TYPE_INTERFACE = "interface";
    static final String TYPE_COLLECTION_MODEL = "collectionModel";
    static final String TYPE_COLLECTION_INTERFACE = "collectionInterface";
    static final String TYPE_MODEL_MAP = "modelMap";
    static final String TYPE_DISCRIMINATOR_RESOLVER = "discriminatorResolver";
    static final String PACKAGE_NAME = "types";
    private final String vendorName;
    private final AnyAnnotationType packageAnnotationType;

    TypesGenerator(final String vendorName, final AnyAnnotationType packageAnnotationType)
    {
        this.vendorName = vendorName;
        this.packageAnnotationType = packageAnnotationType;
    }

    public void generate(final List<AnyType> types, final File outputPath) throws IOException {

        generateTypes(outputPath, types);
        generateMapFile(outputPath, types);
        generateDiscriminatorResolver(outputPath, types);
        generateCollections(outputPath, types);
    }

    private void generateTypes(final File outputPath, List<AnyType> types) throws IOException {
        final TypeGeneratingVisitor interfaceGeneratingVisitor = createVisitor(PACKAGE_NAME, TYPE_INTERFACE);
        final TypeGeneratingVisitor modelGeneratingVisitor =  createVisitor(PACKAGE_NAME, TYPE_MODEL);

        for (final AnyType anyType : types) {
            final String packageFolder = getPackageFolder(anyType);
            final File interfaceFile = new File(outputPath, packageFolder + anyType.getName().concat(".php"));
            final File modelFile = new File(outputPath, packageFolder + anyType.getName().concat("Model.php"));

            generateFile(generateType(interfaceGeneratingVisitor, anyType), interfaceFile);
            generateFile(generateType(modelGeneratingVisitor, anyType), modelFile);
        }
    }

    private String getPackageFolder(AnyType anyType) {
        return getPackageFolder(anyType, "/");
    }

    private String getPackageFolder(AnyType anyType, final String glue) {
        return anyType.getAnnotations().stream().filter(annotation -> annotation.getType().equals(packageAnnotationType))
                .map(annotation -> ((StringInstance)annotation.getValue()).getValue() + glue).findFirst().orElse("");
    }

    private void generateCollections(final File outputPath, final List<AnyType> types) throws IOException {
        final TypeGeneratingVisitor collectionInterfaceGeneratingVisitor = createVisitor(PACKAGE_NAME, TYPE_COLLECTION_INTERFACE);
        final TypeGeneratingVisitor collectionModelGeneratingVisitor = createVisitor(PACKAGE_NAME, TYPE_COLLECTION_MODEL);

        for (final AnyType anyType : types) {
            if (anyType instanceof ObjectType) {
                for( final Property property : ((ObjectType) anyType).getProperties()) {
                    if (property.getType() instanceof ArrayType) {
                        ArrayType arrayType = (ArrayType)property.getType();
                        if (arrayType.getItems() != null && arrayType.getItems() instanceof ObjectType && arrayType.getItems().getName() != null) {
                            final String packageFolder = getPackageFolder(arrayType.getItems());
                            final File interfaceFile = new File(outputPath, packageFolder + arrayType.getItems().getName().concat("Collection.php"));
                            final File modelFile = new File(outputPath, packageFolder + arrayType.getItems().getName().concat("CollectionModel.php"));

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
            final String packageFolder = getPackageFolder(objectType);

            generateFile(st.render(), new File(outputPath, packageFolder + objectType.getName() + "DiscriminatorResolver.php"));
        }
    }

    @VisibleForTesting
    String generateMap(final List<AnyType> types) {
        final List<String> objectTypes = types.stream()
                .filter(anyType -> anyType instanceof ObjectType)
                .map(anyType -> getPackageFolder(anyType, "\\") + anyType.getName())
                .collect(Collectors.toList());
        for (final AnyType anyType : types) {
            if (anyType instanceof ObjectType) {
                for (final Property property : ((ObjectType) anyType).getProperties()) {
                    if (property.getType() instanceof ArrayType) {
                        ArrayType arrayType = (ArrayType) property.getType();
                        if (arrayType.getItems() != null && arrayType.getItems() instanceof ObjectType && arrayType.getItems().getName() != null) {
                            String collectionName = getPackageFolder(arrayType.getItems(), "\\") + arrayType.getItems().getName() + "Collection";
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
        return st.render();
    }

    private void generateMapFile(final File outputPath, final List<AnyType> types) throws IOException {
        generateFile(generateMap(types), new File(outputPath, "ModelClassMap.php"));
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

    abstract class PropertyVisitor extends TypesSwitch<String> {
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

    private class PropertyGetterGeneratingVisitor extends PropertyVisitor {
        final STGroupFile stGroup;
        final Property property;

        PropertyGetterGeneratingVisitor(final STGroupFile stGroup, final Property property) {
            this.stGroup = stGroup;
            this.property = property;
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

        String dateTimeMapper(final String dateTimeFormat)
        {
            final ST st = stGroup.getInstanceOf("dateTimeGetter");
            st.add("property", property);
            st.add("parent", property.eContainer());
            st.add("dateTimeFormat", dateTimeFormat);
            return st.render();
        }

        String scalarMapper(final String scalarType)
        {
            final ST st = stGroup.getInstanceOf("scalarGetter");
            st.add("property", property);
            st.add("parent", property.eContainer());
            st.add("scalarType", scalarType);
            return st.render();
        }

        @Override
        public String caseArrayType(final ArrayType arrayType) {
            if (arrayType.getItems() == null || arrayType.getItems().getName() == null) {
                return null;
            } else {
                final ST st = stGroup.getInstanceOf("arrayGetter");
                st.add("parent", property.eContainer());
                st.add("property", property);
                return st.render();
            }
        }

        @Override
        public String caseObjectType(final ObjectType objectType) {
            if (objectType.getName() == null) {
                return null;
            } else {
                final ST st = stGroup.getInstanceOf("classGetter");
                st.add("parent", property.eContainer());
                st.add("property", property);
                return st.render();
            }
        }

        @Override
        public String defaultCase(EObject object) {
            final ST st = stGroup.getInstanceOf("defaultGetter");
            st.add("parent", property.eContainer());
            st.add("property", property);
            return st.render();
        }
    }

    private class PropertyTypeVisitor extends PropertyVisitor {

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
            if (arrayType.getItems() == null || arrayType.getItems().getName() == null) {
                return null;
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

    private class PropertyInterfaceSetterGeneratingVisitor extends PropertyVisitor {
        final STGroupFile stGroup;
        final Property property;

        PropertyInterfaceSetterGeneratingVisitor(final STGroupFile stGroup, final Property property) {
            this.stGroup = stGroup;
            this.property = property;
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

        private String dateTimeMapper()
        {
            final ST st = stGroup.getInstanceOf("propertyInterfaceSet");
            st.add("property", property);
            st.add("propertyType", null);
            st.add("paramType", "\\DateTimeImmutable|\\DateTime|string");
            return st.render();
        }

        String scalarMapper(final String scalarType)
        {
            final ST st = stGroup.getInstanceOf("propertyInterfaceSet");
            st.add("property", property);
            st.add("propertyType", null);
            st.add("paramType", scalarType);
            return st.render();
        }

        @Override
        public String caseArrayType(final ArrayType arrayType) {
            if (arrayType.getItems() == null || arrayType.getItems().getName() == null) {
                return null;
            } else {
                final ST st = stGroup.getInstanceOf("propertyInterfaceSet");
                st.add("property", property);
                st.add("propertyType", arrayType.getItems().getName() + "Collection");
                st.add("paramType", arrayType.getItems().getName() + "Collection");
                return st.render();
            }
        }

        @Override
        public String caseObjectType(final ObjectType objectType) {
            if (objectType.getName() == null) {
                return null;
            } else {
                final ST st = stGroup.getInstanceOf("propertyInterfaceSet");
                st.add("property", property);
                st.add("propertyType", objectType.getName());
                st.add("paramType", objectType.getName());
                return st.render();
            }
        }

        @Override
        public String defaultCase(EObject object) {
            final ST st = stGroup.getInstanceOf("propertyInterfaceSet");
            st.add("property", property);
            st.add("propertyType", null);
            st.add("paramType", "");
            return st.render();
        }
    }

    private class PropertySetterGeneratingVisitor extends PropertyVisitor {
        final STGroupFile stGroup;
        final Property property;

        PropertySetterGeneratingVisitor(final STGroupFile stGroup, final Property property) {
            this.stGroup = stGroup;
            this.property = property;
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

        private String dateTimeMapper()
        {
            final ST st = stGroup.getInstanceOf("dateTimeSetter");
            st.add("property", property);
            return st.render();
        }

        String scalarMapper(final String scalarType)
        {
            final ST st = stGroup.getInstanceOf("scalarSetter");
            st.add("property", property);
            st.add("scalarType", scalarType);
            return st.render();
        }

        @Override
        public String caseArrayType(final ArrayType arrayType) {
            if (arrayType.getItems() == null || arrayType.getItems().getName() == null) {
                return null;
            } else {
                final ST st = stGroup.getInstanceOf("arraySetter");
                st.add("property", property);
                return st.render();
            }
        }

        @Override
        public String caseObjectType(final ObjectType objectType) {
            if (objectType.getName() == null) {
                return null;
            } else {
                final ST st = stGroup.getInstanceOf("classSetter");
                st.add("property", property);
                return st.render();
            }
        }

        @Override
        public String defaultCase(EObject object) {
            final ST st = stGroup.getInstanceOf("defaultSetter");
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
            Annotation packageAnnotation = items.getAnnotations().stream().filter(annotation -> annotation.getType().equals(packageAnnotationType)).findFirst().orElse(null);
            st.add("typePackage", packageAnnotation);

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
                final Boolean builtInParentType = objectType.getType() == null || BuiltinType.of(objectType.getType().getName()).isPresent();
                st.add("builtInParent", builtInParentType);
                st.add("package", packageName);
                Annotation packageAnnotation = objectType.getAnnotations().stream().filter(annotation -> annotation.getType().equals(packageAnnotationType)).findFirst().orElse(null);
                st.add("typePackage", packageAnnotation);
                final String typeFolder = getPackageFolder(objectType, "\\");
                final Set<String> uses = objectType.getProperties().stream()
                        .filter(property -> property.getType() instanceof ObjectType)
                        .filter(property -> property.getType().getAnnotations().stream().anyMatch(annotation -> annotation.getType().equals(packageAnnotationType)))
                        .filter(property -> !getPackageFolder(property.getType(), "\\").equals(typeFolder))
                        .map(property -> {
                            final String typePackage = getPackageFolder(property.getType(), "\\");
                            return vendorName + "\\" + capitalize(packageName) + "\\" + typePackage + property.getType().getName();
                        })
                        .collect(Collectors.toSet());
                uses.addAll(
                        objectType.getProperties().stream()
                                .filter(property -> property.getType() instanceof ArrayType)
                                .map(property -> ((ArrayType)property.getType()).getItems())
                                .filter(anyType -> anyType instanceof ObjectType)
                                .filter(anyType -> anyType.getAnnotations().stream().anyMatch(annotation -> annotation.getType().equals(packageAnnotationType)))
                                .filter(anyType -> !getPackageFolder(anyType, "\\").equals(typeFolder))
                                .map(anyType -> {
                                    final String typePackage = getPackageFolder(anyType, "\\");
                                    return vendorName + "\\" + capitalize(packageName) + "\\" + typePackage + anyType.getName() + "Collection";
                                })
                                .collect(Collectors.toSet())
                );
                final String typePackageFolder = getPackageFolder(objectType.getType(), "\\");
                if (!builtInParentType && !typePackageFolder.equals(getPackageFolder(objectType, "\\"))) {
                    final String suffix = type.equals(TYPE_MODEL) ? "Model" : "";
                    uses.add(vendorName + "\\" + capitalize(packageName) + "\\" + typePackageFolder + objectType.getType().getName() + suffix);
                }
                st.add("uses", uses);
                if (type.equals(TYPE_INTERFACE)) {
                    final List<Property> typeProperties;
                    if(objectType.getType() instanceof ObjectType) {
                        ObjectType superType = (ObjectType)objectType.getType();
                        typeProperties = objectType.getProperties().stream().filter(property -> superType.getProperty(property.getName()) == null).collect(Collectors.toList());
                    } else {
                        typeProperties = Lists.newArrayList();
                    }
                    st.add("typeProperties", typeProperties);
                    final List<String> propertySetters = objectType.getProperties().stream().map(property -> {
                        PropertyInterfaceSetterGeneratingVisitor visitor = new PropertyInterfaceSetterGeneratingVisitor(stGroup, property);
                        return visitor.doSwitch(property.getType());
                    }).collect(Collectors.toList());
                    st.add("propertySetters", propertySetters);
                }
                if (type.equals(TYPE_MODEL) || type.equals(TYPE_INTERFACE)) {
                    List<String> propertyTypes = objectType.getProperties().stream().map(property -> {
                        PropertyTypeVisitor visitor = new PropertyTypeVisitor();
                        return visitor.doSwitch(property.getType());
                    }).collect(Collectors.toList());
                    st.add("propertyTypes", propertyTypes);
                }
                if (type.equals(TYPE_MODEL)) {
                    List<String> propertyGetters = objectType.getProperties().stream().map(property -> {
                        PropertyGetterGeneratingVisitor visitor = new PropertyGetterGeneratingVisitor(stGroup, property);
                        return visitor.doSwitch(property.getType());
                    }).collect(Collectors.toList());

                    st.add("propertyGetters", propertyGetters);

                    List<String> propertySetters = objectType.getProperties().stream().map(property -> {
                        PropertySetterGeneratingVisitor visitor = new PropertySetterGeneratingVisitor(stGroup, property);
                        return visitor.doSwitch(property.getType());
                    }).collect(Collectors.toList());

                    st.add("propertySetters", propertySetters);

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

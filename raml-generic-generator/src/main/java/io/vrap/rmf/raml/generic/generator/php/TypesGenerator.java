package io.vrap.rmf.raml.generic.generator.php;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import io.vrap.rmf.raml.generic.generator.AbstractTemplateGenerator;
import io.vrap.rmf.raml.model.elements.IdentifiableElement;
import io.vrap.rmf.raml.model.facets.BooleanInstance;
import io.vrap.rmf.raml.model.facets.ObjectInstance;
import io.vrap.rmf.raml.model.facets.StringInstance;
import io.vrap.rmf.raml.model.types.*;
import io.vrap.rmf.raml.model.types.impl.TypesFactoryImpl;
import io.vrap.rmf.raml.model.types.util.TypesSwitch;
import org.eclipse.emf.ecore.EObject;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.capitalize;

public class TypesGenerator extends AbstractTemplateGenerator {
    private static final String resourcesPath = "./templates/php/";
    static final String TYPE_MODEL = "model";
    static final String TYPE_INTERFACE = "interface";
    static final String TYPE_COLLECTION_MODEL = "collectionModel";
    static final String TYPE_COLLECTION_INTERFACE = "collectionInterface";
    static final String TYPE_MODEL_MAP = "modelMap";
    static final String TYPE_DISCRIMINATOR_RESOLVER = "discriminatorResolver";
    static final String PACKAGE_NAME = "types";
    private final String vendorName;
    private final AnyAnnotationType packageAnnotationType;
    private final AnyAnnotationType identifierAnnotationType;

    TypesGenerator(final String vendorName, final AnyAnnotationType packageAnnotationType, final AnyAnnotationType identifierAnnotationType)
    {
        this.vendorName = vendorName;
        this.packageAnnotationType = packageAnnotationType;
        this.identifierAnnotationType = identifierAnnotationType;
    }

    public List<File> generate(final List<AnyType> types, final File outputPath) throws IOException {

        final List<File> f = Lists.newArrayList();
        f.addAll(generateTypes(outputPath, types));
        f.addAll(generateMapFile(outputPath, types));
        f.addAll(generateCollections(outputPath, types));

        return f;
    }

    private List<File> generateTypes(final File outputPath, List<AnyType> types) throws IOException {
        final TypeGeneratingVisitor interfaceGeneratingVisitor = createVisitor(PACKAGE_NAME, TYPE_INTERFACE);
        final TypeGeneratingVisitor modelGeneratingVisitor =  createVisitor(PACKAGE_NAME, TYPE_MODEL);

        final List<File> f = Lists.newArrayList();
        for (final AnyType anyType : types) {
            final String packageFolder = getPackageFolder(anyType);
            final File interfaceFile = new File(outputPath, packageFolder + anyType.getName().concat(".php"));
            final File modelFile = new File(outputPath, packageFolder + anyType.getName().concat("Model.php"));

            f.add(generateFile(generateType(interfaceGeneratingVisitor, anyType), interfaceFile));
            f.add(generateFile(generateType(modelGeneratingVisitor, anyType), modelFile));
        }
        return f;
    }

    private String getPackageFolder(AnyType anyType) {
        return getPackageFolder(anyType, "/");
    }

    private String getPackageFolder(AnyType anyType, final String glue) {
        return anyType.getAnnotations().stream().filter(annotation -> annotation.getType().equals(packageAnnotationType))
                .map(annotation -> ((StringInstance)annotation.getValue()).getValue() + glue).findFirst().orElse("");
    }

    private List<File> generateCollections(final File outputPath, final List<AnyType> types) throws IOException {
        final TypeGeneratingVisitor collectionInterfaceGeneratingVisitor = createVisitor(PACKAGE_NAME, TYPE_COLLECTION_INTERFACE);
        final TypeGeneratingVisitor collectionModelGeneratingVisitor = createVisitor(PACKAGE_NAME, TYPE_COLLECTION_MODEL);

        final List<File> f = Lists.newArrayList();
        for (final AnyType anyType : types) {
            if (anyType instanceof ObjectType) {
                for( final Property property : ((ObjectType) anyType).getProperties()) {
                    if (property.getType() instanceof ArrayType) {
                        ArrayType arrayType = (ArrayType)property.getType();
                        if (arrayType.getItems() != null && arrayType.getItems() instanceof ObjectType && arrayType.getItems().getName() != null) {
                            final String packageFolder = getPackageFolder(arrayType.getItems());
                            final File interfaceFile = new File(outputPath, packageFolder + arrayType.getItems().getName().concat("Collection.php"));
                            final File modelFile = new File(outputPath, packageFolder + arrayType.getItems().getName().concat("CollectionModel.php"));

                            f.add(generateFile(generateType(collectionInterfaceGeneratingVisitor, arrayType), interfaceFile));
                            f.add(generateFile(generateType(collectionModelGeneratingVisitor, arrayType), modelFile));
                        }
                    }
                }
            }
        }
        return f;
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

        final STGroupFile stGroup = createSTGroup(Resources.getResource(resourcesPath + "modelmap.stg"));
        final ST st = stGroup.getInstanceOf(TYPE_MODEL_MAP);
        st.add("vendorName", vendorName);
        st.add("package", PACKAGE_NAME);

        objectTypes.sort(Comparator.naturalOrder());
        st.add("types", objectTypes);
        return st.render();
    }

    private List<File> generateMapFile(final File outputPath, final List<AnyType> types) throws IOException {
        return Lists.newArrayList(generateFile(generateMap(types), new File(outputPath, "ModelClassMap.php")));
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
        return new TypeGeneratingVisitor(vendorName, packageName, createSTGroup(Resources.getResource(resourcesPath + type + ".stg")), type);
    }

    @VisibleForTesting
    String generateType(final TypeGeneratingVisitor visitor, final AnyType type) {
        return visitor.doSwitch(type);
    }

    static abstract class PropertyVisitor extends TypesSwitch<String> {
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

    static class PropertyTypeVisitor extends PropertyVisitor {

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
            if (items instanceof ObjectType && (type.equals(TYPE_COLLECTION_MODEL) || type.equals(TYPE_COLLECTION_INTERFACE))) {
                final List<String> identifiers = ((ObjectType)items).getAllProperties().stream()
                        .filter(property -> {
                            Annotation identifier = property.getAnnotation(identifierAnnotationType);
                            return identifier != null;
                        })
                        .map(IdentifiableElement::getName)
                        .collect(Collectors.toList());
                st.add("identifiers", identifiers);
            }
            return st.render();
        }

        @Override
        public String caseObjectType(final ObjectType objectType) {
            if (objectType.getName() == null) {
                return null;
            } else {
                final MetaType metaType = new MetaType(objectType);

                final ST st = stGroup.getInstanceOf(type);
                st.add("vendorName", vendorName);
                if (type.equals(TYPE_INTERFACE) || type.equals(TYPE_MODEL)) {
                    st.add("type", metaType);
                } else {
                    st.add("type", objectType);
                    st.add("builtInParent", metaType.getHasBuiltinParent());
                    st.add("package", packageName);
                    st.add("typePackage", metaType.getPackage() != null ? metaType.getPackage().getAnnotation() : null);

                    final String typeFolder = getPackageFolder(objectType, "\\");
                    final Set<String> uses = objectType.getProperties().stream()
                            .filter(property -> property.getType() instanceof ObjectType || property.getType() instanceof ArrayType && ((ArrayType) property.getType()).getItems() instanceof ObjectType)
                            .filter(property -> {
                                AnyType t = property.getType() instanceof ArrayType ? ((ArrayType) property.getType()).getItems() : property.getType();
                                return !getPackageFolder(t, "\\").equals(typeFolder);
                            })
                            .map(property -> {
                                AnyType t = property.getType() instanceof ArrayType ? ((ArrayType) property.getType()).getItems() : property.getType();
                                final String typePackage = getPackageFolder(t , "\\");
                                return vendorName + "\\" + capitalize(packageName) + "\\" + typePackage + (new PropertyTypeVisitor()).doSwitch(t);
                            })
                            .collect(Collectors.toSet());
                    uses.addAll(
                            objectType.getProperties().stream()
                                    .filter(property -> property.getType() instanceof ObjectType)
                                    .filter(property -> ((ObjectType)property.getType()).getDiscriminator() != null)
                                    .map(property -> vendorName + "\\Base\\DiscriminatorResolver")
                                    .collect(Collectors.toSet())
                    );
                    uses.addAll(
                            objectType.getProperties().stream()
                                    .map(property -> getBaseProperty(property))
                                    .filter(property -> property.getType() instanceof ObjectType || property.getType() instanceof ArrayType && ((ArrayType) property.getType()).getItems() instanceof ObjectType)
                                    .filter(property -> {
                                        AnyType t = property.getType() instanceof ArrayType ? ((ArrayType) property.getType()).getItems() : property.getType();
                                        return !getPackageFolder(t, "\\").equals(typeFolder);
                                    })
                                    .map(property -> {
                                        AnyType t = property.getType() instanceof ArrayType ? ((ArrayType) property.getType()).getItems() : property.getType();
                                        final String typePackage = getPackageFolder(t , "\\");
                                        return vendorName + "\\" + capitalize(packageName) + "\\" + typePackage + (new PropertyTypeVisitor()).doSwitch(property.getType());
                                    })
                                    .collect(Collectors.toSet())
                    );
                    final String typePackageFolder = getPackageFolder(objectType.getType(), "\\");
                    if (!metaType.getHasBuiltinParent() && !typePackageFolder.equals(getPackageFolder(objectType, "\\"))) {
                        final String suffix = type.equals(TYPE_MODEL) ? "Model" : "";
                        uses.add(vendorName + "\\" + capitalize(packageName) + "\\" + typePackageFolder + objectType.getType().getName() + suffix);
                    }
                    st.add("uses", uses);

                    if (type.equals(TYPE_COLLECTION_MODEL) || type.equals(TYPE_COLLECTION_INTERFACE)) {
                        final List<String> identifiers = objectType.getAllProperties().stream()
                                .filter(property -> {
                                    Annotation identifier = property.getAnnotation(identifierAnnotationType);
                                    if (identifier != null) {
                                        BooleanInstance t = (BooleanInstance)identifier.getValue();
                                        return t.getValue();
                                    }
                                    return false;
                                })
                                .map(IdentifiableElement::getName)
                                .collect(Collectors.toList());
                        st.add("identifiers", identifiers);
                    }
                }

                return st.render();
            }
        }
    }
}

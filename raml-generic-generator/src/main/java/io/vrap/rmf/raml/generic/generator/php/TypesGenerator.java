package io.vrap.rmf.raml.generic.generator.php;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import io.vrap.rmf.raml.generic.generator.AbstractTemplateGenerator;
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

public class TypesGenerator extends AbstractTemplateGenerator {
    private static final String resourcesPath = "./templates/php/";
    static final String TYPE_MODEL = "model";
    static final String TYPE_INTERFACE = "interface";
    static final String TYPE_COLLECTION_MODEL = "collectionModel";
    static final String TYPE_COLLECTION_INTERFACE = "collectionInterface";
    static final String TYPE_MODEL_MAP = "modelMap";
    static final String PACKAGE_NAME = "types";
    private final String vendorName;

    TypesGenerator(final String vendorName)
    {
        this.vendorName = vendorName;
    }

    public List<File> generate(final List<AnyType> types, final File outputPath) throws IOException {

        final List<File> f = Lists.newArrayList();
        f.addAll(generateTypes(outputPath, types));
        f.addAll(generateMapFile(outputPath, types));
        f.addAll(generateCollections(outputPath, types));

        return f;
    }

    private List<File> generateTypes(final File outputPath, List<AnyType> types) throws IOException {
        final TypeGeneratingVisitor interfaceGeneratingVisitor = createVisitor(TYPE_INTERFACE);
        final TypeGeneratingVisitor modelGeneratingVisitor =  createVisitor(TYPE_MODEL);

        final List<File> f = Lists.newArrayList();
        for (final AnyType anyType : types) {
            final String packageFolder = new TypeGenModel(anyType).getPackage().getSubPackageFolder();
            final File interfaceFile = new File(outputPath, packageFolder + anyType.getName().concat(".php"));
            final File modelFile = new File(outputPath, packageFolder + anyType.getName().concat("Model.php"));

            f.add(generateFile(generateType(interfaceGeneratingVisitor, anyType), interfaceFile));
            f.add(generateFile(generateType(modelGeneratingVisitor, anyType), modelFile));
        }
        return f;
    }

    private List<File> generateCollections(final File outputPath, final List<AnyType> types) throws IOException {
        final TypeGeneratingVisitor collectionInterfaceGeneratingVisitor = createVisitor(TYPE_COLLECTION_INTERFACE);
        final TypeGeneratingVisitor collectionModelGeneratingVisitor = createVisitor(TYPE_COLLECTION_MODEL);

        final List<File> f = Lists.newArrayList();
        for (final AnyType anyType : types) {
            if (anyType instanceof ObjectType) {
                for( final Property property : ((ObjectType) anyType).getProperties()) {
                    if (property.getType() instanceof ArrayType) {
                        ArrayType arrayType = (ArrayType)property.getType();
                        if (arrayType.getItems() != null && arrayType.getItems() instanceof ObjectType && arrayType.getItems().getName() != null) {
                            final String packageFolder = new TypeGenModel(arrayType.getItems()).getPackage().getSubPackageFolder();
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
        final List<TypeGenModel> objectTypes = types.stream()
                .filter(anyType -> anyType instanceof ObjectType)
                .map(TypeGenModel::new)
                .collect(Collectors.toList());
        for (final AnyType anyType : types) {
            if (anyType instanceof ObjectType) {
                for (final Property property : ((ObjectType) anyType).getProperties()) {
                    if (property.getType() instanceof ArrayType) {
                        ArrayType arrayType = (ArrayType) property.getType();
                        if (arrayType.getItems() != null && arrayType.getItems() instanceof ObjectType && arrayType.getItems().getName() != null) {
                            final CollectionGenModel collection = new CollectionGenModel(arrayType.getItems());
                            if (!objectTypes.contains(collection)) {
                                objectTypes.add(collection);
                            }
                        }
                    }
                }
            }
        }

        final STGroupFile stGroup = createSTGroup(Resources.getResource(resourcesPath + "modelmap.stg"));
        final ST st = stGroup.getInstanceOf(TYPE_MODEL_MAP);
        st.add("vendorName", vendorName);
        st.add("package", TypeGenModel.TYPES);

        objectTypes.sort(Comparator.comparing(TypeGenModel::getName, Comparator.naturalOrder()));
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
        return generateType(createVisitor(name), dummy);
    }

    @VisibleForTesting
    TypeGeneratingVisitor createVisitor(final String type) {
        return new TypeGeneratingVisitor(vendorName, createSTGroup(Resources.getResource(resourcesPath + type + ".stg")), type);
    }

    @VisibleForTesting
    String generateType(final TypeGeneratingVisitor visitor, final AnyType type) {
        return visitor.doSwitch(type);
    }

    private class TypeGeneratingVisitor extends TypesSwitch<String> {
        private final String vendorName;
        private final STGroupFile stGroup;
        private final String type;

        TypeGeneratingVisitor(final String namespace, final STGroupFile stGroup, final String type) {
            this.stGroup = stGroup;
            this.vendorName = namespace;
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
            st.add("type", new CollectionGenModel(items));
            return st.render();
        }

        @Override
        public String caseObjectType(final ObjectType objectType) {
            if (objectType.getName() == null) {
                return null;
            } else {
                final TypeGenModel typeGenModel = new TypeGenModel(objectType);

                final ST st = stGroup.getInstanceOf(type);
                st.add("vendorName", vendorName);
                if (type.equals(TYPE_INTERFACE) || type.equals(TYPE_MODEL)) {
                    st.add("type", typeGenModel);
                } else if (type.equals(TYPE_COLLECTION_INTERFACE) || type.equals(TYPE_COLLECTION_MODEL)) {
                    st.add("type", new CollectionGenModel(objectType));
                }

                return st.render();
            }
        }
    }
}

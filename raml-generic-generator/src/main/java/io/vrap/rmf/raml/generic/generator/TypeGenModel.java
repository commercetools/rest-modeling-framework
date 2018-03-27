package io.vrap.rmf.raml.generic.generator;

import com.google.common.collect.Lists;
import io.vrap.rmf.raml.generic.generator.php.BuilderGenerator;
import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.types.*;
import io.vrap.rmf.raml.model.types.util.TypesSwitch;
import org.eclipse.emf.ecore.EObject;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class TypeGenModel {
    public static final String TYPES = "Types";

    private final AnyType type;

    public TypeGenModel(final AnyType type) {
        this.type = type;
    }

    public Boolean getHasBuiltinParent()
    {
        return new BuiltinParentVisitor().doSwitch(type);
    }

    @Nullable
    public String getName()
    {
        return type.getName();
    }

    private String getNameString()
    {
        return Optional.ofNullable(type.getName()).orElse("");
    }

    public TypeGenModel getParent()
    {
        return new TypeGenModel(type.getType());
    }

    public AnyType getType() {
        return type;
    }

    @Nullable
    public String getDiscriminator()
    {
        if (type instanceof ObjectType) {
            return ((ObjectType) type).getDiscriminator();
        }
        return null;
    }

    @Nullable
    public List<TypeGenModel> getOneOf() {
        if (type instanceof UnionType) {
            return ((UnionType)type).getOneOf().stream().map(TypeGenModel::new).collect(Collectors.toList());
        }
        return null;
    }

    @Nullable
    public String getDiscriminatorValue()
    {
        if (type instanceof ObjectType) {
            return ((ObjectType) type).getDiscriminatorValue();
        }
        return null;
    }

    public Boolean getIsClass()
    {
        return new IsClassVisitor().doSwitch(type);
    }

    public ImportGenModel getImport()
    {
        final String name = GeneratorHelper.getTypeNameVisitor().doSwitch(type);
        return new ImportGenModel(getPackage(), name);
    }

    @Nullable
    public List<TypeGenModel> getSubTypes()
    {
        return type.getSubTypes().stream().map(TypeGenModel::new).sorted(Comparator.comparing(TypeGenModel::getNameString, Comparator.naturalOrder())).collect(Collectors.toList());
    }

    public List<PropertyGenModel> getTypeProperties()
    {
        if (type instanceof ObjectType) {
            final List<Property> allSuperTypeProperties = type.getType() instanceof ObjectType ?
                    ((ObjectType) type.getType()).getAllProperties() :
                    Collections.emptyList();
            return ((ObjectType)type).getProperties().stream()
                    .filter(property -> !(property.getName().startsWith("/") && property.getName().endsWith("/")))
                    .filter(property -> allSuperTypeProperties.stream().filter(property1 -> property1.getName().equals(property.getName())).count() == 0)
                    .map(PropertyGenModel::new)
                    .collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    public List<PropertyGenModel> getNonPatternProperties()
    {
        if (type instanceof ObjectType) {
            return ((ObjectType)type).getProperties().stream()
                    .filter(property -> !(property.getName().startsWith("/") && property.getName().endsWith("/")))
                    .map(PropertyGenModel::new)
                    .collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    public List<PropertyGenModel> getPatternProperties()
    {
        if (type instanceof ObjectType) {
            return ((ObjectType)type).getProperties().stream()
                    .filter(property -> property.getName().startsWith("/") && property.getName().endsWith("/"))
                    .map(PropertyGenModel::new)
                    .collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    @Nullable
    public List<SerializerGenModel> getSerializers()
    {
        if (type instanceof ObjectType) {
            return ((ObjectType)type).getProperties().stream().map(property -> {
                    return GeneratorHelper.getSerializerVisitor(new PropertyGenModel(property)).doSwitch(property.getType());
                }).filter(Objects::nonNull).collect(Collectors.toList());
        }
        return null;
    }

    public List<PropertyGenModel> getUnionProperties()
    {
        if (type instanceof ObjectType) {
            return ((ObjectType) type).getProperties().stream()
                    .filter(property -> !(property.getName().startsWith("/") && property.getName().endsWith("/")))
                    .filter(property -> property.getType() instanceof UnionType)
                    .map(PropertyGenModel::new)
                    .collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    public String getTypeName()
    {
        return GeneratorHelper.getTypeNameVisitor().doSwitch(type);
    }

    public String getReturnTypeName()
    {
        return GeneratorHelper.getTypeNameVisitor().doSwitch(type);
    }

    public Set<ImportGenModel> getTypeImports()
    {
        if (type instanceof ObjectType) {
            ObjectType objectType = (ObjectType)type;

            final Set<ImportGenModel> uses = objectType.getProperties().stream()
                    .filter(property -> property.getType() instanceof ObjectType || property.getType() instanceof ArrayType && ((ArrayType) property.getType()).getItems() instanceof ObjectType)
                    .filter(property -> {
                        AnyType t = property.getType() instanceof ArrayType ? ((ArrayType) property.getType()).getItems() : property.getType();
                        return !new TypeGenModel(t).getPackage().equals(getPackage());
                    })
                    .filter(property -> !new BuiltinVisitor().doSwitch(property.getType()))
                    .map(property -> {
                        AnyType t = property.getType() instanceof ArrayType ? ((ArrayType) property.getType()).getItems() : property.getType();
                        return new TypeGenModel(t).getImport();
                    })
                    .collect(Collectors.toSet());
            uses.addAll(
                    objectType.getProperties().stream()
                            .map(GeneratorHelper::getBaseProperty)
                            .filter(property -> property.getType() instanceof ObjectType || property.getType() instanceof ArrayType && ((ArrayType) property.getType()).getItems() instanceof ObjectType)
                            .filter(property -> {
                                AnyType t = property.getType() instanceof ArrayType ? ((ArrayType) property.getType()).getItems() : property.getType();
                                return !new TypeGenModel(t).getPackage().equals(getPackage());
                            })
                            .filter(property -> !new BuiltinVisitor().doSwitch(property.getType()))
                            .map(property -> new TypeGenModel(property.getType()).getImport())
                            .collect(Collectors.toSet())
            );
            if (!getHasBuiltinParent() && !getParent().getPackage().equals(getPackage())) {
                uses.add(getParent().getImport());
            }
            if (getDiscriminator() != null && getPackage().getHasPackage()) {
                uses.add(new ImportGenModel(new PackageGenModel(TYPES)));
            }
            if (getUpdateType() != null) {
                uses.add(new ImportGenModel(new PackageGenModel(BuilderGenerator.BUILDER), getUpdateType().getName() + BuilderGenerator.BUILDER));
            }
            return uses;
        }
        return null;
    }

    public Boolean getHasNonPatternProperties()
    {
        return getNonPatternProperties().size() > 0;
    }

    public Boolean getHasPatternProperties()
    {
        return getPatternProperties().size() > 0;
    }

    public PackageGenModel getPackage()
    {
        final AnyType t = type instanceof ArrayType ? ((ArrayType) type).getItems() : type;
        Annotation annotation = t.getAnnotation("package", true);
        return new PackageGenModel(TYPES, annotation);
    }

    @Nullable
    public List<PropertyGenModel> getIdentifiers()
    {
        final AnyType t = type instanceof ArrayType ? ((ArrayType) type).getItems() : type;
        AnyAnnotationType identifierAnnotationType = getApi().getAnnotationType("identifier");
        if (t instanceof ObjectType) {
            return  ((ObjectType)t).getAllProperties().stream()
                    .map(PropertyGenModel::new)
                    .filter(property -> property.getIdentifier() != null)
                    .collect(Collectors.toList());
        }
        return null;
    }

    public Api getApi()
    {
        return GeneratorHelper.getParent(type, Api.class);
    }

    @Nullable
    public TypeGenModel getUpdateType() {
        Annotation updateTypeAnnotation = type.getAnnotation("updateType");
        if (updateTypeAnnotation != null) {
            return new TypeGenModel(getApi().getType(((StringInstance)updateTypeAnnotation.getValue()).getValue()));
        }
        return null;
    }

    private class BuiltinParentVisitor extends TypesSwitch<Boolean> {
        @Override
        public Boolean defaultCase(EObject object) {
            return true;
        }

        @Override
        public Boolean caseArrayType(final ArrayType arrayType) {
            final AnyType items = arrayType.getItems();
            return items != null && items.getName() != null && (items.getType() == null || BuiltinType.of(items.getName()).isPresent());
        }

        @Override
        public Boolean caseObjectType(final ObjectType objectType) {
            return objectType.getName() != null && (objectType.getType() == null || BuiltinType.of(objectType.getType().getName()).isPresent());
        }
    }

    private class BuiltinVisitor extends TypesSwitch<Boolean> {
        @Override
        public Boolean defaultCase(EObject object) {
            return true;
        }

        @Override
        public Boolean caseArrayType(final ArrayType arrayType) {
            final AnyType items = arrayType.getItems();
            return items != null && items.getName() != null && BuiltinType.of(items.getName()).isPresent();
        }

        @Override
        public Boolean caseObjectType(final ObjectType objectType) {
            return objectType.getName() != null && BuiltinType.of(objectType.getName()).isPresent();
        }
    }

    private class IsClassVisitor extends TypesSwitch<Boolean> {
        @Override
        public Boolean defaultCase(EObject object) {
            return false;
        }

        @Override
        public Boolean caseArrayType(final ArrayType arrayType) {
            final AnyType items = arrayType.getItems();
            return items != null && items.getName() != null;
        }

        @Override
        public Boolean caseObjectType(final ObjectType objectType) {
            return objectType.getName() != null;
        }
    }
}

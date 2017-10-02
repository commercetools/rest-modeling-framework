package io.vrap.rmf.raml.generic.generator.php;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.vrap.rmf.raml.model.modules.Api;
import io.vrap.rmf.raml.model.types.*;
import io.vrap.rmf.raml.model.types.util.TypesSwitch;
import org.eclipse.emf.ecore.EObject;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MetaType {
    static final String TYPES = "Types";
    static final String BASE = "Base";

    private final AnyType type;

    public MetaType(final AnyType type) {
        this.type = type;
    }

    public Boolean getHasBuiltinParent()
    {
        return new BuiltinParentVisitor().doSwitch(this.type);
    }

    @Nullable
    public String getName()
    {
        return this.type.getName();
    }

    public MetaType getParent()
    {
        return new MetaType(this.type.getType());
    }

    @Nullable
    public String getDiscriminator()
    {
        if (this.type instanceof ObjectType) {
            return ((ObjectType) this.type).getDiscriminator();
        }
        return null;
    }

    @Nullable
    public String getDiscriminatorValue()
    {
        if (this.type instanceof ObjectType) {
            return ((ObjectType) this.type).getDiscriminatorValue();
        }
        return null;
    }

    public MetaImport getImport()
    {
        final String name = (new MetaHelper.TypeNameVisitor()).doSwitch(type);
        return new MetaImport(getPackage(), name);
    }

    @Nullable
    public List<MetaType> getSubTypes()
    {
        if (this.type.subTypes() != null) {
            return this.type.subTypes().stream().map(MetaType::new).collect(Collectors.toList());
        }
        return null;
    }

    public List<MetaProperty> getNonPatternProperties()
    {
        if (type instanceof ObjectType) {
            return ((ObjectType)type).getProperties().stream()
                    .filter(property -> !(property.getName().startsWith("/") && property.getName().endsWith("/")))
                    .map(MetaProperty::new)
                    .collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    public List<MetaProperty> getPatternProperties()
    {
        if (type instanceof ObjectType) {
            return ((ObjectType)type).getProperties().stream()
                    .filter(property -> property.getName().startsWith("/") && property.getName().endsWith("/"))
                    .map(MetaProperty::new)
                    .collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    public String getTypeName()
    {
        return new MetaHelper.TypeNameVisitor().doSwitch(type);
    }

    public Set<MetaImport> getTypeImports()
    {
        if (type instanceof ObjectType) {
            ObjectType objectType = (ObjectType)type;

            final Set<MetaImport> uses = objectType.getProperties().stream()
                    .filter(property -> property.getType() instanceof ObjectType || property.getType() instanceof ArrayType && ((ArrayType) property.getType()).getItems() instanceof ObjectType)
                    .filter(property -> {
                        AnyType t = property.getType() instanceof ArrayType ? ((ArrayType) property.getType()).getItems() : property.getType();
                        return !new MetaType(t).getPackage().getName().equals(getPackage().getName());
                    })
                    .map(property -> {
                        AnyType t = property.getType() instanceof ArrayType ? ((ArrayType) property.getType()).getItems() : property.getType();
                        return new MetaType(t).getImport();
                    })
                    .collect(Collectors.toSet());
            uses.addAll(
                    objectType.getProperties().stream()
                            .map(MetaHelper::getBaseProperty)
                            .filter(property -> property.getType() instanceof ObjectType || property.getType() instanceof ArrayType && ((ArrayType) property.getType()).getItems() instanceof ObjectType)
                            .filter(property -> {
                                AnyType t = property.getType() instanceof ArrayType ? ((ArrayType) property.getType()).getItems() : property.getType();
                                return !new MetaType(t).getPackage().getName().equals(getPackage().getName());
                            })
                            .map(property -> {
                                return new MetaType(property.getType()).getImport();
                            })
                            .collect(Collectors.toSet())
            );
            if (!getHasBuiltinParent() && !getParent().getPackage().getName().equals(getPackage().getName())) {
                uses.add(getParent().getImport());
            }
            if (getDiscriminator() != null && getPackage().getHasPackage()) {
                uses.add(new MetaImport(new MetaPackage(TYPES)));
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

    public MetaPackage getPackage()
    {
        final AnyType t = type instanceof ArrayType ? ((ArrayType) type).getItems() : type;
        Annotation annotation = t.getAnnotation(getApi().getAnnotationType("package"), true);
        return new MetaPackage(TYPES, annotation);
    }

    public Set<Map.Entry<String, String>> getTypeProperties()
    {
        final Map<String, String> typeProperties;
        if (type instanceof ObjectType) {
            ObjectType objectType = (ObjectType)type;

            if(objectType.getType() instanceof ObjectType) {
                ObjectType superType = (ObjectType)objectType.getType();
                typeProperties = objectType.getProperties().stream()
                        .filter(property -> superType.getProperty(property.getName()) == null)
                        .collect(Collectors.toMap(
                                property ->
                                        property.getName().startsWith("/") && property.getName().endsWith("/") ?
                                                "pattern" + property.hashCode() :
                                                property.getName(),
                                Property::getName
                        ));
            } else {
                typeProperties = Maps.newHashMap();
            }
        } else {
            typeProperties = Maps.newHashMap();
        }
        return typeProperties.entrySet();
    }

    public Api getApi()
    {
        return getRoot(type);
    }

    private Api getRoot(EObject o)
    {
        EObject parent = o.eContainer();
        return parent instanceof Api ? (Api)parent : getRoot(parent);
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

}

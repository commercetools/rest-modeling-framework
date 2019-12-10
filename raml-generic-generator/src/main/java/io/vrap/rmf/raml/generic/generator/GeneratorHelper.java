package io.vrap.rmf.raml.generic.generator;

import com.damnhandy.uri.template.Expression;
import com.damnhandy.uri.template.UriTemplate;
import com.google.common.collect.Lists;
import io.vrap.rmf.raml.generic.generator.php.ResourceGenModel;
import io.vrap.rmf.raml.model.resources.Method;
import io.vrap.rmf.raml.model.resources.Resource;
import io.vrap.rmf.raml.model.types.AnyType;
import io.vrap.rmf.raml.model.types.BuiltinType;
import io.vrap.rmf.raml.model.types.ObjectType;
import io.vrap.rmf.raml.model.types.Property;
import io.vrap.rmf.raml.model.types.util.TypesSwitch;
import io.vrap.rmf.raml.model.util.StringCaseFormat;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.ecore.EObject;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

public class GeneratorHelper {
    private static GeneratorHelper instance;

    public static void setInstance(GeneratorHelper instance) {
        GeneratorHelper.instance = instance;
    }

    public static Property getBaseProperty(final Property property) {
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

    public static String toParamName(final UriTemplate uri, final String delimiter) {
        return toParamName(uri, delimiter, "");
    }

    public static String toParamName(final UriTemplate uri, final String delimiter, final String suffix) {
        return StringUtils.capitalize(uri.getComponents().stream().map(
                uriTemplatePart -> {
                    if (uriTemplatePart instanceof Expression) {
                        return ((Expression)uriTemplatePart).getVarSpecs().stream()
                                .map(s -> delimiter + StringUtils.capitalize(s.getVariableName()) + suffix).collect(Collectors.joining());
                    }
                    return StringCaseFormat.UPPER_CAMEL_CASE.apply(uriTemplatePart.toString().replace("/", "-"));
                }
        ).collect(Collectors.joining())).replaceAll("[^\\p{L}\\p{Nd}]+", "");
    }

    public static String toRequestName(UriTemplate uri, Method method) {
        return toParamName(uri, "By") + StringUtils.capitalize(method.getMethod().toString());
    }

    @Nullable
    public static <T> T getParent(EObject object, Class<T> parentClass)
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

    public static UriTemplate absoluteUri(final Resource resource)
    {
        if (resource.eContainer() instanceof Resource) {
            final Resource parent = (Resource) resource.eContainer();
            return UriTemplate.fromTemplate(absoluteUri(parent).getTemplate() + resource.getRelativeUri().getTemplate());
        } else {
            return resource.getRelativeUri();
        }
    }

    public static List<ResourceGenModel> flattenResources(final List<Resource> resources)
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

    public static TypeNameVisitor getTypeNameVisitor()
    {
        return instance.typeNameVisitor();
    }

    public static ParamVisitor getParamVisitor(final Property property)
    {
        return instance.paramVisitor(property);
    }

    public static SerializerVisitor getSerializerVisitor(final PropertyGenModel propertyGenModel)
    {
        return instance.serializerVisitor(propertyGenModel);
    }

    public static PropertyGetterVisitor getPropertyGetterVisitor(final PropertyGenModel propertyGenModel)
    {
        return instance.propertyGetterVisitor(propertyGenModel);
    }

    public static PropertyMapperVisitor getPropertyMapperVisitor()
    {
        return instance.propertyMapperVisitor();
    }

    public static PropertySetterVisitor getPropertySetterVisitor(final PropertyGenModel propertyGenModel)
    {
        return instance.propertySetterVisitor(propertyGenModel);
    }

    protected TypeNameVisitor typeNameVisitor()
    {
        return new TypeNameVisitor();
    }

    protected ParamVisitor paramVisitor(final Property property)
    {
        return new ParamVisitor(property);
    }

    protected SerializerVisitor serializerVisitor(final PropertyGenModel propertyGenModel)
    {
        return new SerializerVisitor(propertyGenModel);
    }

    protected PropertyGetterVisitor propertyGetterVisitor(final PropertyGenModel propertyGenModel)
    {
        return new PropertyGetterVisitor(propertyGenModel);
    }

    protected PropertyMapperVisitor propertyMapperVisitor()
    {
        return new PropertyMapperVisitor();
    }

    protected PropertySetterVisitor propertySetterVisitor(final PropertyGenModel propertyGenModel)
    {
        return new PropertySetterVisitor(propertyGenModel);
    }

    protected static abstract class TypeVisitor extends TypesSwitch<String> {

        public String defaultCase(EObject object) {
            return null;
        }
    }

    public static class TypeNameVisitor extends TypeVisitor {
    }

    public static class ParamVisitor extends TypeVisitor {
        final Property property;

        public ParamVisitor(final Property property) {
            this.property = property;
        }
    }

    public static class SerializerVisitor extends TypesSwitch<SerializerGenModel> {
        final PropertyGenModel property;

        public SerializerVisitor(final PropertyGenModel property) {
            this.property = property;
        }
    }

    public static class PropertyGetterVisitor extends TypesSwitch<GetterGenModel> {
        final PropertyGenModel property;

        public PropertyGetterVisitor(final PropertyGenModel property) {
            this.property = property;
        }

        @Override
        public GetterGenModel defaultCase(EObject object) {
            return new GetterGenModel("defaultGetter", (AnyType)object, property);
        }
    }

    public static class PropertyMapperVisitor extends TypesSwitch<GetterGenModel> {
        @Override
        public GetterGenModel defaultCase(EObject object) {
            return new GetterGenModel("defaultGetter", (AnyType)object, null);
        }
    }

    public static class PropertySetterVisitor extends TypesSwitch<SetterGenModel> {
        final PropertyGenModel property;

        public PropertySetterVisitor(final PropertyGenModel property) {
            this.property = property;
        }

        @Override
        public SetterGenModel defaultCase(EObject object) {
            return new SetterGenModel("defaultSetter", property, null);
        }
    }
}

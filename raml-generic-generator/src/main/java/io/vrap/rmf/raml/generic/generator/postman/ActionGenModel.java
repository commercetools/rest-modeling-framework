package io.vrap.rmf.raml.generic.generator.postman;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.vrap.rmf.raml.model.resources.Method;
import io.vrap.rmf.raml.model.resources.Resource;
import io.vrap.rmf.raml.model.types.*;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ActionGenModel extends ItemGenModel{
    private final ObjectType type;
    private final String example;
    private final List<String> testScript;

    public ActionGenModel(final ObjectType type, final Resource resource, final String template, final Method method) {
        super(resource, template, method);
        this.type = type;
        String example = null;
        final ObjectMapper mapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addSerializer(ObjectInstance.class, new ObjectInstanceSerializer());
        module.addSerializer(ArrayInstance.class, new InstanceSerializer());
        module.addSerializer(IntegerInstance.class, new InstanceSerializer());
        module.addSerializer(BooleanInstance.class, new InstanceSerializer());
        module.addSerializer(StringInstance.class, new InstanceSerializer());
        module.addSerializer(NumberInstance.class, new InstanceSerializer());
        mapper.registerModule(module);

        if (type.getExamples().size() > 0) {
            final Instance instance = type.getExamples().get(0).getValue();
            if (instance instanceof StringInstance) {
                example = ((StringInstance)instance).getValue();
            }
            if (instance instanceof ObjectInstance) {
                try {
                    example = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(instance);
                } catch (JsonProcessingException e) {}
            }

            try {
                ObjectNode nodes = (ObjectNode)mapper.readTree(example);
                nodes.put("action", type.getDiscriminatorValue());

                example = Arrays.stream(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(nodes)
                        .split("\n")).map(s -> "  " + s)
                        .collect(Collectors.joining("\n"))
                        .trim();
            } catch (IOException e) {}
        }

        this.example = example;
        final Annotation t = type.getAnnotation("postman-test-script");
        if (t != null)  {
            this.testScript = Arrays.stream(((StringInstance) t.getValue()).getValue().split("\n")).collect(Collectors.toList());
        } else {
            this.testScript = null;
        }
    }

    public ObjectType getType() {
        return type;
    }

    public String getDiscriminatorValue() {
        return type.getDiscriminatorValue();
    }

    public List<String> getTestScript() {
        return testScript;
    }

    public String getExample() {
        return StringEscapeUtils.escapeJson(example);
    }

    @Override
    public String getDescription() {
        return StringEscapeUtils.escapeJson(type.getDescription().getValue());
    }

    private class InstanceSerializer extends StdSerializer<Instance> {
        public InstanceSerializer() {
            this(null);
        }

        public InstanceSerializer(Class<Instance> t) {
            super(t);
        }

        @Override
        public void serialize(Instance value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeObject(value.getValue());
        }
    }

    private class ObjectInstanceSerializer extends StdSerializer<ObjectInstance> {
        public ObjectInstanceSerializer() {
            this(null);
        }

        public ObjectInstanceSerializer(Class<ObjectInstance> t) {
            super(t);
        }

        @Override
        public void serialize(ObjectInstance value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            List<PropertyValue> properties = value.getValue();
            gen.writeStartObject();
            for(PropertyValue v : properties) {
                gen.writeObjectField(v.getName(), v.getValue());
            }
            gen.writeEndObject();
        }
    }
}

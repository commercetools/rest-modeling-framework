package io.vrap.rmf.raml.generic.generator.postman;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.vrap.rmf.raml.model.facets.Instance;
import io.vrap.rmf.raml.model.facets.StringInstance;
import io.vrap.rmf.raml.model.resources.Method;
import io.vrap.rmf.raml.model.resources.Resource;
import io.vrap.rmf.raml.model.types.ObjectType;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ActionGenModel extends ItemGenModel{
    private final ObjectType type;
    private final String example;

    public ActionGenModel(final ObjectType type, final Resource resource, final String template, final Method method) {
        super(resource, template, method);
        this.type = type;
        String example = null;

        if (type.getExample() != null && (type.getExample().getValue() instanceof StringInstance)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                ObjectNode nodes = (ObjectNode)mapper.readTree(((StringInstance)type.getExample().getValue()).getValue());
                nodes.put("action", type.getDiscriminatorValue());

                example = Arrays.stream(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(nodes)
                        .split("\n")).map(s -> "  " + s.replace("\"", "\\\""))
                        .collect(Collectors.joining("\\n"))
                        .trim();
            } catch (IOException e) {}
        }
        this.example = example;
    }

    public ObjectType getType() {
        return type;
    }

    public String getExample() {
        return example;
    }
}

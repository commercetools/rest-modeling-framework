package io.vrap.rmf.raml.generic.generator.postman;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.vrap.rmf.raml.model.resources.Method;
import io.vrap.rmf.raml.model.resources.Resource;
import io.vrap.rmf.raml.model.types.ObjectType;
import io.vrap.rmf.raml.model.values.StringInstance;
import org.apache.commons.lang3.StringEscapeUtils;

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

        if (type.getExamples().size() == 1 && (type.getExamples().get(0).getValue() instanceof StringInstance)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                ObjectNode nodes = (ObjectNode)mapper.readTree(((StringInstance)type.getExamples().get(0).getValue()).getValue());
                nodes.put("action", type.getDiscriminatorValue());

                example = Arrays.stream(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(nodes)
                        .split("\n")).map(s -> "  " + s)
                        .collect(Collectors.joining("\n"))
                        .trim();
            } catch (IOException e) {}
        }
        this.example = example;
    }

    public ObjectType getType() {
        return type;
    }

    public String getExample() {
        return StringEscapeUtils.escapeJson(example);
    }

    @Override
    public String getDescription() {
        return StringEscapeUtils.escapeJson(type.getDescription());
    }
}

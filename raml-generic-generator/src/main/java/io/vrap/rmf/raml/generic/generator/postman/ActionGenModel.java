package io.vrap.rmf.raml.generic.generator.postman;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.vrap.rmf.raml.generic.generator.Helper;
import io.vrap.rmf.raml.model.resources.Method;
import io.vrap.rmf.raml.model.resources.Resource;
import io.vrap.rmf.raml.model.types.Annotation;
import io.vrap.rmf.raml.model.types.Instance;
import io.vrap.rmf.raml.model.types.ObjectType;
import io.vrap.rmf.raml.model.types.StringInstance;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ActionGenModel extends ItemGenModel{
    private final ObjectType type;
    private final String example;
    private final List<String> testScript;

    public ActionGenModel(final ObjectType type, final Resource resource, final String template, final Method method) {
        super(resource, template, method);
        this.type = type;
        String example = null;
        Instance instance = null;

        if (type.getAnnotation("postman-example") != null){
            instance = type.getAnnotation("postman-example").getValue();
        } else if (type.getExamples().size() > 0) {
            instance = type.getExamples().get(0).getValue();
        }

        if (instance != null) {
            example = Helper.toJson(instance);
            try {
                ObjectMapper mapper = new ObjectMapper();
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
        String description = Optional.ofNullable(type.getDescription()).map(StringInstance::getValue).orElse(type.getName());
        return StringEscapeUtils.escapeJson(description);
    }
}

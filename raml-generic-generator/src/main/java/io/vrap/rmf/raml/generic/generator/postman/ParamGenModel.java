package io.vrap.rmf.raml.generic.generator.postman;

import com.hypertino.inflector.English;
import io.vrap.rmf.raml.model.resources.Resource;
import io.vrap.rmf.raml.model.types.Annotation;
import io.vrap.rmf.raml.model.types.QueryParameter;
import io.vrap.rmf.raml.model.values.StringInstance;

public class ParamGenModel {
    private final Resource resource;
    private final QueryParameter param;

    public ParamGenModel(final Resource resource, final QueryParameter param) {
        this.resource = resource;
        this.param = param;
    }

    public QueryParameter getParam() {
        return param;
    }

    public Boolean getDisabled() {
        return !param.getRequired();
    }

    public String getName() {
        return param.getName();
    }

    public String getDefault() {
        Annotation defaultValue =  param.getAnnotation("postman-default-value");
        if (defaultValue != null && defaultValue.getValue() instanceof StringInstance) {
            String value = ((String)defaultValue.getValue().getValue()).replace("{{", "").replace("}}", "");

            return "{{" + English.singular(resource.getResourcePathName()) + "-" + value + "}}";
        }

        return "";
    }
}

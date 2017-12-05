package io.vrap.rmf.raml.generic.generator.postman;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import io.vrap.rmf.raml.model.resources.HttpMethod;
import io.vrap.rmf.raml.model.resources.Method;
import io.vrap.rmf.raml.model.resources.Resource;
import io.vrap.rmf.raml.model.responses.Body;
import io.vrap.rmf.raml.model.types.*;
import io.vrap.rmf.raml.model.util.StringCaseFormat;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.List;
import java.util.Optional;

public class ResourceGenModel {
    private final Resource resource;

    public ResourceGenModel(final Resource resource) {
        this.resource = resource;
    }

    public String getName()
    {
        return StringCaseFormat.UPPER_CAMEL_CASE.apply(Optional.ofNullable(resource.getDisplayName()).orElse(resource.getResourcePathName()));
    }

    public String getDescription() throws JsonProcessingException {
        return StringEscapeUtils.escapeJson(resource.getDescription());
    }

    public Resource getResource() {
        return resource;
    }

    public List<ItemGenModel> getItems() {
        List<ItemGenModel> items = Lists.newArrayList();

        if (resource.getMethod(HttpMethod.GET) != null) {
            items.add(new ItemGenModel(resource, "query", resource.getMethod(HttpMethod.GET)));
        }
        if (resource.getMethod(HttpMethod.POST) != null) {
            items.add(new ItemGenModel(resource, "create", resource.getMethod(HttpMethod.POST)));
        }
        Resource byId = resource.getResources().stream().filter(resource1 -> resource1.getUriParameter("ID") != null).findFirst().orElse(null);
        Resource byKey = resource.getResources().stream().filter(resource1 -> resource1.getUriParameter("key") != null).findFirst().orElse(null);
        if (byId != null && byId.getMethod(HttpMethod.GET) != null) {
            items.add(new ItemGenModel(resource, "getByID", byId.getMethod(HttpMethod.GET)));
        }
        if (byKey != null && byKey.getMethod(HttpMethod.GET) != null) {
            items.add(new ItemGenModel(resource, "getByKey", byKey.getMethod(HttpMethod.GET)));
        }
        if (byId != null && byId.getMethod(HttpMethod.POST) != null) {
            items.add(new ItemGenModel(resource, "updateByID", byId.getMethod(HttpMethod.POST)));
        }
        if (byKey != null && byKey.getMethod(HttpMethod.POST) != null) {
            items.add(new ItemGenModel(resource, "updateByKey", byKey.getMethod(HttpMethod.POST)));
        }
        if (byId != null && byId.getMethod(HttpMethod.DELETE) != null) {
            items.add(new ItemGenModel(resource, "deleteByID", byId.getMethod(HttpMethod.DELETE)));
        }
        if (byKey != null && byKey.getMethod(HttpMethod.DELETE) != null) {
            items.add(new ItemGenModel(resource, "deleteByKey", byKey.getMethod(HttpMethod.DELETE)));
        }
        if (byId != null && byId.getMethod(HttpMethod.POST) != null) {
            Method method = byId.getMethod(HttpMethod.POST);
            Body body = method.getBody("application/json");
            if (body != null && body.getType() instanceof ObjectType) {
                Property actions = ((ObjectType)body.getType()).getProperty("actions");
                if (actions != null) {
                    ArrayType actionsType = (ArrayType)actions.getType();
                    List<AnyType> updateActions;
                    if (actionsType.getItems() instanceof UnionType) {
                        updateActions = ((UnionType)actionsType.getItems()).getOneOf().get(0).getSubTypes();
                    } else {
                        updateActions = actionsType.getItems().getSubTypes();
                    }
                    for (AnyType action: updateActions) {
                        items.add(new ActionGenModel((ObjectType)action, resource, "action", method));
                    }
                }
            }
        }

        return items;
    }
}

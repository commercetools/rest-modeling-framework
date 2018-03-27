package io.vrap.rmf.raml.generic.generator.php;

import com.google.common.collect.Lists;
import io.vrap.rmf.raml.generic.generator.ImportGenModel;
import io.vrap.rmf.raml.generic.generator.PackageGenModel;
import io.vrap.rmf.raml.generic.generator.TypeGenModel;
import io.vrap.rmf.raml.generic.generator.postman.ActionGenModel;
import io.vrap.rmf.raml.model.resources.HttpMethod;
import io.vrap.rmf.raml.model.resources.Method;
import io.vrap.rmf.raml.model.resources.Resource;
import io.vrap.rmf.raml.model.responses.Body;
import io.vrap.rmf.raml.model.types.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BuilderGenModel {
    static String BUILDER = "Builder";

    private final RequestGenModel request;
    private final TypeGenModel resourceType;
    private final List<TypeGenModel> updates;
    private final TypeGenModel updateType;
    private final TypeGenModel baseActionType;
    private final ResourceGenModel resource;

    public BuilderGenModel(ResourceGenModel resource) {
        this.resource = resource;
        Annotation annotation = resource.getResource().getAnnotation("updateable");
        resourceType =  new TypeGenModel(resource.getApi().getType(((StringInstance)annotation.getValue()).getValue()));
        updateType = resourceType.getUpdateType();

        request = !getHasId() ?
                resource.getMethods().stream().filter(requestGenModel -> requestGenModel.getMethod().getMethod() == HttpMethod.POST).findFirst().get() :
                resource.getResources().stream()
                    .map(ResourceGenModel::getResource)
                    .filter(resource1 -> resource1.getUriParameter("ID") != null)
                    .filter(resource1 -> resource1.getMethod(HttpMethod.POST) != null)
                    .map(resource1 -> new RequestGenModel(resource1.getMethod(HttpMethod.POST)))
                    .findFirst().get()
        ;

        updates = Lists.newArrayList();
        final Property actions = ((ObjectType)updateType.getType()).getProperty("actions");
        if (actions != null) {
            final ArrayType actionsType = (ArrayType)actions.getType();
            final List<AnyType> updateActions;
            if (actionsType.getItems() instanceof UnionType) {
                updateActions = ((UnionType)actionsType.getItems()).getOneOf().get(0).getSubTypes();
                baseActionType = new TypeGenModel(((UnionType)actionsType.getItems()).getOneOf().get(0));
            } else {
                updateActions = actionsType.getItems().getSubTypes();
                baseActionType = new TypeGenModel(actionsType.getItems());
            }
            updates.addAll(updateActions.stream().map(TypeGenModel::new).collect(Collectors.toList()));
            updates.sort(Comparator.comparing(TypeGenModel::getName, Comparator.naturalOrder()));
        } else {
            baseActionType = null;
        }
    }

    public PackageGenModel getPackage()
    {
        return new PackageGenModel(BUILDER);
    }

    public RequestGenModel getRequest() {
        return request;
    }

    public TypeGenModel getResourceType() {
        return resourceType;
    }

    public TypeGenModel getUpdateType() {
        return updateType;
    }

    public TypeGenModel getBaseActionType() {
        return baseActionType;
    }

    public List<TypeGenModel> getUpdates() {
        return updates;
    }

    public List<ImportGenModel> getUpdateImports() {
        return updates.stream().map(TypeGenModel::getImport).collect(Collectors.toList());
    }
    public List<ImportGenModel> getTypeImports() {
        List<ImportGenModel> imports = getUpdateImports();
        imports.add(resourceType.getImport());
        imports.add(updateType.getImport());
        if (request != null) {
            imports.add(request.getImport());
        }
        return imports;
    };

    public List<ImportGenModel> getBuilderImports() {
        List<ImportGenModel> imports = Lists.newArrayList();
        imports.add(resourceType.getImport());
        imports.add(new ImportGenModel(getPackage(), updateType.getName() + BUILDER));
        return imports;
    }

    public Boolean getHasId() {
        return resourceType.getType() instanceof ObjectType && ((ObjectType)resourceType.getType()).getProperty("id") != null;
    }

    public String getIdMethod() {
        return new ResourceGenModel(request.getResource(), resource.getAllResources()).getMethodName();
    }

}

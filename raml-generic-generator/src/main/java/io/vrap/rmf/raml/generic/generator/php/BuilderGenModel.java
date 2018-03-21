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

    private final TypeGenModel resourceType;
    private final List<TypeGenModel> updates;
    private final TypeGenModel updateType;
    private final TypeGenModel baseActionType;

    public BuilderGenModel(AnyType resourceType) {

        this.resourceType = new TypeGenModel(resourceType);
        updateType = this.resourceType.getUpdateType();

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
        } else {
            baseActionType = null;
        }
    }

    public PackageGenModel getPackage()
    {
        return new PackageGenModel(BUILDER);
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

    public List<ImportGenModel> getTypeImports() {
        List<ImportGenModel> imports = updates.stream().map(TypeGenModel::getImport).collect(Collectors.toList());
        imports.add(resourceType.getImport());
        imports.add(updateType.getImport());
        return imports;
    };
}

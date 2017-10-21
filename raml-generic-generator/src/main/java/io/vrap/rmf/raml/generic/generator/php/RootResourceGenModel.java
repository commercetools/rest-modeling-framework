package io.vrap.rmf.raml.generic.generator.php;

import javax.annotation.Nullable;
import java.util.List;

public class RootResourceGenModel {
    static final String REQUEST = "Request";

    private final List<ResourceGenModel> resources;

    public RootResourceGenModel(List<ResourceGenModel> resources) {
        this.resources = resources;
    }

    @Nullable
    public List<ResourceGenModel> getResources() {
        return resources;
    }

    public PackageGenModel getPackage()
    {
        return new PackageGenModel(REQUEST);
    }
}

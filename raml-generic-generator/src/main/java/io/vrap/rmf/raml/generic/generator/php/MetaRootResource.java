package io.vrap.rmf.raml.generic.generator.php;

import javax.annotation.Nullable;
import java.util.List;

public class MetaRootResource {
    static final String REQUEST = "Request";

    private final List<MetaResource> resources;

    public MetaRootResource(List<MetaResource> resources) {
        this.resources = resources;
    }

    @Nullable
    public List<MetaResource> getResources() {
        return resources;
    }

    public MetaPackage getPackage()
    {
        return new MetaPackage(REQUEST);
    }
}

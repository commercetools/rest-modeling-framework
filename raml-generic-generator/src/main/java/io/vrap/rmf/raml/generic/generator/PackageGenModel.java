package io.vrap.rmf.raml.generic.generator;

import com.google.common.collect.Lists;
import io.vrap.rmf.raml.model.types.Annotation;
import io.vrap.rmf.raml.model.values.StringInstance;
import java.util.List;

public class PackageGenModel {
    private final List<String> parts;

    public PackageGenModel(final String packageName) {
        this(packageName, null);
    }

    public PackageGenModel(final String packageName, final Annotation annotation) {
        parts = Lists.newArrayList();
        parts.add(packageName);

        final String subPackage;
        if (annotation != null && annotation.getValue() instanceof StringInstance) {
            subPackage = ((StringInstance)annotation.getValue()).getValue();
        } else {
            subPackage = null;
        }

        if (subPackage != null) {
            parts.add(subPackage);
        }
    }

    public List<String> getParts() {
        return parts;
    }

    public String getSubPackageFolder()
    {
        return parts.size() > 1 ? parts.get(parts.size() - 1) + "/" : "";
    }

    public Boolean getHasPackage()
    {
        return parts.size() > 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PackageGenModel that = (PackageGenModel) o;

        return parts.equals(that.parts);
    }
}

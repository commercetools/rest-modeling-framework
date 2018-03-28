package io.vrap.rmf.raml.generic.generator;

import com.google.common.collect.Lists;

import javax.annotation.Nullable;
import java.util.List;

public class ImportGenModel {
    private final List<String> parts;
    private final Boolean hasVendor;

    public ImportGenModel(final PackageGenModel packageGenModel) {
        this(packageGenModel, null);
    }

    public ImportGenModel(final PackageGenModel packageGenModel, final String name) {
        hasVendor = packageGenModel != null;
        parts = Lists.newArrayList();
        if (packageGenModel != null) {
            parts.addAll(packageGenModel.getParts());
        }
        parts.add(name);
    }

    public List<String> getParts() {
        return parts;
    }

    public String getName() {
        return parts.get(parts.size() - 1);
    }

    public Boolean getHasVendor() {
        return hasVendor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImportGenModel that = (ImportGenModel) o;

        return parts.equals(that.parts);
    }

    @Override
    public int hashCode() {
        return parts.hashCode();
    }
}

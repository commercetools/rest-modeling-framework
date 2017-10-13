package io.vrap.rmf.raml.generic.generator.php;

import javax.annotation.Nullable;

public class ImportGenModel {
    private final PackageGenModel packageGenModel;
    private final String name;


    public ImportGenModel(final PackageGenModel packageGenModel) {
        this(packageGenModel, null);
    }

    public ImportGenModel(final PackageGenModel packageGenModel, final String name) {
        this.packageGenModel = packageGenModel;
        this.name = name;
    }

    public PackageGenModel getPackage()
    {
        return packageGenModel;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImportGenModel that = (ImportGenModel) o;

        if (!packageGenModel.equals(that.packageGenModel)) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = packageGenModel.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}

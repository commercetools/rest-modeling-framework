package io.vrap.rmf.raml.generic.generator.php;

import io.vrap.rmf.raml.model.facets.StringInstance;
import io.vrap.rmf.raml.model.types.Annotation;

import javax.annotation.Nullable;

public class PackageGenModel {
    private final String packageName;
    private final Annotation annotation;

    public PackageGenModel(final String packageName) {
        this(packageName, null);
    }

    public PackageGenModel(final String packageName, final Annotation annotation) {
        this.packageName = packageName;
        this.annotation = annotation;
    }

    @Nullable
    public Annotation getAnnotation() {
        return annotation;
    }

    public String getName()
    {
        return packageName + (getHasPackage() ? "\\" + getSubPackage() : "");
    }

    public String getSubPackage()
    {
        if (getHasPackage()) {
            return ((StringInstance)annotation.getValue()).getValue();
        }
        return "";
    }

    public String getSubPackageFolder()
    {
        final String subpackage = getSubPackage();
        return subpackage + (subpackage.isEmpty() ? "" : "/");
    }

    public Boolean getHasPackage()
    {
        return annotation != null && annotation.getValue() instanceof StringInstance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PackageGenModel that = (PackageGenModel) o;

        if (!packageName.equals(that.packageName)) return false;
        return annotation != null ? annotation.equals(that.annotation) : that.annotation == null;
    }

    @Override
    public int hashCode() {
        int result = packageName.hashCode();
        result = 31 * result + (annotation != null ? annotation.hashCode() : 0);
        return result;
    }
}

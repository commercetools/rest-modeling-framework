package io.vrap.rmf.raml.generic.generator.php;

import io.vrap.rmf.raml.model.facets.StringInstance;
import io.vrap.rmf.raml.model.types.Annotation;

import javax.annotation.Nullable;

public class MetaPackage {
    private final String packageName;
    private final Annotation annotation;

    public MetaPackage(final String packageName) {
        this(packageName, null);
    }

    public MetaPackage(final String packageName, final Annotation annotation) {
        this.packageName = packageName;
        this.annotation = annotation;
    }

    @Nullable
    public Annotation getAnnotation() {
        return annotation;
    }

    public String getName()
    {
        if (getHasPackage()) {
            return packageName + "\\" + ((StringInstance)annotation.getValue()).getValue();
        }
        return packageName;
    }

    public Boolean getHasPackage()
    {
        return annotation != null && annotation.getValue() instanceof StringInstance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MetaPackage that = (MetaPackage) o;

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

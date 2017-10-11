package io.vrap.rmf.raml.generic.generator.php;

import javax.annotation.Nullable;

public class MetaImport {
    private final MetaPackage metaPackage;
    private final String name;


    public MetaImport(final MetaPackage metaPackage) {
        this(metaPackage, null);
    }

    public MetaImport(final MetaPackage metaPackage, final String name) {
        this.metaPackage = metaPackage;
        this.name = name;
    }

    public MetaPackage getPackage()
    {
        return metaPackage;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MetaImport that = (MetaImport) o;

        if (!metaPackage.equals(that.metaPackage)) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = metaPackage.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}

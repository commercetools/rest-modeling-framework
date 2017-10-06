package io.vrap.rmf.raml.generic.generator.php;

import javax.annotation.Nullable;

public class MetaSetter {
    final private String setter;
    final private MetaProperty property;
    final private String docType;
    final private String paramType;

    public MetaSetter(final String setter, final MetaProperty property, final String docType, final String paramType) {
        this.setter = setter;
        this.property = property;
        this.docType = docType;
        this.paramType = paramType;
    }

    public MetaSetter(final String setter, final MetaProperty property, final String docType) {
        this(setter, property, docType, null);
    }

    public String getSetter() {
        return setter;
    }

    public MetaProperty getProperty() {
        return property;
    }

    @Nullable
    public String getDocType() {
        return docType;
    }

    @Nullable
    public String getParamType() {
        return paramType;
    }

    public Boolean getParamCheck() {
        return !docType.equals(paramType);
    }
}

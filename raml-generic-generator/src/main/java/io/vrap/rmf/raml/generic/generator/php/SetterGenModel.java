package io.vrap.rmf.raml.generic.generator.php;

import javax.annotation.Nullable;

public class SetterGenModel {
    final private String setter;
    final private PropertyGenModel property;
    final private String docType;
    final private String paramType;

    public SetterGenModel(final String setter, final PropertyGenModel property, final String docType, final String paramType) {
        this.setter = setter;
        this.property = property;
        this.docType = docType;
        this.paramType = paramType;
    }

    public SetterGenModel(final String setter, final PropertyGenModel property, final String docType) {
        this(setter, property, docType, null);
    }

    public String getSetter() {
        return setter;
    }

    public PropertyGenModel getProperty() {
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

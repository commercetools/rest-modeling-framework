package io.vrap.rmf.raml.model.util;

import io.vrap.rmf.raml.model.types.PatternProperty;

public class ModelHelper {
    public static boolean testPattern(final PatternProperty property , final String value) {
        return property.getPattern().test(value);
    }
}

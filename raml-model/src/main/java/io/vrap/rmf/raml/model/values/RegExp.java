package io.vrap.rmf.raml.model.values;

public interface RegExp {
    class RegExpConfig {
        private boolean useJavaScriptRegExp = false;

        public void setUseJavaScriptRegExp(boolean useJavaScriptRegExp) {
            this.useJavaScriptRegExp = useJavaScriptRegExp;
        }
    }
    RegExpConfig config = new RegExpConfig();

    boolean test(final String value);

    String toString();

    static RegExp of(final String pattern) {
        if (!config.useJavaScriptRegExp) {
            return JavaRegExp.of(pattern);
        } else {
            return JsRegExp.of(pattern);
        }
    }
}


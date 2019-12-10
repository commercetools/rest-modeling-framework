package io.vrap.rmf.raml.model.values;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class JavaRegExp implements RegExp {
    private final Pattern compiledPattern;
    private final String pattern;

    private JavaRegExp(final String pattern) {
        this.pattern = pattern;
        try {
            if (!pattern.equals("")) {
                compiledPattern = Pattern.compile(pattern);
            } else {
                compiledPattern = null;
            }
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public boolean test(final String value) {
        if (compiledPattern == null) {
            return true;
        }
        final Matcher matcher = compiledPattern.matcher(value);
        return matcher.matches();
    }

    public String toString() {
        return pattern;
    }

    public static RegExp of(final String pattern) {
        return new JavaRegExp(pattern);
    }
}

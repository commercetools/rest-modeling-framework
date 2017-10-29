package io.vrap.rmf.raml.model.util;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.CharMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * This enum represents the different string case formats that RAML supports
 * in string template transformations.
 */
public enum StringCaseFormat implements Predicate<String>, Function<String, String> {
    LOWER_CAMEL_CASE(s -> s.matches("\\p{Lower}(\\p{Lower}|\\p{Upper})*"), CharMatcher.inRange('A', 'Z'), StringCaseFormat::lcFirst, StringCaseFormat::ucFirst),
    UPPER_CAMEL_CASE(s -> s.matches("\\p{Upper}(\\p{Lower}|\\p{Upper})*"), CharMatcher.inRange('a', 'z'), StringCaseFormat::ucFirst, StringCaseFormat::ucFirst),

    LOWER_HYPHEN_CASE(s -> s.matches("\\p{Lower}(-|\\p{Lower})+"), null, "-", String::toLowerCase),
    UPPER_HYPHEN_CASE(s -> s.matches("\\p{Upper}(-|\\p{Upper})+"), null, "-", String::toUpperCase),

    LOWER_UNDERSCORE_CASE(s -> s.matches("\\p{Lower}(_|\\p{Lower})+"), null, "_", String::toLowerCase),
    UPPER_UNDERSCORE_CASE(s -> s.matches("\\p{Upper}(_|\\p{Upper})+"), null, "_", String::toUpperCase)
    ;


    private final Predicate<String> test;
    private final CharMatcher wordSeparator;
    private final String delimiter;
    private final Function<String, String> firstWordTransform;
    private final Function<String, String> otherWordTransform;

    StringCaseFormat(final Predicate<String> test, final CharMatcher wordSeparator, final String delimiter, Function<String, String> firstWordTransform, Function<String, String> otherWordTransform) {
        this.test = test;
        this.wordSeparator = wordSeparator;
        this.delimiter = delimiter;
        this.firstWordTransform = firstWordTransform;
        this.otherWordTransform = otherWordTransform;
    }


    StringCaseFormat(final Predicate<String> test, final CharMatcher wordSeparator, final String delimiter, Function<String, String> wordTransform) {
        this(test, wordSeparator, delimiter, wordTransform, wordTransform);
    }


    StringCaseFormat(final Predicate<String> test, final CharMatcher wordSeparator, Function<String, String> firstWordTransform, Function<String, String> otherWordTransform) {
        this(test, wordSeparator, "", firstWordTransform, otherWordTransform);
    }

    /**
     * Tests if the given value matches this case format.
     * @param value the value to test
     * @return true iff. the given value matches this case format
     */
    @Override
    public boolean test(final String value) {
        return test.test(value);
    }

    /**
     * This method tries to detect the case format of the given value and
     * conerts it to this case format.
     *
     * @param value the value convert
     * @return the converted value
     */
    @Override
    public String apply(final String value) {
        final Optional<StringCaseFormat> sourceFormat = Stream.of(values())
                .filter(stringCaseFormat -> stringCaseFormat.test(value))
                .findFirst();
        return sourceFormat
                .map(source ->  render(source.compoundWords(value)))
                .orElse(value);
    }

    @VisibleForTesting
    String render(final List<String> compoundWords) {
        final StringBuffer buffer = new StringBuffer();
        Function<String, String> transform = firstWordTransform;
        String separator = "";
        for (final String word : compoundWords) {
            buffer.append(separator).append(transform.apply(word));
            separator = delimiter;
            transform = otherWordTransform;
        }
        return buffer.toString();
    }

    @VisibleForTesting
    List<String> compoundWords(final String value) {
        final List<String> parts;
        if (delimiter.isEmpty()) {
            parts = new ArrayList<>();
            int i = 0;
            int wordStart = 0;
            for (; i < value.length(); i++) {
                if (wordSeparator.matches(value.charAt(i)) && wordStart < i) {
                    parts.add(value.substring(wordStart, i));
                    wordStart = i;
                }
            }
            if (wordStart < i) {
                parts.add(value.substring(wordStart, i));
            }
        } else {
            parts = Arrays.asList(value.split(delimiter));
        }
        return parts;
    }

    private final static String lcFirst(final String value) {
        return value.isEmpty() ?
                "" :
                Character.toLowerCase(value.charAt(0)) +
                        (value.length() == 1 ? "" : value.substring(1).toLowerCase());
    }


    private final static String ucFirst(final String value) {
        return value.isEmpty() ?
                "" :
                Character.toUpperCase(value.charAt(0)) +
                        (value.length() == 1 ? "" : value.substring(1).toLowerCase());
    }
}

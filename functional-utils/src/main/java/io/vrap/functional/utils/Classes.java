package io.vrap.functional.utils;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class Classes {
    private Classes() {
    }

    /**
     * A function that return the given value cast to the given class if the value is an instance
     * of the givven class.
     *
     * @param clazz the class
     * @param value the value to cast
     * @param <T>   the type
     * @return the optional value cast to the given type
     */
    public static <T> Optional<T> as(final Class<T> clazz, final Object value) {
        return clazz.isInstance(value) ?
                Optional.of(clazz.cast(value)) :
                Optional.empty();
    }

    public static <T> Optional<T> asOptional(final Class<T> clazz, final Optional<?> value) {
        return value.filter(clazz::isInstance).map(clazz::cast);
    }


    public static <T> Stream<T> instancesOf(final Class<T> clazz, final Stream<? super T> stream) {
        return stream.filter(clazz::isInstance).map(clazz::cast);
    }

    public static <T> Stream<T> instancesOf(final Class<T> clazz, final Collection<? super T> collection) {
        return instancesOf(clazz, collection.stream());
    }
}

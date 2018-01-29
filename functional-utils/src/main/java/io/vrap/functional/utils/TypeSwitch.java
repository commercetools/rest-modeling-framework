package io.vrap.functional.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * This class dispatches calls to the {@link #apply(Object)} method based on
 * the class of the argument to a provided type specific function.
 * <p>
 * The function to apply to an argument of a class can be specified with the
 * {@link #on(Class, Function)} method.
 * <p>
 * The {@link #fallthrough(Function)} method can be used to specify a fallthrough function.
 *
 * @param <T>
 * @param <R>
 */
public class TypeSwitch<T, R> {
    private List<Switch<T, R>> aSwitches;

    public TypeSwitch() {
        aSwitches = new LinkedList<>();
    }

    private TypeSwitch(final List<Switch<T, R>> aSwitches) {
        this.aSwitches = aSwitches;
    }

    /**
     * applies this switch to the given argument.
     *
     * @param arg the argument
     * @return the result of applying the argument to this switch
     */
    public R apply(final T arg) {
        final Switch<T, R> aSwitch = aSwitches.stream()
                .filter(s -> s.test(arg))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Can't handle argument of type " + arg.getClass()));
        return aSwitch.apply(arg);
    }

    /**
     * Build a dispatcher for the given target class.
     *
     * @param onClass  the class to dispatch on
     * @param function the function to apply for an argument of the given class
     * @return a new type switch
     */
    public <S extends T> TypeSwitch<T, R> on(final Class<S> onClass, final Function<S, R> function) {
        final List<Switch<T, R>> aSwitches = new LinkedList<>(this.aSwitches);
        aSwitches.add(new Switch(onClass::isInstance, function));

        return new TypeSwitch<>(aSwitches);
    }

    /**
     * Specifies a fallback function that is applied when no other function is applicable.
     *
     * @param fallback the fallback function
     * @param <S>      the argument type
     * @return a new type switch
     */
    public <S extends T> TypeSwitch<T, R> fallthrough(final Function<S, R> fallback) {
        final List<Switch<T, R>> aSwitches = new LinkedList<>(this.aSwitches);
        aSwitches.add(new Switch((arg) -> true, fallback));

        return new TypeSwitch<>(aSwitches);
    }

    private static class Switch<T, R> implements Predicate<T>, Function<T, R> {
        private final Predicate<T> predicate;
        private final Function<T, R> function;

        Switch(final Predicate<T> predicate, final Function<T, R> function) {
            this.predicate = predicate;
            this.function = function;
        }

        @Override
        public boolean test(final T t) {
            return predicate.test(t);
        }

        @Override
        public R apply(final T t) {
            return function.apply(t);
        }
    }
}

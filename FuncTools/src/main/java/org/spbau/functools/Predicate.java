package org.spbau.functools;


public interface Predicate<A> extends Function1<A, Boolean> {

    Predicate<Object> ALWAYS_TRUE = a -> true;
    Predicate<Object> ALWAYS_FALSE = a -> false;

    default Predicate<A> or(Predicate<? super A> second) {
        return a -> apply(a) || second.apply(a);
    }

    default Predicate<A> and(Predicate<? super A> second) {
        return a -> apply(a) && second.apply(a);
    }

    default Predicate<A> not() {
        return a -> !apply(a);
    }

}

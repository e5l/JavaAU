package org.spbau.functools;


public interface Function2<A1, A2, R> {

    R apply(A1 a1, A2 a2);

    default <R1> Function2<A1, A2, R1> compose(Function1<? super R, R1> f) {
        return (a1, a2) -> f.apply(apply(a1, a2));
    }

    default Function1<A2, R> bind1(A1 a1) {
        return a2 -> apply(a1, a2);
    }

    default Function1<A1, R> bind2(A2 a2) {
        return a1 -> apply(a1, a2);
    }

    default Function1<A1, Function1<A2, R>> curry() {
        return a1 -> a2 -> apply(a1, a2);
    }

}

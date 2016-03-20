package org.spbau.functools;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Collections {

    static <A, R> List<R> map(Function1<? super A, R> f, Iterable<A> a) {
        ArrayList<R> result = new ArrayList<R>();

        for (A item : a) {
            result.add(f.apply(item));
        }

        return result;
    }

    static <A> List<A> filter(Predicate<? super A> f, Iterable<A> a) {
        ArrayList<A> result = new ArrayList<A>();

        for (A item : a) {
            if (f.apply(item)) {
                result.add(item);
            }
        }

        return result;
    }

    static <A> List<A> takeWhile(Predicate<? super A> f, Iterable<A> a) {
        ArrayList<A> result = new ArrayList<A>();

        for (A item : a) {
            if (!f.apply(item)) {
                return result;
            }

            result.add(item);
        }

        return result;
    }

    static <A> List<A> takeUnless(Predicate<? super A> f, Iterable<A> a) {
        return takeWhile(f.not(), a);
    }

    static <A, R> R foldr(Function2<?super A, R, R> f, R init, Iterable<A> a) {
        return foldr(f, init, a.iterator());
    }

    static <A, R> A foldl(Function2<A, ? super R, A> f, A init, Iterable<R> a) {
        A value = init;

        for (R item : a) {
            value = f.apply(value, item);
        }

        return value;
    }

    static private <A, R> R foldr(Function2<? super A, R, R> f, R init, Iterator<A> it) {
        return it.hasNext() ? f.apply(it.next(), foldr(f, init, it)) : init;
    }
}

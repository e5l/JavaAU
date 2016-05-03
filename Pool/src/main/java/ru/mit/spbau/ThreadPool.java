package ru.mit.spbau;

import java.util.function.Function;
import java.util.function.Supplier;

public interface ThreadPool {

    <T> LightFuture<T> add(Supplier<T> task);

    void shutdown() throws InterruptedException;

    <R, T> LightFuture<R> addFuture(Function<? super T, R> function, LightFuture<T> parrent);
}

package ru.mit.spbau;

import java.util.function.Function;

public interface LightFuture<T> {

    <R> LightFuture<R> thenApply(Function<? super T, R> function);

    T get() throws LightExecutionException, InterruptedException;

    boolean isReady();
}

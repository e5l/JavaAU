package ru.mit.spbau;

import java.util.function.Supplier;

public interface ThreadPool {

    <T> LightFuture<T> add(Supplier<T> task);

    void shutdown() throws InterruptedException;
}

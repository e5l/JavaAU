package ru.mit.spbau;

import java.util.function.Function;

public class LightFutureImpl<T> implements LightFuture<T> {

    private T result;
    private final ThreadPool threadPool;
    private LightExecutionException exception;

    public LightFutureImpl(ThreadPool threadPool) {
        this.threadPool = threadPool;
    }

    @Override
    public <R> LightFuture<R> thenApply(Function<? super T, R> function) {
        return threadPool.addFuture(function, this);
    }

    @Override
    public synchronized T get() throws LightExecutionException, InterruptedException {
        while (!isReady()) {
            wait();
        }

        if (exception != null) {
            throw exception;
        }

        return result;
    }

    public synchronized void setError(Exception e) {
        exception = new LightExecutionException(e);
        notify();
    }

    public synchronized void setResult(T result) {
        this.result = result;
        notify();
    }

    @Override
    public synchronized boolean isReady() {
        return (result != null || exception != null);
    }
}

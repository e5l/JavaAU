package ru.mit.spbau;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Function;

public class LightFutureImpl<T> implements LightFuture<T> {

    private final ThreadPoolImpl threadPool;
    private final Queue<Runnable> waiters = new LinkedList<>();

    private T result;
    private LightExecutionException exception;

    public LightFutureImpl(ThreadPoolImpl threadPool) {
        this.threadPool = threadPool;
    }

    @Override
    public synchronized <R> LightFuture<R> thenApply(Function<? super T, R> function) {
        final LightFutureImpl<R> result = new LightFutureImpl<>(threadPool);
        final Runnable task = () -> {
            try {
                final T value = get();
                result.setResult(function.apply(value));
            } catch (Exception e) {
                result.setError(e);
            }
        };

        if (!isReady()) {
            waiters.add(task);
        } else {
            threadPool.enqueue(task);
        }

        return result;
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

        runWaiters();
    }

    public synchronized void setResult(T result) {
        this.result = result;
        notify();

        runWaiters();
    }

    @Override
    public synchronized boolean isReady() {
        return (result != null || exception != null);
    }

    private void runWaiters() {
        synchronized (waiters) {
            waiters.forEach(threadPool::enqueue);
            waiters.clear();
        }
    }
}

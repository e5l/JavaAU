package ru.mit.spbau;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Function;
import java.util.function.Supplier;

public class ThreadPoolImpl implements ThreadPool {

    private final Queue<Runnable> tasks = new LinkedList<>();
    private final List<Thread> threads = new ArrayList<>();

    public ThreadPoolImpl(int threadCount) {
        for (int i = 0; i < threadCount; i++) {
            final Worker thread = new Worker(tasks);
            thread.start();
            threads.add(thread);
        }
    }

    @Override
    public <T> LightFuture<T> add(Supplier<T> task) {
        final LightFutureImpl<T> result = new LightFutureImpl<>(this);

        synchronized (tasks) {
            tasks.add(() -> {
                try {
                    result.setResult(task.get());
                } catch (Exception e) {
                    result.setError(e);
                }
            });

            tasks.notify();
        }

        return result;
    }

    @Override
    public <R, T> LightFuture<R> addFuture(Function<? super T, R> function, LightFuture<T> parrent) {
        final LightFutureImpl<R> result = new LightFutureImpl<>(this);

        synchronized (tasks) {
            tasks.add(() -> {
                try {
                    final T value = parrent.get();
                    result.setResult(function.apply(value));
                } catch (Exception e) {
                    result.setError(e);
                }
            });

            tasks.notify();
        }

        return result;
    }


    @Override
    public void shutdown() throws InterruptedException {
        for (Thread thread : threads) {
            thread.interrupt();
            thread.join();
        }
    }

}

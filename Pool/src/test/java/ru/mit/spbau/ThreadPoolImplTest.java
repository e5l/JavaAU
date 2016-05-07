package ru.mit.spbau;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Supplier;

import static org.junit.Assert.*;

public class ThreadPoolImplTest {
    @Test
    public void add() throws Exception {

        final Integer[] counter = {0};

        final Supplier<Integer> simple = () -> {
            synchronized (counter) {
                counter[0] += 1;
                return 1;
            }
        };

        final ThreadPool pool = new ThreadPoolImpl(2);

        final Queue<LightFuture<Integer>> results = new LinkedList<>();
        for (int i = 0; i < 100; ++i) {
            results.add(pool.add(simple));
        }

        int final_result = 0;
        while (!results.isEmpty()) {
            LightFuture<Integer> result = results.poll();
            final_result += result.get();
        }

        pool.shutdown();

        assertEquals(100, final_result);
        assertEquals(100, (int) counter[0]);
    }

    @Test
    public void checkThreadCount() throws InterruptedException {
        final ThreadPool pool = new ThreadPoolImpl(10);
        final Barrier barrier = new Barrier(10);

        final int[] result = {0};

        for (int i = 0; i < 9; i++) {
            pool.add(() -> {
                try {
                    barrier.await();
                } catch (InterruptedException ignored) {
                }

                result[0]++;
                return 0;
            });
        }

        barrier.await();
        pool.shutdown();

        assertEquals(9, result[0]);
    }

    @Test
    public void checkThenApply() throws LightExecutionException, InterruptedException {
        final ThreadPool pool = new ThreadPoolImpl(10);

        int result = pool
                .add(() -> 1)
                .thenApply((i) -> i + 1)
                .thenApply((i) -> i + 1)
                .get();

        pool.shutdown();

        assertEquals(3, result);
    }

    @Test
    public void checkThenApplyBottleneck() throws InterruptedException {
        final ThreadPool pool = new ThreadPoolImpl(2);
        final Integer[] i = {1};

        pool.add(() -> {
            while (true) {
                synchronized (i[0]) {
                    if (i[0] == 2) {
                        break;
                    }
                }
            }

            return 0;
        }).thenApply((a) -> true);

        pool.add(() -> {
            synchronized (i[0]) {
                i[0] = 2;
            }

            return 0;
        });

        pool.shutdown();
    }

    @Test(expected = LightExecutionException.class)
    public void checkExceptions() throws LightExecutionException, InterruptedException {
        final ThreadPool pool = new ThreadPoolImpl(10);

        pool.add(() -> {
            throw new IllegalStateException();
        }).get();

        pool.shutdown();
    }

    private final class Barrier {

        private final int parties;
        private int currentCount = 0;

        public Barrier(int parties) {
            this.parties = parties;
        }

        public synchronized void await() throws InterruptedException {
            currentCount += 1;

            if (currentCount == parties) {
                notifyAll();
            }

            while (currentCount < parties) {
                wait();
            }
        }
    }


}
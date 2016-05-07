package ru.mit.spbau;

import java.util.Queue;


class Worker extends Thread {
    private final Queue<Runnable> tasks;

    Worker(Queue<Runnable> tasks) {
        this.tasks = tasks;
    }

    public void run() {
        try {
            while (!isInterrupted()) {
                Runnable task;

                synchronized (tasks) {
                    while (tasks.isEmpty()) {
                        tasks.wait();
                    }

                    task = tasks.poll();
                }

                task.run();
            }
        } catch (InterruptedException e) {
        }
    }
}

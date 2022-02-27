package ru.job4j.concurrent;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {
    private final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(4);
    private final AtomicInteger count = new AtomicInteger(1);
    private final AtomicReference<Integer> value = new AtomicReference<>();

    private class Producer extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < queue.getLimit(); i++) {
                try {
                    queue.offer(count.getAndIncrement());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private class Consumer extends Thread {
        @Override
        public void run() {
            try {
                value.set(queue.poll());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Test
    public void test() throws InterruptedException {
        Thread producer = new Producer();
        Thread consumer = new Consumer();
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(queue.size(), is(3));
        assertThat(value.get(), is(1));
    }
}


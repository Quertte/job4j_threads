package ru.job4j.concurrent;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {
    @Test
    public void test() throws InterruptedException {
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(4);
        AtomicInteger count = new AtomicInteger(1);
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < queue.getLimit(); i++) {
                        queue.offer(count.getAndIncrement());
                    }
                }
        );
        AtomicReference<Integer> value = new AtomicReference<>();
        Thread consumer = new Thread(
                () -> {
                    value.set(queue.poll());
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        producer.join();
        assertThat(queue.size(), is(3));
        assertThat(value.get(), is(1));
    }


}


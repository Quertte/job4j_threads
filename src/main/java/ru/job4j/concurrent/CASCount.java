package ru.job4j.concurrent;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        if (count.get() == null) {
            throw new UnsupportedOperationException("Count is not impl");
        }
        int ref;
        int next;
        do {
            ref = count.get();
            next = ref++;
        } while (!count.compareAndSet(ref, next));
    }

    public int get() {
        if (count.get() == null) {
            throw new UnsupportedOperationException("Count is not impl");
        }
        return count.get();
    }
}

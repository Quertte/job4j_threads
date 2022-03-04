package ru.job4j.concurrent;

import org.junit.Test;

import java.util.concurrent.ForkJoinPool;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ParallelSearchIndexTest {
    @Test
    public void whenSearchIndex() {
        ForkJoinPool pool = new ForkJoinPool();
        Integer[] array = getArray(50);
        int expect = 10;
        int result = pool.invoke(new ParallelSearchIndex<>(
                array, 0, array.length - 1, 10));
        assertThat(result, is(expect));
    }

    @Test
    public void whenDoNotSearchIndex() {
        ForkJoinPool pool = new ForkJoinPool();
        Integer[] array = getArray(50);
        int expect = -1;
        int result = pool.invoke(new ParallelSearchIndex<>(
                array, 0, array.length - 1, 100));
        assertThat(result, is(expect));
    }

    private static Integer[] getArray(int capacity) {
        Integer[] array = new Integer[capacity];
        for (int i = 0; i < capacity; i++) {
            array[i] = i;
        }
        return array;
    }
}
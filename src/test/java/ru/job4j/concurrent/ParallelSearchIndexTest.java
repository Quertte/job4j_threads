package ru.job4j.concurrent;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ParallelSearchIndexTest {
    @Test
    public void whenSearchIndex() {
        Integer[] array = getArray(50);
        Integer element = 10;
        int expect = 10;
        int result = ParallelSearchIndex.returnIndex(array, element);
        assertThat(result, is(expect));
    }

    @Test
    public void whenDoNotSearchIndex() {
        Integer[] array = getArray(50);
        Integer element = 100;
        int expect = -1;
        int result = ParallelSearchIndex.returnIndex(array, element);
        assertThat(result, is(expect));
    }

    @Test
    public void whenSearchLastElement() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Integer element = array[array.length - 1];
        int expect = 8;
        int result = ParallelSearchIndex.returnIndex(array, element);
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
package ru.job4j.concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearchIndex<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int from;
    private final int to;
    private final T element;

    public ParallelSearchIndex(T[] array, int from, int to, T element) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.element = element;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return search();
        }
        int mid = (from + to) / 2;
        ParallelSearchIndex<T> leftIndex = new ParallelSearchIndex<>(
                array, from, mid, element);
        ParallelSearchIndex<T> rightIndex = new ParallelSearchIndex<>(
                array, mid + 1, to, element);
        leftIndex.fork();
        rightIndex.fork();
        int leftArrayIndex = leftIndex.join();
        int rightArrayIndex = rightIndex.join();
        return Math.max(leftArrayIndex, rightArrayIndex);
    }

    private int search() {
        int index = -1;
        for (int i = from; i <= to; i++) {
            if (element.equals(array[i])) {
                index = i;
                break;
            }
        }
        return index;
    }

    public static <T> int returnIndex(T[] array, T element) {
        ForkJoinPool pool = new ForkJoinPool();
        return pool.invoke(new ParallelSearchIndex<>(
                array, 0, array.length - 1, element));
    }
}

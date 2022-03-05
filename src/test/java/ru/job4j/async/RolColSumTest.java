package ru.job4j.async;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class RolColSumTest {

    @Test
    public void whenTest() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums[] sums = RolColSum.sum(matrix);
        assertThat(sums[0].getRowSum(), is(6));
        assertThat(sums[0].getColSum(), is(12));
        assertThat(sums[2].getRowSum(), is(24));
        assertThat(sums[2].getColSum(), is(18));
    }

    @Test
    public void whenTest1() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums[] sums = RolColSum.asyncSum(matrix);
        assertThat(sums[0].getRowSum(), is(6));
        assertThat(sums[0].getColSum(), is(12));
        assertThat(sums[2].getRowSum(), is(24));
        assertThat(sums[2].getColSum(), is(18));
    }
}
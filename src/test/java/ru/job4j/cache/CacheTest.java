package ru.job4j.cache;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CacheTest {
    @Test
    public void whenAdd() {
        Cache cache = new Cache();
        Base first = new Base(1, 0);
        Base second = new Base(2, 0);
        assertTrue(cache.add(first));
        assertTrue(cache.add(second));
    }

    @Test
    public void whenUpdate() {
        Cache cache = new Cache();
        Base first = new Base(1, 0);
        Base second = new Base(1, 0);
        first.setName("Java");
        second.setName("Python");
        cache.add(first);
        cache.update(second);
        assertThat(cache.getMemory().get(first.getId()).getVersion(), is(1));
        assertThat(cache.getMemory().get(first.getId()).getName(), is("Python"));
    }

    @Test(expected = OptimisticException.class)
    public void whenVersionNotEqual() {
        Cache cache = new Cache();
        Base first = new Base(1, 0);
        Base second = new Base(1, 1);
        first.setName("Java");
        second.setName("Python");
        cache.add(first);
        cache.update(second);
    }

    @Test
    public void whenDelete() {
        Cache cache = new Cache();
        Base first = new Base(1, 0);
        Base second = new Base(2, 1);
        first.setName("Java");
        second.setName("Python");
        cache.add(first);
        cache.add(second);
        cache.delete(second);
        assertThat(cache.getMemory().size(), is(1));
    }

}
package com.ninjaone.backendinterviewproject.infraestructure.rudimentarycache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class RudimentaryCacheTest {

    private RudimentaryCache cache;

    @BeforeEach
    public void beforeEach() {
        this.cache = new RudimentaryCache();
    }

    @Test
    void save() {
        String key = "key1";
        int value = 10;
        cache.save(key, value, String::valueOf);

        int valueFound = cache.get(key, Integer::valueOf).get();

        assertEquals(valueFound, 10);
    }

    @Test
    void update() {
        String key = "key1";
        int value = 10;
        cache.save(key, value, String::valueOf);
        cache.update(key, Integer::valueOf, v -> v + 10, Object::toString);

        int valueFound = cache.get(key, Integer::valueOf).get();

        assertEquals(valueFound, 20);
    }

    @Test
    void remove() {
        String key = "key1";
        int value = 10;
        cache.save(key, value, String::valueOf);
        cache.remove(key);

        assertThrows(NoSuchElementException.class, () -> cache.get(key, Integer::valueOf).get());
    }
}
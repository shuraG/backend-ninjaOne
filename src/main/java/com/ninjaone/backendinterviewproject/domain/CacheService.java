package com.ninjaone.backendinterviewproject.domain;

import java.math.BigDecimal;
import java.util.function.Function;

public interface CacheService {
    <V> V get(String a, Function<String, V> mapper);

    <V> void save(String key, V value, Function<V, String> mapper);

    void remove(String key);
}

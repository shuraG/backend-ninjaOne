package com.ninjaone.backendinterviewproject.domain;

import java.util.Optional;
import java.util.function.Function;

public interface CacheService {
    <V> Optional<V> get(String a, Function<String, V> mapper);

    <V> V save(String key, V value, Function<V, String> mapper);

    void remove(String key);

    <V> void update(String key, Function<String, V> business, Function<V, V> updateFunction, Function<V, String> raw);
}

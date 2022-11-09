package com.ninjaone.backendinterviewproject.infraestructure.rudimentarycache;

import com.ninjaone.backendinterviewproject.domain.CacheService;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class RudimentaryCache implements CacheService {

    Map<String, String> map = new HashMap();

    public <V> V get(String key, Function<String, V> mapper) {
        var value = map.get(key);
        return mapper.apply(value);
    }

    public <V> void save(String key, V value, Function<V, String> mapper) {
        map.put(key, mapper.apply(value));
    }

    public void remove(String key) {
        map.remove(key);
    }
}

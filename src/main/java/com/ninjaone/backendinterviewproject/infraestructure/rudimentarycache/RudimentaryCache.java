package com.ninjaone.backendinterviewproject.infraestructure.rudimentarycache;

import com.ninjaone.backendinterviewproject.domain.CacheService;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class RudimentaryCache implements CacheService {

    Map<String, String> map = new ConcurrentHashMap<>();

    public <V> Optional<V> get(String key, Function<String, V> mapper) {
        var value = map.get(key);
        if (value == null) return Optional.empty();
        return Optional.of(mapper.apply(value));
    }

    public <V> V save(String key, V value, Function<V, String> mapper) {
        var valueMapped = mapper.apply(value);
        map.put(key, valueMapped);
        return value;
    }

    public void remove(String key) {
        map.remove(key);
    }

    public <V> void update(String key, Function<String, V> business, Function<V, V> updateFunction, Function<V, String> raw) {
        map.computeIfPresent(key, (k, rawData) -> {
            var businessObject = business.apply(rawData);
            var updateObject = updateFunction.apply(businessObject);
            return raw.apply(updateObject);
        });
    }


}

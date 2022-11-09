package com.ninjaone.backendinterviewproject.domain.rmmservice;

import java.util.Optional;
import java.util.UUID;

public interface RMMServiceRepository {
    void save(RMMService s);

    void remove(UUID id);

    Optional<RMMService> get(UUID id);

    Optional<RMMService> getByName(String n);
}

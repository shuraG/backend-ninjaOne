package com.ninjaone.backendinterviewproject.domain.rmmservice;

import java.util.Optional;

public interface RMMServiceRepository {
    void save(RMMService s);

    RMMService getById(Long id);

    Optional<RMMService> getByName(String n);
}

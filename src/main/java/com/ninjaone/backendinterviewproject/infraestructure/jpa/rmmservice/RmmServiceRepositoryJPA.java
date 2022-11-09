package com.ninjaone.backendinterviewproject.infraestructure.jpa.rmmservice;

import com.ninjaone.backendinterviewproject.domain.rmmservice.RMMService;
import com.ninjaone.backendinterviewproject.domain.rmmservice.RMMServiceRepository;

import java.util.Optional;
import java.util.UUID;

public class RmmServiceRepositoryJPA implements RMMServiceRepository {
    private final RmmServiceCrudRepository repo;

    public RmmServiceRepositoryJPA(RmmServiceCrudRepository repo) {
        this.repo = repo;
    }

    @Override
    public void save(RMMService rmmService) {
        var entity = rmmService.getEntity();
        repo.save(entity);
    }

    @Override
    public void remove(UUID id) {
        repo.deleteById(id);
    }

    @Override
    public Optional<RMMService> get(UUID id) {
        return repo.findById(id).map(RmmServiceEntity::getRmmService);
    }

    @Override
    public Optional<RMMService> getByName(String n) {
        return repo.findByName(n).map(RmmServiceEntity::getRmmService);
    }
}

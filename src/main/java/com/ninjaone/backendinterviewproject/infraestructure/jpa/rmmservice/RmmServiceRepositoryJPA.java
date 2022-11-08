package com.ninjaone.backendinterviewproject.infraestructure.jpa.rmmservice;

import com.ninjaone.backendinterviewproject.domain.rmmservice.RMMService;
import com.ninjaone.backendinterviewproject.domain.rmmservice.RMMServiceRepository;

import java.util.Optional;

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
    public RMMService getById(Long id) {
        return null;
    }

    @Override
    public Optional<RMMService> getByName(String n) {
        return repo.findByName(n).map(s -> s.getRmmService());
    }
}

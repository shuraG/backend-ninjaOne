package com.ninjaone.backendinterviewproject.infraestructure.jpa.rmmservice;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RmmServiceCrudRepository extends CrudRepository<RmmServiceEntity, UUID> {
    Optional<RmmServiceEntity> findByName(String name);

}

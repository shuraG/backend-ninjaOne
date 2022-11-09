package com.ninjaone.backendinterviewproject.infraestructure.jpa.device;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DeviceCrudRepository extends CrudRepository<DeviceEntity, UUID> {
    List<DeviceEntity> findAllById(Iterable<UUID> ids);

}

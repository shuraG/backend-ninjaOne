package com.ninjaone.backendinterviewproject.infraestructure.jpa.device;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeviceCrudRepository extends CrudRepository<DeviceEntity, UUID> {
}

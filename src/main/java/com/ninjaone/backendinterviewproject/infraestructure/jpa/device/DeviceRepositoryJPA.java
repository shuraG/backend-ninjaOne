package com.ninjaone.backendinterviewproject.infraestructure.jpa.device;

import com.ninjaone.backendinterviewproject.domain.device.Device;
import com.ninjaone.backendinterviewproject.domain.device.DeviceRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DeviceRepositoryJPA implements DeviceRepository {

    private final DeviceCrudRepository repo;

    public DeviceRepositoryJPA(DeviceCrudRepository deviceRepository) {
        this.repo = deviceRepository;
    }

    @Override
    public void save(Device d) {
        this.repo.save(d.getEntity());
    }

    @Override
    public List<Device> getDevices(long customerId) {
        return null;
    }

    @Override
    public Optional<Device> get(UUID id) {
        return repo.findById(id).map(e -> e.getDevice());
    }


}


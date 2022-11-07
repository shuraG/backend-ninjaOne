package com.ninjaone.backendinterviewproject.infraestructure.jpa.device;

import com.ninjaone.backendinterviewproject.domain.device.Device;
import com.ninjaone.backendinterviewproject.domain.device.DeviceRepository;

import java.util.List;

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


}


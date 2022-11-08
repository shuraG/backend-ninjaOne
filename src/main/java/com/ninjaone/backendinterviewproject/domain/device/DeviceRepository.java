package com.ninjaone.backendinterviewproject.domain.device;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeviceRepository {

    void save(Device d);

    List<Device> getDevices(long customerId);

    Optional<Device> get(UUID id);
}

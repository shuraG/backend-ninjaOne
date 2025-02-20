package com.ninjaone.backendinterviewproject.domain.device;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

public interface DeviceRepository {

    void save(Device d);

    void remove(UUID id);

    Stream<Device> getDevices(Set<UUID> devicesId);

    Optional<Device> get(UUID id);

    Optional<Device> get(String systemName);
}

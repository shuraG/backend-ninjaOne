package com.ninjaone.backendinterviewproject.application;

import com.ninjaone.backendinterviewproject.domain.TypeDevice;
import com.ninjaone.backendinterviewproject.domain.device.Device;
import com.ninjaone.backendinterviewproject.domain.device.DeviceRepository;
import com.ninjaone.backendinterviewproject.domain.rmmservice.RMMServiceRepository;

import java.util.UUID;

public class DeviceApplication {

    public final DeviceRepository deviceRepository;

    public final RMMServiceRepository rmmServiceRepository;

    public DeviceApplication(DeviceRepository deviceRepository, RMMServiceRepository rmmServiceRepository) {
        this.deviceRepository = deviceRepository;
        this.rmmServiceRepository = rmmServiceRepository;
    }

    public UUID createDevice(String systemName, TypeDevice typeDevice) {
        var deviceId = UUID.randomUUID();
        var device = new Device(deviceId, systemName, typeDevice);
        deviceRepository.save(device);
        return deviceId;
    }

    public void removeDevice(UUID deviceId) {
        deviceRepository.remove(deviceId);
    }

    public void addSubscription(UUID deviceId, UUID serviceId) {
        var device = deviceRepository.get(deviceId).orElseThrow(NotFoundException::new);
        var service = rmmServiceRepository.get(serviceId).orElseThrow(NotFoundException::new);
        device.addSubscription(service);
        deviceRepository.save(device);
    }

}

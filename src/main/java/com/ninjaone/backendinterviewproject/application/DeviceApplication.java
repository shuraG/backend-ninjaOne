package com.ninjaone.backendinterviewproject.application;

import com.ninjaone.backendinterviewproject.domain.TypeDevice;
import com.ninjaone.backendinterviewproject.domain.device.Device;
import com.ninjaone.backendinterviewproject.domain.device.DeviceRepository;

import java.util.UUID;

public class DeviceApplication {

    public final DeviceRepository deviceRepository;

    public DeviceApplication(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public UUID createDevice(String systemName, TypeDevice typeDevice) {
        var deviceId = UUID.randomUUID();
        var device = new Device(deviceId, systemName, typeDevice);
        deviceRepository.save(device);
        return deviceId;
    }

}

package com.ninjaone.backendinterviewproject.application;

import com.ninjaone.backendinterviewproject.domain.CacheService;
import com.ninjaone.backendinterviewproject.domain.TypeDevice;
import com.ninjaone.backendinterviewproject.domain.device.Device;
import com.ninjaone.backendinterviewproject.domain.device.DeviceRepository;
import com.ninjaone.backendinterviewproject.domain.rmmservice.RMMServiceRepository;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public class DeviceApplication {

    public final DeviceRepository deviceRepository;

    public final RMMServiceRepository rmmServiceRepository;

    public final CacheService cacheService;

    public DeviceApplication(DeviceRepository deviceRepository, RMMServiceRepository rmmServiceRepository, CacheService cacheService) {
        this.deviceRepository = deviceRepository;
        this.rmmServiceRepository = rmmServiceRepository;
        this.cacheService = cacheService;
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

    public void removeSubscription(UUID deviceId, UUID serviceId) {
        var device = deviceRepository.get(deviceId).orElseThrow(NotFoundException::new);
        var service = rmmServiceRepository.get(serviceId).orElseThrow(NotFoundException::new);
        device.unsubscribe(service);
        deviceRepository.save(device);
    }

    public BigDecimal calculateTotal(Set<UUID> devicesId) {
        var a = cacheService.get(1, s -> new BigDecimal(s));

        return deviceRepository.getDevices(devicesId)
                .map(Device::costSubscriptions)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}

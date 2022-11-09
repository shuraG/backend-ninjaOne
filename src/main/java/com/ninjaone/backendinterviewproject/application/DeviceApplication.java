package com.ninjaone.backendinterviewproject.application;

import com.ninjaone.backendinterviewproject.domain.CacheService;
import com.ninjaone.backendinterviewproject.domain.TypeDevice;
import com.ninjaone.backendinterviewproject.domain.device.Device;
import com.ninjaone.backendinterviewproject.domain.device.DeviceRepository;
import com.ninjaone.backendinterviewproject.domain.extracost.ExtraCost;
import com.ninjaone.backendinterviewproject.domain.extracost.ExtraCostRepository;
import com.ninjaone.backendinterviewproject.domain.extracost.TypeExtraCost;
import com.ninjaone.backendinterviewproject.domain.rmmservice.RMMServiceRepository;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;

public class DeviceApplication {

    public final DeviceRepository deviceRepository;

    public final RMMServiceRepository rmmServiceRepository;

    public final ExtraCostRepository extraCostRepository;

    public final CacheService cache;

    public DeviceApplication(DeviceRepository deviceRepository, RMMServiceRepository rmmServiceRepository, ExtraCostRepository extraCostRepository, CacheService cacheService) {
        this.deviceRepository = deviceRepository;
        this.rmmServiceRepository = rmmServiceRepository;
        this.extraCostRepository = extraCostRepository;
        this.cache = cacheService;
    }

    public UUID createDevice(String systemName, TypeDevice typeDevice) {
        validateDeviceDuplicated(systemName);
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
        upCache(deviceId, service.getPrice(device.getType()), BigDecimal::add);
    }

    public void removeSubscription(UUID deviceId, UUID serviceId) {
        var device = deviceRepository.get(deviceId).orElseThrow(NotFoundException::new);
        var service = rmmServiceRepository.get(serviceId).orElseThrow(NotFoundException::new);
        device.unsubscribe(service);
        deviceRepository.save(device);
        upCache(deviceId, service.getPrice(device.getType()), BigDecimal::subtract);
    }

    public BigDecimal calculateTotal(Set<UUID> devicesId) {
        var extraCost = extraCostRepository.findByType(TypeExtraCost.BASE_COST_OBLIGATORY)
                .map(ExtraCost::getCost).get();

        return devicesId.stream().map(deviceId ->
                cache.get(deviceId.toString(), BigDecimal::new)
                        .orElseGet(() -> deviceRepository.get(deviceId)
                                .map(device -> device.costSubscriptions().add(extraCost))
                                .map(cost -> cache.save(deviceId.toString(), cost, BigDecimal::toString))
                                .orElseThrow(NotFoundException::new))
        ).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void validateDeviceDuplicated(String systemName) {
        if (deviceRepository.get(systemName).isPresent()) {
            throw new DuplicateException();
        }
    }

    private void upCache(UUID id, BigDecimal price, BiFunction<BigDecimal, BigDecimal, BigDecimal> op) {
        cache.update(id.toString(), BigDecimal::new, p -> op.apply(p, price), BigDecimal::toString);
    }

}

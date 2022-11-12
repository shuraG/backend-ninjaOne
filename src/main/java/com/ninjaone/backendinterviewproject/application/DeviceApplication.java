package com.ninjaone.backendinterviewproject.application;

import com.ninjaone.backendinterviewproject.domain.CacheService;
import com.ninjaone.backendinterviewproject.domain.DuplicateException;
import com.ninjaone.backendinterviewproject.domain.device.Device;
import com.ninjaone.backendinterviewproject.domain.device.DeviceNotFoundException;
import com.ninjaone.backendinterviewproject.domain.device.DeviceRepository;
import com.ninjaone.backendinterviewproject.domain.device.TypeDevice;
import com.ninjaone.backendinterviewproject.domain.extracost.ExtraCost;
import com.ninjaone.backendinterviewproject.domain.extracost.ExtraCostRepository;
import com.ninjaone.backendinterviewproject.domain.extracost.TypeExtraCost;
import com.ninjaone.backendinterviewproject.domain.rmmservice.RMMServiceRepository;
import com.ninjaone.backendinterviewproject.domain.rmmservice.RmmServiceNotFoundException;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

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
        var device = deviceRepository.get(deviceId).orElseThrow(() -> new DeviceNotFoundException(deviceId));
        var service = rmmServiceRepository.get(serviceId).orElseThrow(() -> new RmmServiceNotFoundException(serviceId));
        device.subscribe(service);
        deviceRepository.save(device);
        upCache(deviceId, service.getPrice(device.getType()), BigDecimal::add);
    }

    public void removeSubscription(UUID deviceId, UUID serviceId) {
        var device = deviceRepository.get(deviceId).orElseThrow(() -> new DeviceNotFoundException(deviceId));
        var service = rmmServiceRepository.get(serviceId).orElseThrow(() -> new RmmServiceNotFoundException(serviceId));
        device.unsubscribe(service);
        deviceRepository.save(device);
        upCache(deviceId, service.getPrice(device.getType()), BigDecimal::subtract);
    }

    public BigDecimal calculateTotal(Set<UUID> devicesId) {
        var extraCost = extraCostRepository.findByType(TypeExtraCost.BASE_COST_OBLIGATORY)
                .map(ExtraCost::getCost).get();

        Function<UUID, BigDecimal> calculateTotalCostDevice = (UUID deviceId) -> deviceRepository
                .get(deviceId)
                .map(device -> device.hasSubscriptions() ? device.costSubscriptions().add(extraCost) : BigDecimal.ZERO)
                .orElseThrow(() -> new DeviceNotFoundException(deviceId));

        return devicesId.stream()
                .map(deviceId -> getCostCached(deviceId, calculateTotalCostDevice))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void validateDeviceDuplicated(String systemName) {
        var nameValidated = systemName.toUpperCase(Locale.ROOT);
        if (deviceRepository.get(nameValidated).isPresent()) {
            throw new DuplicateException("Device", "SystemName", nameValidated);
        }
    }

    private BigDecimal getCostCached(UUID id, Function<UUID, BigDecimal> getCostIfNotCached) {
        return cache.get(id.toString(), BigDecimal::new)
                .orElseGet(() -> {
                    var cost = getCostIfNotCached.apply(id);
                    cache.save(id.toString(), cost, BigDecimal::toString);
                    return cost;
                });
    }

    private void upCache(UUID id, BigDecimal price, BiFunction<BigDecimal, BigDecimal, BigDecimal> op) {
        cache.update(id.toString(), BigDecimal::new, p -> op.apply(p, price), BigDecimal::toString);
    }

}

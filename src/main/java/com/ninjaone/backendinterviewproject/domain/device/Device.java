package com.ninjaone.backendinterviewproject.domain.device;

import com.ninjaone.backendinterviewproject.domain.BusinessException;
import com.ninjaone.backendinterviewproject.domain.rmmservice.RMMService;
import com.ninjaone.backendinterviewproject.infraestructure.jpa.device.DeviceEntity;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class Device {
    private UUID id;
    private String systemName;
    private TypeDevice type;
    private List<RMMService> subscriptions;

    public Device(UUID id, String systemName, TypeDevice type) {
        validateSystemName(systemName);
        this.id = id;
        this.systemName = systemName.toUpperCase(Locale.ROOT);
        this.type = type;
        this.subscriptions = new LinkedList<>();
    }

    public void subscribe(RMMService service) {
        if (!service.isAvailableForTypeDevice(type)) {
            throw new NotAvailableRMMServiceForDevice();
        }
        if (hasRMMService(service)) {
            throw new BusinessException("Service already added!");
        }
        subscriptions.add(service);
    }

    public void unsubscribe(RMMService service) {
        if (!hasRMMService(service)) {
            throw new BusinessException("Device does not subscribed to service!");
        }
        subscriptions.remove(service);
    }

    public BigDecimal costSubscriptions() {
        return subscriptions.stream()
                .map(s -> s.getPrice(type))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean hasSubscriptions() {
        return !subscriptions.isEmpty();
    }

    private boolean hasRMMService(RMMService service) {
        return subscriptions.stream().anyMatch(s -> s.equals(service));
    }

    private void validateSystemName(String systemName) {
        if (systemName.isBlank()) {
            throw new BusinessException("Device needs a system name!");
        }
    }

    public TypeDevice getType() {
        return this.type;
    }

    public DeviceEntity getEntity() {
        return new DeviceEntity(id, systemName, type, subscriptions);
    }
}

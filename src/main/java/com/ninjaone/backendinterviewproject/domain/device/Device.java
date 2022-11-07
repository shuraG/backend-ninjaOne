package com.ninjaone.backendinterviewproject.domain.device;

import com.ninjaone.backendinterviewproject.domain.TypeDevice;
import com.ninjaone.backendinterviewproject.infraestructure.jpa.device.DeviceEntity;
import com.ninjaone.backendinterviewproject.domain.rmmservice.RMMService;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Device {
    private UUID id;
    private String systemName;
    private TypeDevice type;
    private List<RMMService> services;

    public Device(UUID id, String systemName, TypeDevice type) {
        this.id = id;
        this.systemName = systemName;
        this.type = type;
        this.services = new LinkedList<>();
    }

    public void addSubscription(RMMService service) {
        if (!service.isAvailableForTypeDevice(type)) {
            throw new NotAvailableRMMServiceForDevice();
        }
        if (hasRMMService(service)) {
            throw new RuntimeException("Service already added!");
        }
        services.add(service);
    }

    public BigDecimal costServices() {
        return services.stream()
                .map(s -> s.getPrice(type))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private boolean hasRMMService(RMMService service) {
        return services.stream().anyMatch(s -> s.equals(service));
    }

    public DeviceEntity getEntity() {
        return new DeviceEntity(id, systemName, type);
    }
}

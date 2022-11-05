package com.ninjaone.backendinterviewproject.domain.device;

import com.ninjaone.backendinterviewproject.domain.TypeDevice;
import com.ninjaone.backendinterviewproject.domain.rmmservice.RMMService;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class Device {
    private Long id;
    private String systemName;
    private TypeDevice type;
    private Set<RMMService> services;

    public Device(String systemName, TypeDevice type, RMMService baseService) {
        this.systemName = systemName;
        this.type = type;
        this.services = new HashSet<>();
        addService(baseService);
    }

    public BigDecimal costServices() {
        return services.stream()
                .map(s -> s.getPrice(type))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addService(RMMService service) {
        if (!service.isAvailableForTypeDevice(type)) {
            throw new NotAvailableRMMServiceForDevice();
        }
        services.add(service);
    }
}

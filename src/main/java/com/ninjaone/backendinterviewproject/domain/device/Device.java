package com.ninjaone.backendinterviewproject.domain.device;

import com.ninjaone.backendinterviewproject.domain.TypeDevice;
import com.ninjaone.backendinterviewproject.domain.rmmservice.RMMService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Device {
    private Long id;
    private String systemName;
    private TypeDevice type;
    private List<RMMService> services;

    public Device(String systemName, TypeDevice type, RMMService baseService) {
        this.systemName = systemName;
        this.type = type;
        this.services = new ArrayList<>();
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

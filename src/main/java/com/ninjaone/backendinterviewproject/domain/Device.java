package com.ninjaone.backendinterviewproject.domain;

import com.ninjaone.backendinterviewproject.domain.rmmservice.NotAvailableRMMServiceForDevice;
import com.ninjaone.backendinterviewproject.domain.rmmservice.PriceRMMService;
import com.ninjaone.backendinterviewproject.domain.rmmservice.RMMService;

import java.math.BigDecimal;
import java.util.List;

public class Device {
    private Long id;
    private String systemName;
    private TypeDevice type;
    private List<PriceRMMService> priceRMMServices;

    public Device(String systemName, TypeDevice type, RMMService baseService) {
        this.systemName = systemName;
        this.type = type;
        addService(baseService);
    }

    public BigDecimal costServices() {
        return priceRMMServices.stream()
                .map(s -> s.getCost())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addService(RMMService service) {
        if (!service.isAvailableForTypeDevice(type)) {
            throw new NotAvailableRMMServiceForDevice();
        }
        priceRMMServices.add(service.getPrice(type));
    }
}

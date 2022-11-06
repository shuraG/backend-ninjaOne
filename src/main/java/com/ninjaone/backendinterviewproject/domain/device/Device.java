package com.ninjaone.backendinterviewproject.domain.device;

import com.ninjaone.backendinterviewproject.domain.TypeDevice;
import com.ninjaone.backendinterviewproject.domain.rmmservice.RMMService;

import java.math.BigDecimal;

public class Device {
    private Long id;
    private String systemName;
    private TypeDevice type;

    public Device(String systemName, TypeDevice type) {
        this.systemName = systemName;
        this.type = type;
    }

    public void addSubscription(SubscriptionService subService, RMMService service) {
        if (!service.isAvailableForTypeDevice(type)) {
            throw new NotAvailableRMMServiceForDevice();
        }
        if (subService.hasRMMService(this, service)) {
            throw new RuntimeException("Service already added!");
        }
        subService.add(this, service);
    }

    public BigDecimal costServices(SubscriptionService subService, BaseCostService baseCostService) {
        return subService.getServices(this)
                .stream()
                .map(s -> s.getPrice(type))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(baseCostService.get(this));
    }

}

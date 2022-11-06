package com.ninjaone.backendinterviewproject.domain.device;

import com.ninjaone.backendinterviewproject.domain.rmmservice.RMMService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SubscriptionStub implements SubscriptionService {
    private List<RMMService> services = new ArrayList<>();

    @Override
    public void add(Device device, RMMService service) {
        services.add(service);
    }

    @Override
    public List<RMMService> getServices(Device device) {
        return services;
    }

    @Override
    public boolean hasRMMService(Device device, RMMService service) {
        return services.stream().map(s -> s.getId())
                .collect(Collectors.toList())
                .contains(service.getId());
    }
}

package com.ninjaone.backendinterviewproject.domain.device;

import com.ninjaone.backendinterviewproject.domain.rmmservice.RMMService;

import java.util.List;

public interface SubscriptionService {

    void add(Device device, RMMService service);

    List<RMMService> getServices(Device device);

    boolean hasRMMService(Device device, RMMService service);
}

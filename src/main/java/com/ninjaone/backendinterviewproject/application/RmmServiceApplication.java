package com.ninjaone.backendinterviewproject.application;

import com.ninjaone.backendinterviewproject.domain.DuplicateException;
import com.ninjaone.backendinterviewproject.domain.device.TypeDevice;
import com.ninjaone.backendinterviewproject.domain.rmmservice.RMMService;
import com.ninjaone.backendinterviewproject.domain.rmmservice.RMMServiceRepository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public class RmmServiceApplication {
    private final RMMServiceRepository rmmServiceRepository;

    public RmmServiceApplication(RMMServiceRepository rmmServiceRepository) {
        this.rmmServiceRepository = rmmServiceRepository;
    }

    public UUID createRmmService(String name, BigDecimal price) {
        validateRmmServiceDuplicated(name);
        var rmmServiceId = UUID.randomUUID();
        var rmmService = new RMMService(rmmServiceId, name, price);
        rmmServiceRepository.save(rmmService);
        return rmmServiceId;
    }

    public UUID createRmmService(String name, Map<TypeDevice, BigDecimal> prices) {
        validateRmmServiceDuplicated(name);
        var rmmServiceId = UUID.randomUUID();
        var rmmService = new RMMService(rmmServiceId, name, prices);
        rmmServiceRepository.save(rmmService);
        return rmmServiceId;
    }

    public void removeRmmService(UUID deviceId) {
        rmmServiceRepository.remove(deviceId);
    }

    private void validateRmmServiceDuplicated(String name) {
        if (rmmServiceRepository.getByName(name).isPresent()) {
            throw new DuplicateException("RmmService", "name", name);
        }
    }
}

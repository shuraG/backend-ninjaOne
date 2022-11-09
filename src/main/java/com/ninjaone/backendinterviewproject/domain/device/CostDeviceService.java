package com.ninjaone.backendinterviewproject.domain.device;

import com.ninjaone.backendinterviewproject.domain.CacheService;

public class CostDeviceService {

    public final DeviceRepository deviceRepository;
    public final CacheService cacheService;

    public CostDeviceService(DeviceRepository deviceRepository, CacheService cacheService) {
        this.deviceRepository = deviceRepository;
        this.cacheService = cacheService;
    }


}

package com.ninjaone.backendinterviewproject.web.configuration;

import com.ninjaone.backendinterviewproject.application.DeviceApplication;
import com.ninjaone.backendinterviewproject.application.RmmServiceApplication;
import com.ninjaone.backendinterviewproject.domain.CacheService;
import com.ninjaone.backendinterviewproject.domain.device.DeviceRepository;
import com.ninjaone.backendinterviewproject.domain.rmmservice.RMMServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Application {

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    RMMServiceRepository rmmServiceRepository;

    @Autowired
    CacheService cacheService;

    @Bean
    public DeviceApplication getApplicationDevice() {
        return new DeviceApplication(deviceRepository, rmmServiceRepository, cacheService);
    }

    @Bean
    public RmmServiceApplication getApplicationRmmService() {
        return new RmmServiceApplication(rmmServiceRepository);
    }
}

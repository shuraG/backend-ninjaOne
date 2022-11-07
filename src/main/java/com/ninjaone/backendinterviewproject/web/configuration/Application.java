package com.ninjaone.backendinterviewproject.web.configuration;

import com.ninjaone.backendinterviewproject.application.DeviceApplication;
import com.ninjaone.backendinterviewproject.domain.device.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Application {

    @Autowired
    DeviceRepository deviceRepository;

    @Bean
    public DeviceApplication getApplicationDevice() {
        return new DeviceApplication(deviceRepository);
    }
}

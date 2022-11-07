package com.ninjaone.backendinterviewproject.web.configuration;

import com.ninjaone.backendinterviewproject.domain.device.DeviceRepository;
import com.ninjaone.backendinterviewproject.infraestructure.jpa.device.DeviceCrudRepository;
import com.ninjaone.backendinterviewproject.infraestructure.jpa.device.DeviceRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Repository {

    @Autowired
    DeviceCrudRepository crud;

    @Bean
    public DeviceRepository getDeviceRepository() {
        return new DeviceRepositoryJPA(crud);
    }
}

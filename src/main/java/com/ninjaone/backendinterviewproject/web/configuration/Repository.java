package com.ninjaone.backendinterviewproject.web.configuration;

import com.ninjaone.backendinterviewproject.domain.device.DeviceRepository;
import com.ninjaone.backendinterviewproject.domain.extracost.ExtraCostRepository;
import com.ninjaone.backendinterviewproject.infraestructure.ExtraCostRepositoryImp;
import com.ninjaone.backendinterviewproject.infraestructure.jpa.device.DeviceCrudRepository;
import com.ninjaone.backendinterviewproject.infraestructure.jpa.device.DeviceRepositoryJPA;
import com.ninjaone.backendinterviewproject.infraestructure.jpa.rmmservice.RmmServiceCrudRepository;
import com.ninjaone.backendinterviewproject.infraestructure.jpa.rmmservice.RmmServiceRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Repository {

    @Autowired
    DeviceCrudRepository deviceCrud;

    @Autowired
    RmmServiceCrudRepository rmmServiceCrud;

    @Bean
    public DeviceRepository getDeviceRepository() {
        return new DeviceRepositoryJPA(deviceCrud);
    }

    @Bean
    public RmmServiceRepositoryJPA getRmmServiceRepository() {
        return new RmmServiceRepositoryJPA(rmmServiceCrud);
    }

    @Bean
    public ExtraCostRepository getExtraCostRepository() {
        return new ExtraCostRepositoryImp();
    }

}

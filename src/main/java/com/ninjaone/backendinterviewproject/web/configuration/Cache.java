package com.ninjaone.backendinterviewproject.web.configuration;

import com.ninjaone.backendinterviewproject.domain.CacheService;
import com.ninjaone.backendinterviewproject.infraestructure.rudimentarycache.RudimentaryCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Cache {

    @Bean
    public CacheService getCacheService() {
        return new RudimentaryCache();
    }

}

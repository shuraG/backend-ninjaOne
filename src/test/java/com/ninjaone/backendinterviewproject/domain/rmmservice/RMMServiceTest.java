package com.ninjaone.backendinterviewproject.domain.rmmservice;

import com.ninjaone.backendinterviewproject.domain.TypeDevice;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RMMServiceTest {

    @Test
    void givenAnCommonPriceWhenGetAvailabilityForAnyTypeThenGetTrue() {
        var price = new BigDecimal(10.10);
        var rmmService = new RMMService("antivirus", new PriceRMMServiceCommon(price));

        assertTrue(rmmService.isAvailableForTypeDevice(TypeDevice.WINDOWS));
        assertTrue(rmmService.isAvailableForTypeDevice(TypeDevice.MAC));
        assertTrue(rmmService.isAvailableForTypeDevice(TypeDevice.LINUX));
    }
}
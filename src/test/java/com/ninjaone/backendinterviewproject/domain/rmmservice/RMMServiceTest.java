package com.ninjaone.backendinterviewproject.domain.rmmservice;

import com.ninjaone.backendinterviewproject.domain.TypeDevice;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RMMServiceTest {

    @Test
    void givenAnCommonPriceWhenGetAvailabilityForAnyTypeThenGetTrue() {
        var price = new BigDecimal(10.10);
        var rmmService = new RMMService(1, "antivirus", new PriceRMMServiceCommon(price));

        assertTrue(rmmService.isAvailableForTypeDevice(TypeDevice.WINDOWS));
        assertTrue(rmmService.isAvailableForTypeDevice(TypeDevice.MAC));
        assertTrue(rmmService.isAvailableForTypeDevice(TypeDevice.LINUX));
    }

    @Test
    void givenAnPriceForSpecificTypeWhenGetAvailabilityForDifferentTypeThenGetFalse() {
        var price = new BigDecimal(10.10);
        var rmmService = new RMMService(1, "antivirus", new PriceRMMServiceSpecific(TypeDevice.WINDOWS, price));

        assertFalse(rmmService.isAvailableForTypeDevice(TypeDevice.MAC));
    }

    @Test
    void givenAnPriceForSpecificTypeWhenGetAvailabilityForTypeThenGetTrue() {
        var price = new BigDecimal(10.10);
        var rmmService = new RMMService(1, "antivirus", new PriceRMMServiceSpecific(TypeDevice.WINDOWS, price));

        assertTrue(rmmService.isAvailableForTypeDevice(TypeDevice.WINDOWS));
    }

    @Test
    void givenAnCommonPriceWhenGetPriceForAnyTypeThenGetThisPrice() {
        var price = new BigDecimal(10.10);
        var rmmService = new RMMService(1, "antivirus", new PriceRMMServiceCommon(price));

        assertEquals(price, rmmService.getPrice(TypeDevice.WINDOWS));
        assertEquals(price, rmmService.getPrice(TypeDevice.MAC));
        assertEquals(price, rmmService.getPrice(TypeDevice.LINUX));
    }

    @Test
    void givenAnSpecificPriceWhenGetPriceForDifferentTypeThenGetException() {
        var price = new BigDecimal(10.10);
        var rmmService = new RMMService(1, "antivirus", new PriceRMMServiceSpecific(TypeDevice.WINDOWS, price));

        assertThrows(PriceNotAvailableForDevice.class, () -> rmmService.getPrice(TypeDevice.MAC));
    }
}
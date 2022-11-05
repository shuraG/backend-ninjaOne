package com.ninjaone.backendinterviewproject.domain.device;

import com.ninjaone.backendinterviewproject.domain.TypeDevice;
import com.ninjaone.backendinterviewproject.domain.rmmservice.PriceRMMServiceCommon;
import com.ninjaone.backendinterviewproject.domain.rmmservice.PriceRMMServiceSpecific;
import com.ninjaone.backendinterviewproject.domain.rmmservice.RMMService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class DeviceTest {

    @Test
    void givenAInitialDeviceWhenCalculateCostThenGetBaseCost() {
        var basicPrice = new BigDecimal(4.0);
        var baseService = new RMMService("basic", new PriceRMMServiceCommon(basicPrice));
        var device = new Device("Macbook pro", TypeDevice.MAC, baseService);

        var expectedCost = new BigDecimal(4.0);

        assertEquals(expectedCost, device.costServices());
    }

    @Test
    void givenADeviceWithAdditionalServicesWhenCalculateCostThenGetValidCost() {
        var basicPrice = new BigDecimal(4.0);
        var baseService = new RMMService("basic", new PriceRMMServiceCommon(basicPrice));
        var device = new Device("Macbook pro", TypeDevice.MAC, baseService);

        var backupService = new RMMService("backup", new PriceRMMServiceCommon(new BigDecimal(3.0)));
        device.addService(backupService);
        var screenShareService = new RMMService("screen_share", new PriceRMMServiceCommon(new BigDecimal(1.0)));
        device.addService(screenShareService);

        var expectedCost = new BigDecimal(8.0);

        assertEquals(expectedCost, device.costServices());
    }

    @Test
    void givenADeviceWhenAddServiceNotSupportedTypeThenGetException() {
        var basicPrice = new BigDecimal(4.0);
        var baseService = new RMMService("basic", new PriceRMMServiceCommon(basicPrice));
        var deviceMac = new Device("Macbook pro", TypeDevice.MAC, baseService);

        var priceForWindows = new PriceRMMServiceSpecific(TypeDevice.WINDOWS, new BigDecimal(5.0));
        var antivirusForWindowsService = new RMMService("Antivirus", priceForWindows);

        assertThrows(NotAvailableRMMServiceForDevice.class, () -> deviceMac.addService(antivirusForWindowsService));
    }

    @Test
    void givenADeviceWhenAddServiceSupportedTypeThenGetValidCost() {
        var basicPrice = new BigDecimal(4.0);
        var baseService = new RMMService("basic", new PriceRMMServiceCommon(basicPrice));
        var deviceMac = new Device("Macbook pro", TypeDevice.MAC, baseService);

        var priceForMac = new PriceRMMServiceSpecific(TypeDevice.MAC, new BigDecimal(7.0));
        var antivirusForMacService = new RMMService("Antivirus", priceForMac);
        deviceMac.addService(antivirusForMacService);

        var expectedCost = new BigDecimal(11.0);
        assertEquals(expectedCost, deviceMac.costServices());
    }
}
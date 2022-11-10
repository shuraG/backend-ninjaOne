package com.ninjaone.backendinterviewproject.domain.device;

import com.ninjaone.backendinterviewproject.domain.rmmservice.RMMService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class DeviceTest {

    @Test
    void givenADeviceWithZeroServicesWhenCalculateCostSubscriptionThenGetZero() {
        var device = new Device(UUID.randomUUID(), "Macbook pro", TypeDevice.MAC);
        assertEquals(BigDecimal.ZERO, device.costSubscriptions());
    }

    @Test
    void givenADeviceWithAdditionalServicesWhenCalculateCostThenGetValidCost() {
        var device = new Device(UUID.randomUUID(), "Macbook pro", TypeDevice.MAC);

        var backupService = new RMMService(UUID.randomUUID(), "backup", new BigDecimal(3.0));
        var screenShareService = new RMMService(UUID.randomUUID(), "screen_share", BigDecimal.ONE);
        device.addSubscription(backupService);
        device.addSubscription(screenShareService);

        assertEquals(new BigDecimal(4.0), device.costSubscriptions());
    }

    @Test
    void givenADeviceWhenAddServiceNotSupportedTypeThenGetNotAvailableException() {
        var deviceMac = new Device(UUID.randomUUID(), "Macbook pro", TypeDevice.MAC);

        var priceForWindows = new HashMap<TypeDevice, BigDecimal>();
        priceForWindows.put(TypeDevice.WINDOWS, new BigDecimal(5.0));
        var antivirusForWindowsService = new RMMService(UUID.randomUUID(), "Antivirus", priceForWindows);

        assertThrows(NotAvailableRMMServiceForDevice.class, () -> deviceMac.addSubscription(antivirusForWindowsService));
    }

    @Test
    void givenADeviceWhenAddServiceSupportedTypeThenGetValidCost() {
        var deviceMac = new Device(UUID.randomUUID(), "Macbook pro", TypeDevice.MAC);

        var priceForMac = new HashMap<TypeDevice, BigDecimal>();
        priceForMac.put(TypeDevice.MAC, new BigDecimal(7.4));
        var antivirusForMacService = new RMMService(UUID.randomUUID(), "Antivirus", priceForMac);

        deviceMac.addSubscription(antivirusForMacService);

        assertEquals(new BigDecimal(7.4), deviceMac.costSubscriptions());
    }

    @Test
    void givenADeviceWhenAddSameServiceThenGetDuplicateException() {
        var deviceMac = new Device(UUID.randomUUID(), "Macbook pro", TypeDevice.MAC);

        var priceForMac = new HashMap<TypeDevice, BigDecimal>();
        priceForMac.put(TypeDevice.MAC, new BigDecimal(7.0));
        var antivirusForMacService = new RMMService(UUID.fromString("0000-00-00-00-000000"), "Antivirus", priceForMac);
        var antivirusForMacServiceDuplicated = new RMMService(UUID.fromString("0000-00-00-00-000000"), "Antivirus", priceForMac);
        deviceMac.addSubscription(antivirusForMacService);

        assertThrows(RuntimeException.class, () -> deviceMac.addSubscription(antivirusForMacServiceDuplicated));
    }
}
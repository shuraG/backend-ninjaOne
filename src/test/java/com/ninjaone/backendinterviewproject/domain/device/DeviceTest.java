package com.ninjaone.backendinterviewproject.domain.device;

import com.ninjaone.backendinterviewproject.domain.TypeDevice;
import com.ninjaone.backendinterviewproject.domain.rmmservice.RMMService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class DeviceTest {

    @Test
    void givenAInitialDeviceWhenCalculateCostThenGetBaseCost() {
        var device = new Device("Macbook pro", TypeDevice.MAC);
        var expectedCost = new BigDecimal(4.0);
        var emptyServices = new SubscriptionStub();
        assertEquals(expectedCost, device.costServices(emptyServices, device1 -> new BigDecimal(4.0)));
    }

    @Test
    void givenADeviceWithAdditionalServicesWhenCalculateCostThenGetValidCost() {
        var device = new Device("Macbook pro", TypeDevice.MAC);

        var backupService = new RMMService(2, "backup", new BigDecimal(3.0));
        var screenShareService = new RMMService(3, "screen_share", new BigDecimal(1.0));
        var subscriptionStub = new SubscriptionStub();
        subscriptionStub.add(device, backupService);
        subscriptionStub.add(device, screenShareService);


        var expectedCost = new BigDecimal(8.0);

        assertEquals(expectedCost, device.costServices(subscriptionStub, device1 -> new BigDecimal(4.0)));
    }

    @Test
    void givenADeviceWhenAddServiceNotSupportedTypeThenGetException() {
        var deviceMac = new Device("Macbook pro", TypeDevice.MAC);

        var priceForWindows = new HashMap<TypeDevice, BigDecimal>();
        priceForWindows.put(TypeDevice.WINDOWS, new BigDecimal(5.0));
        var antivirusForWindowsService = new RMMService(2, "Antivirus", priceForWindows);

        assertThrows(NotAvailableRMMServiceForDevice.class,
                () -> deviceMac.addSubscription(null, antivirusForWindowsService));
    }

    @Test
    void givenADeviceWhenAddServiceSupportedTypeThenGetValidCost() {
        var deviceMac = new Device("Macbook pro", TypeDevice.MAC);

        var priceForMac = new HashMap<TypeDevice, BigDecimal>();
        priceForMac.put(TypeDevice.MAC, new BigDecimal(7.0));
        var antivirusForMacService = new RMMService(2, "Antivirus", priceForMac);

        var subscriptionService = new SubscriptionStub();
        deviceMac.addSubscription(subscriptionService, antivirusForMacService);

        var expectedCost = new BigDecimal(11.0);

        assertEquals(expectedCost, deviceMac.costServices(subscriptionService, device1 -> new BigDecimal(4.0)));
    }

    @Test
    void givenADeviceWhenAddSameServiceThenGetDuplicatedException() {
        var deviceMac = new Device("Macbook pro", TypeDevice.MAC);

        var priceForMac = new HashMap<TypeDevice, BigDecimal>();
        priceForMac.put(TypeDevice.MAC, new BigDecimal(7.0));
        var antivirusForMacService1 = new RMMService(2, "Antivirus", priceForMac);
        var antivirusForMacService2 = new RMMService(2, "Antivirus", priceForMac);

        var subscriptionService = new SubscriptionStub();
        deviceMac.addSubscription(subscriptionService, antivirusForMacService1);

        assertThrows(RuntimeException.class,
                () -> deviceMac.addSubscription(subscriptionService, antivirusForMacService2));
    }
}
package com.ninjaone.backendinterviewproject.application;


import com.ninjaone.backendinterviewproject.domain.CacheService;
import com.ninjaone.backendinterviewproject.domain.device.Device;
import com.ninjaone.backendinterviewproject.domain.device.DeviceRepository;
import com.ninjaone.backendinterviewproject.domain.device.TypeDevice;
import com.ninjaone.backendinterviewproject.domain.extracost.ExtraCost;
import com.ninjaone.backendinterviewproject.domain.extracost.ExtraCostRepository;
import com.ninjaone.backendinterviewproject.domain.extracost.TypeExtraCost;
import com.ninjaone.backendinterviewproject.domain.rmmservice.RMMService;
import com.ninjaone.backendinterviewproject.domain.rmmservice.RMMServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeviceApplicationTest {
    public static final UUID DEVICE_ID = UUID.fromString("0000-00-00-00-000000");
    public static final UUID RMM_SERVICE_ID = UUID.fromString("0000-00-00-00-000001");


    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private RMMServiceRepository rmmServiceRepository;

    @Mock
    private ExtraCostRepository extraCostRepository;

    @Mock
    public CacheService cache;

    @InjectMocks
    private DeviceApplication testObject;

    @BeforeEach
    public void beforeEach() {

    }

    @Test
    public void givenDeviceWithCostNotCachedThenGetPrice() {
        when(extraCostRepository.findByType(any())).thenReturn(Optional.of(getExtraCost()));
        when(cache.get(any(), any())).thenReturn(Optional.empty());
        when(deviceRepository.get(DEVICE_ID)).thenReturn(Optional.of(getDeviceSubscribed()));

        var cost = testObject.calculateTotal(Set.of(DEVICE_ID));
        var expectedCost = new BigDecimal(11);

        assertEquals(expectedCost, cost);
        Mockito.verify(deviceRepository, times(1)).get(DEVICE_ID);
        Mockito.verify(cache, times(1)).get(any(), any());
        Mockito.verify(cache, times(1)).save(any(), any(), any());
    }

    @Test
    public void givenDeviceWithCostCachedThenGetPrice() {
        when(extraCostRepository.findByType(any())).thenReturn(Optional.of(getExtraCost()));
        when(cache.get(any(), any())).thenReturn(Optional.of(BigDecimal.ONE));

        var cost = testObject.calculateTotal(Set.of(DEVICE_ID));

        assertEquals(BigDecimal.ONE, cost);
        Mockito.verify(deviceRepository, times(0)).get(DEVICE_ID);
        Mockito.verify(cache, times(1)).get(any(), any());
        Mockito.verify(cache, times(0)).save(any(), any(), any());
    }

    @Test
    public void givenDeviceWhenSubscribedThenUpdateCache() {
        when(deviceRepository.get(DEVICE_ID)).thenReturn(Optional.of(getDevice()));
        when(rmmServiceRepository.get(RMM_SERVICE_ID)).thenReturn(Optional.of(getRmmService()));

        testObject.addSubscription(DEVICE_ID, RMM_SERVICE_ID);

        Mockito.verify(deviceRepository, times(1)).get(DEVICE_ID);
        Mockito.verify(rmmServiceRepository, times(1)).get(RMM_SERVICE_ID);
        Mockito.verify(cache, times(1)).update(any(), any(), any(), any());
        Mockito.verify(cache, times(0)).save(any(), any(), any());
    }

    @Test
    public void givenDeviceWhenUnsubscribedThenUpdateCache() {
        when(deviceRepository.get(DEVICE_ID)).thenReturn(Optional.of(getDeviceSubscribed()));
        when(rmmServiceRepository.get(RMM_SERVICE_ID)).thenReturn(Optional.of(getRmmService()));

        testObject.removeSubscription(DEVICE_ID, RMM_SERVICE_ID);

        Mockito.verify(deviceRepository, times(1)).get(DEVICE_ID);
        Mockito.verify(rmmServiceRepository, times(1)).get(RMM_SERVICE_ID);
        Mockito.verify(cache, times(1)).update(any(), any(), any(), any());
        Mockito.verify(cache, times(0)).save(any(), any(), any());
    }

    public Device getDevice() {
        return new Device(DEVICE_ID, "laptop123", TypeDevice.MAC);
    }

    public Device getDeviceSubscribed() {
        var rmmService = getRmmService();
        var device = new Device(DEVICE_ID, "laptop123", TypeDevice.MAC);
        device.subscribe(rmmService);
        return device;
    }

    public RMMService getRmmService() {
        return new RMMService(RMM_SERVICE_ID, "a", BigDecimal.ONE);
    }

    public ExtraCost getExtraCost() {
        return new ExtraCost(BigDecimal.TEN, TypeExtraCost.BASE_COST_OBLIGATORY);
    }

}
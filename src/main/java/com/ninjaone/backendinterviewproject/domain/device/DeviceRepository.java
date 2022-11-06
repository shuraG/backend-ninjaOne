package com.ninjaone.backendinterviewproject.domain.device;

import java.util.List;

public interface DeviceRepository {

    List<Device> getDevices(long customerId);
}

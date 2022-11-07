package com.ninjaone.backendinterviewproject.domain.device;

import java.util.List;

public interface DeviceRepository {

    void save(Device d);

    List<Device> getDevices(long customerId);
}

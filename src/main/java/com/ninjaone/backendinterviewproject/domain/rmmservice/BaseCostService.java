package com.ninjaone.backendinterviewproject.domain.rmmservice;

import com.ninjaone.backendinterviewproject.domain.device.Device;

import java.math.BigDecimal;

public interface BaseCostService {

    BigDecimal get(Device device);

}

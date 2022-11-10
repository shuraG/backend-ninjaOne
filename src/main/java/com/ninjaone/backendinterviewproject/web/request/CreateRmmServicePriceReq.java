package com.ninjaone.backendinterviewproject.web.request;

import com.ninjaone.backendinterviewproject.domain.device.TypeDevice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CreateRmmServicePriceReq {
    public String name;
    public BigDecimal price;
    public TypeDevice type;
    public Map<TypeDevice, BigDecimal> prices = new HashMap<>();
}

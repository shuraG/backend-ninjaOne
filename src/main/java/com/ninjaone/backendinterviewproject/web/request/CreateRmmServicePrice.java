package com.ninjaone.backendinterviewproject.web.request;

import com.ninjaone.backendinterviewproject.domain.TypeDevice;

import java.math.BigDecimal;
import java.util.Map;

public class CreateRmmServicePrice {
    public String name;
    public BigDecimal price;
    public TypeDevice type;
    public Map<TypeDevice, BigDecimal> prices;
}

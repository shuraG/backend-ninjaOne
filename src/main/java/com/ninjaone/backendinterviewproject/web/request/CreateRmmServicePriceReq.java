package com.ninjaone.backendinterviewproject.web.request;

import com.ninjaone.backendinterviewproject.domain.device.TypeDevice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CreateRmmServicePriceReq {
    private String name;
    private BigDecimal price;
    private Map<TypeDevice, BigDecimal> prices = new HashMap<>();

    public CreateRmmServicePriceReq(String name, TypeDevice type, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public CreateRmmServicePriceReq(String name, TypeDevice type, Map<TypeDevice, BigDecimal> prices) {
        this.name = name;
        this.prices = prices;
    }

    public CreateRmmServicePriceReq() {

    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Map<TypeDevice, BigDecimal> getPrices() {
        return prices;
    }
}

package com.ninjaone.backendinterviewproject.domain;

import java.math.BigDecimal;
import java.util.List;

public class Device {
    private Long id;
    private TypeDevice type;
    private List<PriceRMMService> priceRMMServices;

    public BigDecimal costService() {
        return priceRMMServices.stream()
                .map(s -> s.getCost())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

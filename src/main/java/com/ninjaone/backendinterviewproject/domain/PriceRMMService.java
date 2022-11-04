package com.ninjaone.backendinterviewproject.domain;

import java.math.BigDecimal;

public class PriceRMMService {
    private RMMService service;
    private TypeDevice typeDevice;
    private BigDecimal cost;

    public BigDecimal getCost() {
        return this.cost;
    }

    public TypeDevice getTypeDevice() {
        return this.typeDevice;
    }
}

package com.ninjaone.backendinterviewproject.domain;

import java.math.BigDecimal;

public abstract class PriceRMMService {
    private BigDecimal cost;

    public PriceRMMService(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getCost() {
        return this.cost;
    }

    public abstract boolean isAvailableFor(TypeDevice typeDevice);
}

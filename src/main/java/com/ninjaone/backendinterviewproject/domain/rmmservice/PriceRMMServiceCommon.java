package com.ninjaone.backendinterviewproject.domain.rmmservice;

import com.ninjaone.backendinterviewproject.domain.TypeDevice;

import java.math.BigDecimal;

public class PriceRMMServiceCommon implements PriceRMMService {
    private BigDecimal cost;

    public PriceRMMServiceCommon(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public BigDecimal getCost() {
        return this.cost;
    }

    @Override
    public boolean isAvailableFor(TypeDevice typeDevice) {
        return true;
    }
}

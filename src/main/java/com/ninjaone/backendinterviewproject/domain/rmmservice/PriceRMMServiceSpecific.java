package com.ninjaone.backendinterviewproject.domain.rmmservice;

import com.ninjaone.backendinterviewproject.domain.TypeDevice;

import java.math.BigDecimal;

public class PriceRMMServiceSpecific implements PriceRMMService {

    private BigDecimal cost;

    private TypeDevice typeDevice;

    public PriceRMMServiceSpecific(TypeDevice typeDevice, BigDecimal cost) {
        this.typeDevice = typeDevice;
        this.cost = cost;
    }

    @Override
    public BigDecimal getCost() {
        return this.cost;
    }

    @Override
    public boolean isAvailableFor(TypeDevice typeDevice) {
        if (typeDevice.equals(this.typeDevice)) return true;
        return false;
    }
}

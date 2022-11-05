package com.ninjaone.backendinterviewproject.domain.rmmservice;

import com.ninjaone.backendinterviewproject.domain.TypeDevice;

import java.math.BigDecimal;

public class PriceRMMServiceSpecific extends PriceRMMService {
    private TypeDevice typeDevice;

    public PriceRMMServiceSpecific(TypeDevice typeDevice, BigDecimal cost) {
        super(cost);
        this.typeDevice = typeDevice;
    }


    @Override
    public boolean isAvailableFor(TypeDevice typeDevice) {
        if (typeDevice.getName().equals(typeDevice.getName())) return true;
        return false;
    }
}

package com.ninjaone.backendinterviewproject.domain.rmmservice;

import com.ninjaone.backendinterviewproject.domain.TypeDevice;

import java.math.BigDecimal;

public class PriceRMMServiceCommon extends PriceRMMService {

    public PriceRMMServiceCommon(BigDecimal cost) {
        super(cost);
    }

    @Override
    public boolean isAvailableFor(TypeDevice typeDevice) {
        return true;
    }
}

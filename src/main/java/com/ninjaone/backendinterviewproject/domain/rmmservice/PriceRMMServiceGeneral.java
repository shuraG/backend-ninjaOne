package com.ninjaone.backendinterviewproject.domain.rmmservice;

import com.ninjaone.backendinterviewproject.domain.TypeDevice;

import java.math.BigDecimal;

public class PriceRMMServiceGeneral extends PriceRMMService {

    public PriceRMMServiceGeneral(BigDecimal cost) {
        super(cost);
    }

    @Override
    public boolean isAvailableFor(TypeDevice typeDevice) {
        return true;
    }
}

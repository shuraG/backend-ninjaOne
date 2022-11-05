package com.ninjaone.backendinterviewproject.domain.rmmservice;

import com.ninjaone.backendinterviewproject.domain.TypeDevice;

import java.math.BigDecimal;

public interface PriceRMMService {
    BigDecimal getCost();

    boolean isAvailableFor(TypeDevice typeDevice);
}

package com.ninjaone.backendinterviewproject.web.response;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TotalCostResponse {
    private BigDecimal totalCost;

    public TotalCostResponse(BigDecimal totalCost) {
        this.totalCost = scaleTwoDecimals(totalCost);
    }

    private BigDecimal scaleTwoDecimals(BigDecimal totalCost) {
        return totalCost.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }
}

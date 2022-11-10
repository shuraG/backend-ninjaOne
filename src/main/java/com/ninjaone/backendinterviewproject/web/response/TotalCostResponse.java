package com.ninjaone.backendinterviewproject.web.response;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TotalCostResponse {
    private BigDecimal totalCost;

    public TotalCostResponse(BigDecimal totalCost) {
        this.totalCost = totalCost;
        scaleTwoDecimals();
    }

    private void scaleTwoDecimals() {
        this.totalCost.setScale(2, RoundingMode.HALF_UP);
    }
}

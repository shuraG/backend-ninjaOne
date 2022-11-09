package com.ninjaone.backendinterviewproject.domain.extracost;

import java.math.BigDecimal;

public class ExtraCost {

    private BigDecimal cost;
    private TypeExtraCost type;

    public ExtraCost(BigDecimal cost, TypeExtraCost type) {
        this.cost = cost;
        this.type = type;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public TypeExtraCost getType() {
        return type;
    }
}

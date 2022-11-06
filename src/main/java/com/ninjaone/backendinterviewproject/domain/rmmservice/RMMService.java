package com.ninjaone.backendinterviewproject.domain.rmmservice;

import com.ninjaone.backendinterviewproject.domain.TypeDevice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RMMService {
    private long id;
    private String name;
    private BigDecimal commonPrice;
    private Map<TypeDevice, BigDecimal> prices;

    public RMMService(long id, String name, Map<TypeDevice, BigDecimal> prices) {
        this.id = id;
        this.name = name;
        this.prices = new HashMap<>();
        this.prices.putAll(prices);
    }

    public RMMService(long id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.commonPrice = price;
    }

    public boolean isAvailableForTypeDevice(TypeDevice typeDevice) {
        return findPrice(typeDevice).isPresent();
    }

    public BigDecimal getPrice(TypeDevice typeDevice) {
        return findPrice(typeDevice)
                .orElseThrow(PriceNotAvailableForDevice::new);
    }

    private Optional<BigDecimal> findPrice(TypeDevice typeDevice) {
        if (commonPrice != null) return Optional.of(commonPrice);
        return Optional.ofNullable(prices.get(typeDevice));
    }

    public long getId() {
        return id;
    }
}

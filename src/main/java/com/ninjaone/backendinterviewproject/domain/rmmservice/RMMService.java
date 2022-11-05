package com.ninjaone.backendinterviewproject.domain.rmmservice;

import com.ninjaone.backendinterviewproject.domain.TypeDevice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RMMService {
    private long id;
    private String name;
    private List<PriceRMMService> prices;

    public RMMService(String name, PriceRMMService price) {
        this.name = name;
        prices = new ArrayList<>();
        prices.add(price);
    }

    public boolean isAvailableForTypeDevice(TypeDevice typeDevice) {
        return findPrice(typeDevice).isPresent();
    }

    public BigDecimal getPrice(TypeDevice typeDevice) {
        return findPrice(typeDevice).map(priceRMMService -> priceRMMService.getCost())
                .orElseThrow(PriceNotAvailableForDevice::new);
    }

    private Optional<PriceRMMService> findPrice(TypeDevice typeDevice) {
        return prices.stream().filter(p -> p.isAvailableFor(typeDevice))
                .findFirst();
    }
}

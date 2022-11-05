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

    public RMMService(long id, String name, PriceRMMService price) {
        this.id = id;
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

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof RMMService))
            return false;
        if (obj == this)
            return true;
        return this.id == ((RMMService) obj).id;
    }
}

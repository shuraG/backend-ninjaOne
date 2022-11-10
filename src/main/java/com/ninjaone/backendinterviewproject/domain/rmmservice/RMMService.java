package com.ninjaone.backendinterviewproject.domain.rmmservice;

import com.ninjaone.backendinterviewproject.domain.BusinessException;
import com.ninjaone.backendinterviewproject.domain.device.TypeDevice;
import com.ninjaone.backendinterviewproject.infraestructure.jpa.rmmservice.RmmServiceEntity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class RMMService {
    private UUID id;
    private String name;
    private BigDecimal commonPrice;
    private Map<TypeDevice, BigDecimal> prices;

    public RMMService(UUID id, String name, Map<TypeDevice, BigDecimal> prices) {
        validateName(name);
        this.id = id;
        this.name = name;
        this.prices = new HashMap<>();
        this.prices.putAll(prices);
    }

    public RMMService(UUID id, String name, BigDecimal price) {
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

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new BusinessException("Service needs a name!");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof RMMService))
            return false;
        if (obj == this)
            return true;
        return this.id.equals(((RMMService) obj).id);
    }

    public RmmServiceEntity getEntity() {
        return new RmmServiceEntity(id, name, commonPrice, prices);
    }

}

package com.ninjaone.backendinterviewproject.infraestructure.jpa.rmmservice;

import com.ninjaone.backendinterviewproject.domain.TypeDevice;
import com.ninjaone.backendinterviewproject.domain.rmmservice.RMMService;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "RMM_SERVICE")
public class RmmServiceEntity {
    @Id
    private UUID id;
    private String name;

    @OneToMany(mappedBy = "rmmServiceId", cascade = CascadeType.ALL)
    private List<RmmServicePriceEntity> prices;

    public RmmServiceEntity() {
    }

    public RmmServiceEntity(UUID id, String name, BigDecimal commonPrice, Map<TypeDevice, BigDecimal> prices) {
        this.id = id;
        this.name = name;
        this.prices = buildListPrices(commonPrice, prices);
    }

    private List<RmmServicePriceEntity> buildListPrices(BigDecimal commonPrice, Map<TypeDevice, BigDecimal> prices) {
        if (prices != null && !prices.isEmpty()) {
            return prices.entrySet().stream()
                    .map(entry -> new RmmServicePriceEntity(id, entry.getKey().toString(), entry.getValue()))
                    .collect(Collectors.toList());
        }

        return List.of(new RmmServicePriceEntity(id, "-", commonPrice));
    }

    public RMMService getRmmService() {
        if (prices.stream().count() == 1 && prices.stream().anyMatch(s -> s.getTypeDevice().equals("-"))) {
            return new RMMService(id, name, prices.get(0).getPrice());
        }
        Map<TypeDevice, BigDecimal> typeDeviceWithPrice = prices.stream()
                .collect(Collectors.toMap(r -> TypeDevice.valueOf(r.getTypeDevice()), o -> o.getPrice()));

        return new RMMService(id, name, typeDeviceWithPrice);
    }

}

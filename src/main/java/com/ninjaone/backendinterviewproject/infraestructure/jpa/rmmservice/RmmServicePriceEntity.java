package com.ninjaone.backendinterviewproject.infraestructure.jpa.rmmservice;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@IdClass(RmmServicePriceId.class)
@Table(name = "RMM_SERVICE_PRICE")
public class RmmServicePriceEntity implements Serializable {

    @Id
    private UUID rmmServiceId;
    @Id
    private String typeDevice;

    private BigDecimal price;

    public RmmServicePriceEntity() {
    }

    public RmmServicePriceEntity(UUID rmmServiceId, String typeDevice, BigDecimal price) {
        System.out.println("typeDevice" + typeDevice);
        this.rmmServiceId = rmmServiceId;
        this.typeDevice = typeDevice;
        this.price = price;
    }

    public UUID getRmmServiceId() {
        return rmmServiceId;
    }

    public String getTypeDevice() {
        return typeDevice;
    }

    public BigDecimal getPrice() {
        return price;
    }
}


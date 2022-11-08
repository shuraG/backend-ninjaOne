package com.ninjaone.backendinterviewproject.infraestructure.jpa.rmmservice;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class RmmServicePriceId implements Serializable {

    private UUID rmmServiceId;

    private String typeDevice;

}

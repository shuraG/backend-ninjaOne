package com.ninjaone.backendinterviewproject.domain;

import java.util.List;

public class RMMService {
    private long id;
    private String name;
    private List<PriceRMMService> prices;

    public boolean isAvailableForTypeDevice(TypeDevice typeDevice) {
        return true;
    }

    public PriceRMMService getPrice(TypeDevice typeDevice) {
        return null;
    }
}

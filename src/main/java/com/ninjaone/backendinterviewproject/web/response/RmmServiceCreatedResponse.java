package com.ninjaone.backendinterviewproject.web.response;

import java.util.UUID;

public class RmmServiceCreatedResponse {

    private UUID rmmServiceId;

    public RmmServiceCreatedResponse(UUID rmmServiceId) {
        this.rmmServiceId = rmmServiceId;
    }

    public UUID getRmmServiceId() {
        return rmmServiceId;
    }
}

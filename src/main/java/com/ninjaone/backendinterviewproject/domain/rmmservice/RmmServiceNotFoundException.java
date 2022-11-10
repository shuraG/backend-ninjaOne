package com.ninjaone.backendinterviewproject.domain.rmmservice;

import com.ninjaone.backendinterviewproject.domain.NotFoundException;

import java.util.UUID;

public class RmmServiceNotFoundException extends NotFoundException {
    private UUID id;

    public RmmServiceNotFoundException(UUID id) {
        super(id, "RmmService");
    }
}

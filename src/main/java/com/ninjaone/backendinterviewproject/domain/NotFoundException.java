package com.ninjaone.backendinterviewproject.domain;

import java.util.UUID;

public abstract class NotFoundException extends BusinessException {
    private UUID id;
    private String type;

    public NotFoundException(UUID id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getMessage() {
        return "Not found " + type + " with ID: " + id;
    }

    public NotFoundException() {

    }
}

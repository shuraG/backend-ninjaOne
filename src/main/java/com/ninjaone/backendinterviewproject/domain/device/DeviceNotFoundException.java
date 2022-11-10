package com.ninjaone.backendinterviewproject.domain.device;

import com.ninjaone.backendinterviewproject.domain.NotFoundException;

import java.util.UUID;

public class DeviceNotFoundException extends NotFoundException {
    private UUID id;

    public DeviceNotFoundException(UUID id) {
        super(id, "Device");
    }
}

package com.ninjaone.backendinterviewproject.web.response;

import java.util.UUID;

public class DeviceCreatedResponse {

    private UUID deviceId;

    public DeviceCreatedResponse(UUID idDevice) {
        this.deviceId = idDevice;
    }

    public UUID getDeviceId() {
        return deviceId;
    }
}

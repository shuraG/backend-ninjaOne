package com.ninjaone.backendinterviewproject.web.request;

import com.ninjaone.backendinterviewproject.domain.device.TypeDevice;

public class CreateDeviceReq {
    private String systemName;
    private TypeDevice typeDevice;

    public CreateDeviceReq(String systemName, TypeDevice typeDevice) {
        this.systemName = systemName;
        this.typeDevice = typeDevice;
    }

    public String getSystemName() {
        return systemName;
    }

    public TypeDevice getTypeDevice() {
        return typeDevice;
    }
}

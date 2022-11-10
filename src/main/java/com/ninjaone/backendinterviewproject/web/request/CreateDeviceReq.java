package com.ninjaone.backendinterviewproject.web.request;

public class CreateDeviceReq {
    private String systemName;
    private String typeDevice;

    public CreateDeviceReq(String systemName, String typeDevice) {
        this.systemName = systemName;
        this.typeDevice = typeDevice;
    }

    public String getSystemName() {
        return systemName;
    }

    public String getTypeDevice() {
        return typeDevice;
    }
}

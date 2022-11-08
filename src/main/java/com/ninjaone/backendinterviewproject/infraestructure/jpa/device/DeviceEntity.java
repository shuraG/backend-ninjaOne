package com.ninjaone.backendinterviewproject.infraestructure.jpa.device;

import com.ninjaone.backendinterviewproject.domain.TypeDevice;
import com.ninjaone.backendinterviewproject.domain.device.Device;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "DEVICE")
public class DeviceEntity {
    @Id
    private UUID id;
    private String systemName;
    private String type;

    public DeviceEntity(UUID id, String systemName, TypeDevice type) {
        this.id = id;
        this.systemName = systemName;
        this.type = type.toString();
    }

    public DeviceEntity() {
    }

    public Device getDevice() {
        return new Device(id, systemName, TypeDevice.valueOf(type));
    }
}

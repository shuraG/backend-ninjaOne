package com.ninjaone.backendinterviewproject.infraestructure.jpa.device;

import com.ninjaone.backendinterviewproject.domain.device.Device;
import com.ninjaone.backendinterviewproject.domain.device.TypeDevice;
import com.ninjaone.backendinterviewproject.domain.rmmservice.RMMService;
import com.ninjaone.backendinterviewproject.infraestructure.jpa.rmmservice.RmmServiceEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "DEVICE")
public class DeviceEntity {
    @Id
    private UUID id;
    @Column(unique = true)
    private String systemName;
    private String type;
    @ManyToMany
    @JoinTable(
            name = "subscription",
            joinColumns = @JoinColumn(name = "device_id"),
            inverseJoinColumns = @JoinColumn(name = "rmm_service_id")
    )
    List<RmmServiceEntity> subscriptions;

    public DeviceEntity(UUID id, String systemName, TypeDevice type, List<RMMService> subscriptions) {
        this.id = id;
        this.systemName = systemName;
        this.type = type.toString();
        this.subscriptions = subscriptions.stream().map(RMMService::getEntity).collect(Collectors.toList());
    }

    public DeviceEntity() {
    }

    public Device getDevice() {
        var device = new Device(id, systemName, TypeDevice.valueOf(type));
        subscriptions.stream().map(s -> s.getRmmService()).forEach(s -> device.addSubscription(s));
        return device;
    }

    public UUID getId() {
        return this.id;
    }
}

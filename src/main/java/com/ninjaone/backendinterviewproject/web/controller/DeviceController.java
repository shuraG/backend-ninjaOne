package com.ninjaone.backendinterviewproject.web.controller;

import com.ninjaone.backendinterviewproject.application.DeviceApplication;
import com.ninjaone.backendinterviewproject.domain.device.TypeDevice;
import com.ninjaone.backendinterviewproject.web.request.CreateDeviceReq;
import com.ninjaone.backendinterviewproject.web.request.CreateSubscriptionReq;
import com.ninjaone.backendinterviewproject.web.response.DeviceCreatedResponse;
import com.ninjaone.backendinterviewproject.web.response.TotalCostResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;


import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/device")
public class DeviceController {
    private final DeviceApplication deviceApplication;

    public DeviceController(DeviceApplication deviceApplication) {
        this.deviceApplication = deviceApplication;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceCreatedResponse postDevice(@RequestBody CreateDeviceReq request) {
        var typeDevice = toTypeDevice(request.getTypeDevice());
        var deviceId = deviceApplication.createDevice(request.getSystemName(), typeDevice);
        return new DeviceCreatedResponse(deviceId);
    }

    @PostMapping("/{id_device}/subscription")
    @ResponseStatus(HttpStatus.CREATED)
    public void postSubscription(
            @PathVariable(name = "id_device") UUID deviceId,
            @RequestBody CreateSubscriptionReq request
    ) {
        deviceApplication.addSubscription(deviceId, request.serviceId);
    }

    @GetMapping("/cost")
    public TotalCostResponse postSubscription(
            @RequestBody Set<UUID> request
    ) {
        var totalCost = deviceApplication.calculateTotal(request);
        return new TotalCostResponse(totalCost);
    }

    @DeleteMapping("/{id_device}/subscription")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubscription(
            @PathVariable(name = "id_device") UUID deviceId,
            @RequestBody CreateSubscriptionReq request
    ) {
        deviceApplication.removeSubscription(deviceId, request.serviceId);
    }

    @DeleteMapping("/{id_device}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDevice(@PathVariable(name = "id_device") UUID deviceId) {
        deviceApplication.removeDevice(deviceId);
    }

    private TypeDevice toTypeDevice(String typeDevice) {
        return TypeDevice.valueOf(typeDevice.toUpperCase());
    }
}

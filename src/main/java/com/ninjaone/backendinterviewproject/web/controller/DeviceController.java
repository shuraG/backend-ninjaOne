package com.ninjaone.backendinterviewproject.web.controller;

import com.ninjaone.backendinterviewproject.application.DeviceApplication;
import com.ninjaone.backendinterviewproject.domain.device.TypeDevice;
import com.ninjaone.backendinterviewproject.web.request.CreateDevice;
import com.ninjaone.backendinterviewproject.web.request.CreateSubscription;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    public UUID postDevice(@RequestBody CreateDevice request) {
        var typeDevice = toTypeDevice(request.typeDevice);
        return deviceApplication.createDevice(request.systemName, typeDevice);
    }

    @PostMapping("/{id_device}/subscription")
    @ResponseStatus(HttpStatus.CREATED)
    public void postSubscription(
            @PathVariable(name = "id_device") UUID deviceId,
            @RequestBody CreateSubscription request
    ) {
        deviceApplication.addSubscription(deviceId, request.serviceId);
    }

    @GetMapping("/cost")
    public BigDecimal postSubscription(
            @RequestBody Set<UUID> request
    ) {
        return deviceApplication.calculateTotal(request).setScale(2, RoundingMode.HALF_UP);
    }

    @DeleteMapping("/{id_device}/subscription")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubscription(
            @PathVariable(name = "id_device") UUID deviceId,
            @RequestBody CreateSubscription request
    ) {
        deviceApplication.removeSubscription(deviceId, request.serviceId);
    }

    @DeleteMapping("/{id_device}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void postDevice(@PathVariable(name = "id_device") UUID deviceId) {
        deviceApplication.removeDevice(deviceId);
    }

    private TypeDevice toTypeDevice(String typeDevice) {
        return TypeDevice.valueOf(typeDevice.toUpperCase());
    }
}

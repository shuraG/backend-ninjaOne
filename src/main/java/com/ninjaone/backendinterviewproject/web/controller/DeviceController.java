package com.ninjaone.backendinterviewproject.web.controller;

import com.ninjaone.backendinterviewproject.application.DeviceApplication;
import com.ninjaone.backendinterviewproject.domain.TypeDevice;
import com.ninjaone.backendinterviewproject.web.request.CreateDevice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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


    private TypeDevice toTypeDevice(String typeDevice) {

        return TypeDevice.valueOf(typeDevice.toUpperCase());
    }
}

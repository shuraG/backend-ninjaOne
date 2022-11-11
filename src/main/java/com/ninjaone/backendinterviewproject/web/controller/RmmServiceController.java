package com.ninjaone.backendinterviewproject.web.controller;

import com.ninjaone.backendinterviewproject.application.RmmServiceApplication;
import com.ninjaone.backendinterviewproject.web.request.CreateRmmServicePriceReq;
import com.ninjaone.backendinterviewproject.web.response.RmmServiceCreatedResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@RestController
@RequestMapping("/rmmservice")
public class RmmServiceController {
    private final RmmServiceApplication rmmServiceApplication;

    public RmmServiceController(RmmServiceApplication rmmServiceApplication) {
        this.rmmServiceApplication = rmmServiceApplication;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RmmServiceCreatedResponse post(@RequestBody CreateRmmServicePriceReq request) {
        if (request.getPrices().isEmpty()) {
            return new RmmServiceCreatedResponse(rmmServiceApplication.createRmmService(request.getName(), request.getPrice()));
        }
        return new RmmServiceCreatedResponse(rmmServiceApplication.createRmmService(request.getName(), request.getPrices()));
    }

    @DeleteMapping("/{id_rmmservice}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteDevice(@PathVariable(name = "id_rmmservice") UUID rmmServiceId) {
        rmmServiceApplication.removeRmmService(rmmServiceId);
    }
}

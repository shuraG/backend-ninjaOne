package com.ninjaone.backendinterviewproject.web.controller;

import com.ninjaone.backendinterviewproject.application.RmmServiceApplication;
import com.ninjaone.backendinterviewproject.web.request.CreateRmmServicePrice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

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
    public UUID post(@RequestBody CreateRmmServicePrice request) {
        if (request.prices.isEmpty()) {
            return rmmServiceApplication.createRmmService(request.name, request.price);
        }
        return rmmServiceApplication.createRmmService(request.name, request.prices);
    }
}

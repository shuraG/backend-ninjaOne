package com.ninjaone.backendinterviewproject.application;

import com.ninjaone.backendinterviewproject.domain.BusinessException;

public class NotFoundException extends BusinessException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException() {
    }
}

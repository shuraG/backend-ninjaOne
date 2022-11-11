package com.ninjaone.backendinterviewproject.domain.device;

import com.ninjaone.backendinterviewproject.domain.BusinessException;

public class NotAvailableRMMServiceForDevice extends BusinessException {

    public NotAvailableRMMServiceForDevice(TypeDevice type) {
        super("Not available rmm service for type device: " + type.toString());
    }

    public NotAvailableRMMServiceForDevice() {
    }
}

package com.ninjaone.backendinterviewproject.domain;

import com.ninjaone.backendinterviewproject.domain.BusinessException;

public class DuplicateException extends BusinessException {

    public DuplicateException(String type, String attributeDuplicated, String value) {
        super("Duplicate: " + type + "with attribute: " + attributeDuplicated + ":" + value + "is already exist!");
    }


}

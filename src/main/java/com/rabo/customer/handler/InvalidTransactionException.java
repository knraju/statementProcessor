package com.rabo.customer.handler;

import com.rabo.customer.dto.CustomResponseEntity;

public class InvalidTransactionException extends RuntimeException {
    private CustomResponseEntity customResponseEntity;

    public InvalidTransactionException(CustomResponseEntity customResponseEntity) {
        super(customResponseEntity.getResult().toString());
        this.customResponseEntity = customResponseEntity;
    }

    public CustomResponseEntity getCustomResponseEntity() {
        return this.customResponseEntity;
    }
}

package com.rabo.customer.handler;

import com.rabo.customer.dto.RaboResponse;

public class TransactionException extends RuntimeException {
    private RaboResponse raboResponse;
    public TransactionException() {
        super();
    }

    public TransactionException(String errorMessage) {
        super(errorMessage);
    }

    public TransactionException(Throwable cause) {
        super(cause);
    }

    public TransactionException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    public TransactionException(RaboResponse raboResponse) {
        super(raboResponse.getResult().toString());
        this.raboResponse = raboResponse;
    }

    public RaboResponse getRaboResponse() {
        return this.raboResponse;
    }
}

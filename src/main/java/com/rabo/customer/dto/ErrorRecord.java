package com.rabo.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ErrorRecord {
    private Long reference;
    private String accountNumber;

    public ErrorRecord(Transaction cs) {
        this.reference = cs.getReference();
        this.accountNumber = cs.getAccountNumber();
    }
}

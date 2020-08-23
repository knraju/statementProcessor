package com.rabo.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class Transaction {
    @NotNull
    private Long reference;
    @NotNull
    private String accountNumber;
    private String description;
    @NotNull
    private BigDecimal startBalance;
    @NotNull
    private BigDecimal mutation;
    @NotNull
    private BigDecimal endBalance;
}

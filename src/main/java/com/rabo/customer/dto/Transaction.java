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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @NotNull(message = "Reference should not be null")
    private Long reference;
    @NotNull(message = "accountNumber should not be null")
    private String accountNumber;
    private String description;
    @NotNull(message = "startBalance should not be null")
    private BigDecimal startBalance;
    @NotNull(message = "mutation should not be null")
    private BigDecimal mutation;
    @NotNull(message = "endBalance should not be null")
    private BigDecimal endBalance;

}

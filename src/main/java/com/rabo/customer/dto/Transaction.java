package com.rabo.customer.dto;

import lombok.*;

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

    public boolean validEndBalance() {
        System.out.println(this.getEndBalance() +" - " + this.getStartBalance() +" !="+ this.getMutation());
        System.out.println(this.getEndBalance().subtract(this.getStartBalance()));
        System.out.println(this.getEndBalance().subtract(this.getStartBalance()).compareTo(this.getMutation()));
        return this.getEndBalance().subtract(this.getStartBalance()).compareTo(this.getMutation()) != 0;
    }

}

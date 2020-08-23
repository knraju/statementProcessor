package com.rabo.customer.dto;

import com.rabo.customer.handler.ResultCodes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CustomResponseEntity {
    ResultCodes result;
    List<ErrorRecord> errorRecords;

}

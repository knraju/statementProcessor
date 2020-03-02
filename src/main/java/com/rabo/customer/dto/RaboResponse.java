package com.rabo.customer.dto;

import com.rabo.customer.handler.ResultCodes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RaboResponse {
    ResultCodes result = ResultCodes.SUCCESSFUL;
    List<ErrorRecord> errorRecords = new ArrayList();

}

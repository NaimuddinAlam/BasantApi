package com.medayes.erp.ErpApi.dto.response.salary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalaryHeadResponse {

    private String compCode;
    private String centCode;
    private String empCode;

    private String payableDays;
    private String payMonth;

    private String headCode;
    private String salHeadCode;

    private String salAmount;
    private String arrears;
}
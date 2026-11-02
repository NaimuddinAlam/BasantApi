package com.medayes.erp.ErpApi.dto.response.salary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalaryResponse {

    private CompanyResponse company;

    private SalaryEmployeeResponse employee;

    private List<SalaryHeadResponse> earnings;

    private List<SalaryHeadResponse> deductions;
}
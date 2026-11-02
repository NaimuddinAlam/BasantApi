package com.medayes.erp.ErpApi.dto.response.salary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponse {

    private String centCode;
    private String centName;
    private String add1;
    private String add2;
    private String cityCode;
    private String stateCode;
    private String countryCode;
    private String postCode;
    private String compName;
}
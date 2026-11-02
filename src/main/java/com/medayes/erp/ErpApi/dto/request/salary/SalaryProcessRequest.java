package com.medayes.erp.ErpApi.dto.request.salary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalaryProcessRequest {

    private String pcompCode;
    private String pcentCode;
    private String pbranchCode;

    private String pempType;
    private String pempGroup;
    private String pdeptCode;
    private String pempCode;

    private String pattanMonth;

    private String puserCode;

    private String pextra;
    private String pextra1;
    private String pextra2;
    private String pextra3;
    private String pextra4;
    private String pextra5;
}
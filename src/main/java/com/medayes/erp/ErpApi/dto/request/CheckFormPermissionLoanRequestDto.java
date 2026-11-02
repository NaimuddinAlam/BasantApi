package com.medayes.erp.ErpApi.dto.request;

import lombok.Data;

@Data
public class CheckFormPermissionLoanRequestDto {

    private String pcompCode;     // @PCOMP_CODE
    private String pcentCode;      // @PDOC_TYPE
    private String pempCode;   // @PMODULE_CODE

}

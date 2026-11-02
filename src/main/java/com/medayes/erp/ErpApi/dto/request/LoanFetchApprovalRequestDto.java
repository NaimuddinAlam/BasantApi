package com.medayes.erp.ErpApi.dto.request;


import lombok.Data;

@Data
public class LoanFetchApprovalRequestDto {

    private String pcomcode;      // TO1
    private String pcentercode;   // DE1
    private String pbranchcode;   // ""
    private String pemploytype;   // NULL
    private String pgroup;        // NULL
    private String pdeptcode;     // ""
    private String pempcode;      // TON00004
    private String pfromdate;     // NULL
    private String ptodate;       // NULL
    private String pdateformat;   // dd/mm/yyyy
    private String pextra;        // UNA
    private String pextra1;       // "1"
    private String pextra2;       // AD1
    private String pextra3;       // ""
    private String pextra4;       // ""

}
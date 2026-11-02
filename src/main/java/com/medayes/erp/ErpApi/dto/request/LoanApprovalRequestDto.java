package com.medayes.erp.ErpApi.dto.request;



import lombok.Data;

@Data
public class LoanApprovalRequestDto {

    private String pcompCode;       // TO1
    private String pcentCode;       // DE1
    private String pempCode;        // TON00004
    private String pappNo;          // DE20250002
    private String pappDate;        // 11/07/2025 (dd/mm/yyyy)
    private String ploanType;       // SH6
    private String pappFlag;        // A
    private String pappUser;        // AD1
    private String pappRemarks;     // ""
    private String pappType;        // 1
    private String psanctionAmt;    // 30000
    private String psanctionInst;   // 10000
    private String psanNoInst;      // 3
    private String pextra1;         // ""
    private String pextra2;         // ""
    private String pextra3;
    private String pextra4;
    private String pextra5; // ""

}

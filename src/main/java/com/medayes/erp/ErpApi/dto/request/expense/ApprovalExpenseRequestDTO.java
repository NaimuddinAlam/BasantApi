package com.medayes.erp.ErpApi.dto.request.expense;

import lombok.Data;

@Data
public class ApprovalExpenseRequestDTO {
    private String compCode;
    private   String centCode;
    private  String branchCode;
    private  String location;
    private   String advReqId;
    private  String fromDate;
    private  String toDate;
    private  String suggestedEmpCode;
    private  String dateFormate;
    private  String status;
    private  String extra1;
    private  String extra2;
    private  String extra3;
    private  String extra4;
    private  String extra5;
    private  String appDate;
    private  String appFlag;
    private  String appUser;
    private String approvalAmt;
    private String approvalRemark;
    private String canDate;
    private String canFlag;
    private String canUser;
    private String canRemarks;
    private  String appType;
}


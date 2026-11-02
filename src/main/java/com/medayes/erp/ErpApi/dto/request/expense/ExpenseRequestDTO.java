package com.medayes.erp.ErpApi.dto.request.expense;

import lombok.Data;

@Data
public class ExpenseRequestDTO{

    private String compCode;
    private   String centCode;
    private  String branchCode;
    private  String location;
    private   String advReqId;
    private  String advReqDate;
    private  String suggestedEmpCode;
    private  String advanceAmount;
    private  String estimatedAmount;
    private  String purpose;
    private  String attachImage;
    private  String ip;
    private  String userId;
    private  String type;
    private  String extra1;
    private  String extra2;
    private  String extra3;
    private  String extra4;
    private  String extra5;
}

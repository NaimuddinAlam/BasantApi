package com.medayes.erp.ErpApi.dto;


import lombok.Data;

@Data

public class InvReqSlipResponse {
    private String centCode;
    private String branchCode;
    private String centName;
    private String branchName;
    private String refJobNo;
    private String refJobDate;
    private String rsDate;
    private String itemType;
    private String itemCode;
    private String itemTypeName;
    private String itemName;
    private String size;
    private String sizeName;
    private String color;
    private String baseName;
    private String convFactor;
    private String purUom;
    private String status;
    private String depttName;


}


package com.medayes.erp.ErpApi.dto.response.indent;


import lombok.Data;


@Data
public class QuotationRequisitionDTO {
    private String quotNo;
    private String quotDate;
    private String partyCode;
    private String supplierName;
    private String stateCode;
    private String stateName;
    private String rating;
    private String quotQty;
    private String rate;
    private String amount;
    private String tolerancePer;
    private String taxAmt;
    private String landingCost;
    private String itemCode;
    private String color;
    private String size;
    private String manualDocNo;
    private String prApprovalRemarks;
}
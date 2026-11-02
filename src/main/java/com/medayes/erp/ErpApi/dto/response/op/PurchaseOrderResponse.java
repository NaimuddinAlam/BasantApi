package com.medayes.erp.ErpApi.dto.response.op;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class   PurchaseOrderResponse

{
    private String refJobNo;
    private  String refJobDate;
    private  String compCode;
    private String centCode;
    private  String branchCode;
    private String poNo;
    private  String poDate;
    private  String partyCode;
    private  String suppName;
    private String mode;
    private  String delDate;
    private String rating;
    private String attachment; // file path or base64
    private  String totalItem;
    private String poAmount;
}
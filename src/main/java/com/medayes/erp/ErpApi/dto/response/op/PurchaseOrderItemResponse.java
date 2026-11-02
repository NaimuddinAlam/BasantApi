package com.medayes.erp.ErpApi.dto.response.op;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderItemResponse {
    private String refJobNo;
    private String refJobDate;
    private String refFinItemCode;
    private String compCode;
    private String centCode;
    private String branchCode;
    private String poNo;
    private String poDate;
    private String partyCode;
    private String suppName;
    private String rating;
    private String itemType;
    private String itemCode;
    private String miscMastName;
    private String itemName;
    private String size;
    private String sizeName;
    private String color;
    private String baseUom;
    private String baseName;
    private String convFactor;
    private String purUom;
    private String poQty;
    private String rate;
    private String amount;
    private String landingCost;
    private String tolerancePer;
    private String remarks;
    private String status;
    private String refPrNo;
    private String comments;
    private String refQuotNo;
    private String gateRecQty;
    private String packingTypeCode;
    private String packingTypeName;
    private String mode;
    private String attachment;
    private String itemNameOth;
    private String minQuotRequired;
}

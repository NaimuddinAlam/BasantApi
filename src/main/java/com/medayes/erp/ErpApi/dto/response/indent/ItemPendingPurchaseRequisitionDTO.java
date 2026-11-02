package com.medayes.erp.ErpApi.dto.response.indent;

public record ItemPendingPurchaseRequisitionDTO(
        String itemType,
        String productCode,
        String itemTypeName,
        String itemName,
        String size,
        String color,
        String uom,
        String balanceRSQty,
        String prQty,
        String closingStock,
        String rsQty,
        String branchName,
        String branchCode,
        String remark,
        String convFactor,
         String  purUom,
        String baseUom,
        String efFinItemCode

) {
}

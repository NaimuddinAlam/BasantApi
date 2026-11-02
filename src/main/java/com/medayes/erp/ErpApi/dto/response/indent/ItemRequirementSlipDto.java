package com.medayes.erp.ErpApi.dto.response.indent;

public record ItemRequirementSlipDto(
        String itemType,
        String itemCode,
        String itemTypeName,
        String itemName,
        String size,
        String color,
        String uom,
        String rqQty,
        String appQty,
        String remark,
        String status
) {
}

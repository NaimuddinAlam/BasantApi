package com.medayes.erp.ErpApi.dto.response.indent;

import java.util.List;

public record PendingPurchaseRequisitionResponseDTO(
        String rsNo,
        String rsDate,



        List<ItemPendingPurchaseRequisitionDTO> items
) {
}

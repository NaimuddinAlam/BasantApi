package com.medayes.erp.ErpApi.dto.response.production;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class StockOrgResponseDto {
    private String  orgName;
    private String  productName;
    private List<StockLedgerResponse> dto;
    private Double receiptQty;
    private Double receiptQtyKg;
    private Double issueQty;
    private Double issueQtyKg;
    private Double balanceQty;
    private Double qtyInKg;

}

package com.medayes.erp.ErpApi.dto.response.production;


public record ReportOrganiserStockResponseDto<T>(
        String status,
        String message,
        int code,
        T data,
        Double receiptQtyTotal,
        Double receiptQtyKgTotal,
        Double issueQtyTotal,
         Double issueQtyKgTotal,
                Double balanceQtyTotal,
                Double qtyInKgTotal
) {
}

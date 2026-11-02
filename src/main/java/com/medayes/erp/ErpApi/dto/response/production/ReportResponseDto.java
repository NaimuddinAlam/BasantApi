package com.medayes.erp.ErpApi.dto.response.production;


public record ReportResponseDto<T>(
        String status,
        String message,
        int code,
        T data,
        Double total,
        Double totalKg
) {
}

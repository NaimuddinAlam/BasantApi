package com.medayes.erp.ErpApi.dto.response.task;



public record TaskMastResponsibleResponseDto<T>(
        String status,
        String message,
        int code,
        T data
) {
}

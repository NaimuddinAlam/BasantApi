package com.medayes.erp.ErpApi.dto.response.tracking;

public record EmployeeTrackingRequestDto(String compCode, String empCode, String fromDate, String toDate, String reportingTo) {
}

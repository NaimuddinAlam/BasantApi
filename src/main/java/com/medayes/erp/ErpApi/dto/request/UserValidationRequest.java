package com.medayes.erp.ErpApi.dto.request;

import java.time.LocalDate;

public record UserValidationRequest(String mobile, String empCode, LocalDate inactiveDate) {
}

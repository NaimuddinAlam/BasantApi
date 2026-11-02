package com.medayes.erp.ErpApi.dto.response;


import org.springframework.http.HttpStatus;

public record LoanResponseDto(
        String status,
        String message,
        int code,
        String data
) {
  public static LoanResponseDto success(String loanNo) {
    return new LoanResponseDto("SUCCESS", "Operation completed successfully.",
            HttpStatus.OK.value(), loanNo);
  }

  public static LoanResponseDto failure(String message, int code) {
    return new LoanResponseDto("FAILED", message, code, null);
  }

  public static LoanResponseDto error(String message) {
    return new LoanResponseDto("ERROR", message, HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
  }
}

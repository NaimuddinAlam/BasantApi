package com.medayes.erp.ErpApi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanResponse<T> {

    private String status;
    private String message;
    private int code;
    private T data;

    // SUCCESS Response
    public static <T> LoanResponse<T> success(T data) {
        return new LoanResponse<>(
                "SUCCESS",
                "Operation completed successfully.",
                HttpStatus.OK.value(),
                data
        );
    }

    // FAILURE Response
    public static <T> LoanResponse<T> failure(String message, int code) {
        return new LoanResponse<>(
                "FAILED",
                message,
                code,
                null
        );
    }
}

package com.medayes.erp.ErpApi.dto.response.expense;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseExpenseDTO<T> {
    String message;
    Boolean status;
    Integer  httpStatus;
    T response;
}

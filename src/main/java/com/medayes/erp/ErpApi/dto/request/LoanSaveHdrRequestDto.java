package com.medayes.erp.ErpApi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoanSaveHdrRequestDto {

    @NotBlank(message = "Company Code is required.")
    private String compCode;

    @NotBlank(message = "Center Code is required.")
    private String centCode;

    @NotBlank(message = "Application Date is required.")
    private String appDate;

    @NotBlank(message = "Branch Code is required.")
    private String branchCode;

    @NotBlank(message = "Employee Code is required.")
    private String empCode;

    @NotBlank(message = "Loan Reason is required.")
    private String loanReason;

    @NotBlank(message = "Transaction Type (I/U/D) is required.")
    private String type;        // ✅ Correct name for @Type

    @NotBlank(message = "User Id is required.")
    private String userId;

    @NotBlank(message = "Application Number (Loan No) is required.")
    private String loanNo;

    private String ip;

}


package com.medayes.erp.ErpApi.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class LoanSaveDetRequestDto {

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

    private String rowId;

    @NotBlank( message = "Loan Type must not exceed 10 characters.")
    private String loanType;

    @Digits(integer = 12, fraction = 2, message = "Loan Amount must have max 12 digits and 2 decimals.")
    @PositiveOrZero(message = "Loan Amount must be zero or positive.")
    private String loanAmt;

    @Digits(integer = 12, fraction = 2, message = "Installment Amount must have max 12 digits and 2 decimals.")
    @PositiveOrZero(message = "Installment Amount must be zero or positive.")
    private String instAmt;

    @PositiveOrZero(message = "Number of Installments must be zero or positive.")
    @Max(value = 999, message = "Number of Installments cannot exceed 999.")
    private String noOfInst;

    @DateTimeFormat(pattern = "d/M/yyyy")
    private String loanStartDate;


    private String status;
    private String appNo;
    private String extra;
    private String extra1;
    private String extra2;
    private String extra3;
    private String extra4;
    private  String employtype;
    private  String group;
    private  String deptcode;
    private  String fromdate;
    private  String todate;
}


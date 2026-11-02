package com.medayes.erp.ErpApi.dto.request;




import jakarta.validation.constraints.NotBlank;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequestDto {

    @NotBlank(message = "Company code is required")
    private String compCode;

    @NotBlank(message = "Center code is required")

    private String centCode;

    @NotBlank(message = "Loan date is required")
    @DateTimeFormat(pattern = "M/d/yyyy")
    private String loanDate;


    private String extra1;


    private String extra2;


    private String extra3;
}


package com.medayes.erp.ErpApi.dto.response.expense;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdvanceExpenseResponseDTO {
    private String compCode;
    private String centCode;
    private String branchCode;
    private String branchName;
    private String location;
    private String whName;
    private String advReqId;
    private String advReqDate;
    private double advanceAmount;
    private double estimatedAmount;
    private String purpose;
    private  double approvalAmt;
    private  String approvalDate;
    private  String approvalFlag;
    private  String approvalUser;
    private  String empName;
    private  String designationName;
    private  String status;
    private  String empCode;



}

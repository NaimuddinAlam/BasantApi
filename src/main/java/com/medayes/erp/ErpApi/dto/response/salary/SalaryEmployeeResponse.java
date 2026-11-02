package com.medayes.erp.ErpApi.dto.response.salary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalaryEmployeeResponse {

    private String compCode;
    private String centCode;
    private String empCode;
    private String structureCode;

    private String payMonth;
    private String payableDays;
    private String salaryDate;

    private String pfEmployer;
    private String esicEmployer;

    private String empName;
    private String desig;
    private String department;

    private String dob;
    private String doj;

    private String gradeName;
    private String locName;

    private String bankAccNo;
    private String pfNo;
    private String panNo;

    private String uanNo;
    private String esicNo;
    private String bankName;
    private String lName;
    private String lEmpNo;
    private  String lJobDate;
    private  String lBankName;
    private  String lDesignation;
    private  String lBankAccountNo;
    private  String lDepartment;
    private  String lPANNumber;
    private  String lLocation;
    private  String lPFNO;
    private  String lEffectiveWorkDays;
    private  String lPFUAN;
    private  String lLOP;




}
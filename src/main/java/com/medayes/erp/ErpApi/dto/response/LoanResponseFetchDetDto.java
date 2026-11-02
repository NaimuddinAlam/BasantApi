package com.medayes.erp.ErpApi.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class LoanResponseFetchDetDto {
    private String EMP_CODE;
    private String EMP_NAME;
    private String COMP_CODE;
    private String CENT_CODE;
    private String ROW_ID;
    private String APP_NO;
    private String APP_DATE;
    private String LOAN_TYPE;
    private String LOAN_REASON;
    private String LOAN_TYPE_NAME;
    private String LOAN_AMT;
    private String INST_AMT;
    private String NO_OF_INST,REMARKS;
    private String LOAN_START_DATE;
    private String SANCTION_AMT;
    private String SANCTION_NO_OF_INST;
    private String SANCTION_INST_AMT;
    private String STATUSNAME;
    private  String EDIT_PERMISSION;
    private  String CODE;
    private  String TYPE;
    private  String RES;
    private  String status;
    private  String status1;
    private  String status2;

   private  String approval;
   private  String approval1;
   private  String approval2;
    private  String approvalCancel;
    private  String approvalCancel1;
    private  String approvalCancel2;
    private  String approvalDate;
   private  String approvalDate1;
    private  String approvalDate2;


}


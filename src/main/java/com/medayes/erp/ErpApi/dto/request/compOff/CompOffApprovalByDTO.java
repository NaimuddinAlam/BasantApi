package com.medayes.erp.ErpApi.dto.request.compOff;


import lombok.Data;

@Data
public class CompOffApprovalByDTO {
    private  String compReqNo;
    private  String cancelUser;
    private  String cancelDate;
    private  String cancelRemark;
    private  String cancelFlag;
    private  String createDate;
    private  String approvalFlag;
    private  String approvalUser;
    private  String approvalDate;
    private  String approvalRemark;
}


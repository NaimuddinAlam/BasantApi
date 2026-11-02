package com.medayes.erp.ErpApi.dto.request.compOff;


import lombok.Data;



@Data
public class CompOffApprovalDTO {
    private  String empCode;
    private  String empName;
    private  String fullHalf;
    private  String compCode;
    private  String remarks;
    private  String approvalDate;
    private  String approvalRemark;
    private  String compHoliday;
    private  String status;
    private  String appNo;
    private  String cancelUser;
    private  String cancelDate;
    private  String cancelRemark;
    private  String cancelFlag;
    private  String approvalFlag;
    private  String createDate;
    private  String compReqNo;
    private  String inTime;
    private  String outTime;
    private  String total_time;

}

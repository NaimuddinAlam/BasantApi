package com.medayes.erp.ErpApi.dto.request.notification;

import lombok.Data;

@Data
public class NotificationInfoDTO {
    private String token;
    private String empName;
    private String empCode;
    private String fromDate;
    private String toDate;
    private String title;
    private String userType;
    private  String leaveType;
    private  String leaveTypeName;
    private  String message;
    private  String orderNo;
    private  String customerName;

}

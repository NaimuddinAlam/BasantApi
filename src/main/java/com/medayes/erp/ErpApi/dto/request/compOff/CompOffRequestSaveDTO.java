package com.medayes.erp.ErpApi.dto.request.compOff;

import lombok.Data;

@Data
public class CompOffRequestSaveDTO {
    private String compReqNo;
    private String compHoliday;
    private String fullHalf;
    private String empCode;
    private String appNo;
    private String compCode;
    private String centCode;
    private String branchCode;
    private String creditDate;
    private String remarks;
    private String createUser;

}

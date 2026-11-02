package com.medayes.erp.ErpApi.dto.request.production;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class QualityCheckRequestDto {
    private String compCode;
    private String centCode;
    private String distNo;
    private String refNo;
    private String orgCode;
    private String distFromDt;
    private String distToDt;
    private String farmerCode;
    private String villageCode;
    private String itemCode;
    private String batchMale;
    private String batchFemale;
    private String farmerLotStatus;
    private String status;
    private String extra1;
    private String extra2;
    private String extra3;
    private String extra4;
    private String extra5;
    private  String reasonCode;
    private  String ledgerCodeSubCode;

}

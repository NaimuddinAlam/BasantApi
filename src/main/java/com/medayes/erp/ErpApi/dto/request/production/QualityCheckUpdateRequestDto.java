package com.medayes.erp.ErpApi.dto.request.production;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class QualityCheckUpdateRequestDto {
    private String pcompCode;
    private String pcentCode;
    private String pdistNo;
    private String porgCode;
    private String pfarmerCode;
    private String pitemCode;
    private String psizeCode;
    private String pbatchMale;
    private String pbatchFemale;
    private String psowingDateMale1;
    private String psowingDateFemale1;
    private String psowingDateMale2;
    private String psowingDateFemale2;
    private String psurveyNo;
    private String pfarmerLotNo;
    private String pprevCropCode;
    private String ptotalRowsMale;
    private String ptotalRowsFemale;
    private String pplantsInRowMale;
    private String pplantsInRowFemale;
    private String pstatus;
    private String pextra1;
    private String pextra2;
    private String pextra3;
    private String pextra4;
    private String pextra5;
    private String qcStatus;
    private String qcRejectReasonCode;
}

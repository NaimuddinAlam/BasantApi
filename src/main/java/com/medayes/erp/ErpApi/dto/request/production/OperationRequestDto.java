
package com.medayes.erp.ErpApi.dto.request.production;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
public class OperationRequestDto {

    // Company and distribution details
    private String compCode;
    private String centCode;
    private String branchCode;
    private String rowId;

    private String refAgrEntryNo;
    private String refIssueNo;
    private String distNo;
    private String refDistNo;
    private String refNo;



    // Dates
    private String visitDate;
    private String entryDate;
    private String distFromDt;
    private String distToDt;

    private String finYear;
    private String seasionCode;
    private String orgCode;
    private String farmerCode;
    private String itemCode;

    // Sowing details
    private String sowingDateFemale1;
    private String sowingDateMale1;
    private String sowingDateFemale2;
    private String sowingDateMale2;

    private String sowingMaleDate;
    private String sowingFemaleDate;

    private String surveyNo;
    private String batchMale;
    private String batchFemale;
    private String farmerLotNo;

    // Acreage details
    private String areaAcre;
    private String pldAcre;
    private String nsaAcre;
    private String pldReasonCode;

    // Operation details
    private String operTypeCode;
    private String operCode;
    private String targetDays;
    private String targetDate;
    private String actualDate;

    private String valueText;
    private String valueNum;
    private String rating;
    private String operStatus;
    private String operComments;
    private String remarks;
    private String observRemarks;

    // Audit details
    private String ip;
    private String createUser;

    // Extra parameters
    // PEXTRA  = ATTACH_CODE
    // PEXTRA1 = ITEM_TYPE
    // PEXTRA2 = CITY_CODE
    // PEXTRA3 = CROP_CODE
    // PEXTRA4 = VISITED_PERSON_CODE
    // PEXTRA5 = ENTRY_NO
    private String extra;
    private String extra1;
    private String extra2;
    private String extra3;
    private String extra4;
    private String extra5;

    // Existing fields retained for other APIs
    private String villageCode;
    private String farmerLotStatus;
    private String status;
    private String extra6;
    private String page;
    private String size;
    private String qcType;
    private List<MultipartFile> photo;
}

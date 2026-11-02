package com.medayes.erp.ErpApi.dto.response.production;
import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class QualityCheckResponseDto {

    private String compCode;
    private String compName;
    private String centCode;
    private String distrNo;
    private String distrDate;
    private String refAgrEntryNo;
    private String finyearCode;
    private String finyearNick;
    private String seasonCode;
    private String seasonName;
    private String orgCode;
    private String orgName;
    private String farmerCode;
    private String farmerName;
    private String fatherName;
    private String villageName;
    private String cityName;
    private String itemType;
    private String itemTypeName;
    private String cropCode;
    private String cropName;
    private String itemCode;
    private String itemName;
    private String size;
    private String sizeName;
    private String color;
    private String colorName;
    private String packingTypeCode;
    private String packingTypeName;
    private String seedStage;
    private String seedStageName;
    private String sedVarietyCode;
    private String sedVarietyName;
    private String batchNo;
    private String distrQty;
    private String areaAcre;
    private String sowingDateMale1;
    private String totalRows;
    private String plantsInRow;
    private String prevCropCode;
    private String prevCropName;
    private String harvestDate;
    private String farmerLotNo;
    private String remarks;
    private String surveyNo ;
    private String qcStatus;
    private String qcRejectReasonCode;
    private String qcRejectReasonName;

}
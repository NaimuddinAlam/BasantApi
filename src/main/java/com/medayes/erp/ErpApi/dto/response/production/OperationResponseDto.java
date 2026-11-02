package com.medayes.erp.ErpApi.dto.response.production;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OperationResponseDto {

    private String compCode;
    private String compName;
    private String centCode;
    private String distrNo;
    private String distrDate;
    private String refAgrEntryNo;
    private String refAgrDate;
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
    private Integer totalRows;
    private Integer plantsInRow;
    private String prevCropCode;
    private String prevCropName;
    private String harvestDate;
    private String remarks;



    // Generate Getters and Setters in your IDE
}

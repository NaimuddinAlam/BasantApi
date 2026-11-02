package com.medayes.erp.ErpApi.dto.response.production;

import lombok.Builder;
import lombok.Data;

@Data
@Builder



public class ReportReposeDto {

    private String compCode;
    private String compNick;

    private String centCode;
    private String centNick;

    private String branchCode;
    private String branchNick;

    private String puCode;
    private String puName;

    private String whCode;
    private String whName;

    private String returnTypeName;

    private String seasonCode;
    private String seasonName;

    private String finyearCode;
    private String finyearNick;

    private String orgCode;
    private String orgName;

    private String docType;
    private String docNo;
    private String docDate;

    private String vehicleNo;
    private String grNo;
    private String grDate;
    private String insurance;

    private String trCode;
    private String trName;
    private String trModeCode;
    private String trModeName;

    private String driverName;
    private String driverMobileNo;

    private String remarks;
    private String ip;
    private String createdBy;
    private String creationDate;

    private String contCode;
    private String contName;

    private String inchargeCode;
    private String inchargeName;

    private String weighmentName;
    private String returnAgainst;

    private String saleTypeCode;
    private String saleTypeName;

    private String crLedgerCodeSubcode;
    private String crLedgerCodeName;

    private String cropCode;
    private String cropName;

    private String seedClassCode;
    private String itemCatName;

    private String cropType;
    private String cropTypeName;

    private String itemType;
    private String itemTypeName;

    private String itemCode;
    private String itemName;

    private String size;
    private String sizeName;

    private String mode;
    private String modeName;

    private String color;

    private String packingType;
    private String packingTypeName;

    private String baseUom;
    private String baseUomName;

    private String convFactor;

    private String purUom;
    private String purUomName;

    private String batchNo;
    private String expiryDate;

    private String saleReturnAmount;
    private String saleReturnRate;

    private String sriQty;
    private String qtyInKg;

    private String qc;
    private String landingCost;
    private String sriAmount;

    private String noOfBoxes;
    private String noOfBags;
    private String grossWeight;

    private String refAgrNo;
    private String rackNo;
    private String binNo;
    private  String farmerCode;
    private  String farmerName;
    private  String villageCode;
    private  String  villageName;
    private  String distrQty;
    private  String distrQtyKg;
    private  String areaAcre;

}
package com.medayes.erp.ErpApi.dto.response.indent;




import lombok.Data;


@Data

public class PurchaseRequisitionResponseDTO {

    private String refJobNo;
    private String refJobDate;
    private String refFinItemCode;
    private String prDate;
    private String refQuotNo;

    private String compCode;
    private String centCode;
    private String branchCode;
    private String prNo;

    private String itemType;
    private String itemCode;
    private String miscMastName;
    private String itemName;

    private String size;
    private String sizeName;
    private String color;

    private String baseUom;
    private String baseName;
    private String convFactor;

    private String purUom;
    private String purUomName;

    private String prQty;
    private String poRaisedQty;
    private String prBalanceQty;

    private String prApprovalFlag;
    private String prApprovalUser;
    private String prApprovalDate;

    private String prCancelFlag;
    private String prCancelDate;
    private String prCancelUser;

    private String prCloseFlag;
    private String prCloseUser;
    private String prCloseDate;

    private String remarks;
    private String status;
    private String comments;

    private String refRsNo;
    private String quotCnt;
    private String itemQuotCnt;

    private String productCode;
    private String mode;
    private String convOperator;
}
package com.medayes.erp.ErpApi.dto.response.indent;

import lombok.Data;


@Data


public class PurchaseRequisitionResponseDataDTO {

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


    public PurchaseRequisitionResponseDataDTO(String refJobNo, String refJobDate, String refFinItemCode, String prDate, String refQuotNo, String compCode, String centCode, String branchCode, String prNo, String itemType, String itemCode, String miscMastName, String itemName, String size, String sizeName, String color, String baseUom, String baseName, String convFactor, String purUom, String purUomName, String prQty, String poRaisedQty, String prBalanceQty, String prApprovalFlag, String prApprovalUser, String prApprovalDate, String prCancelFlag, String prCancelDate, String prCancelUser, String prCloseFlag, String prCloseUser, String prCloseDate, String remarks, String status, String comments, String refRsNo, String quotCnt, String itemQuotCnt, String productCode, String mode, String convOperator) {
        this.refJobNo = refJobNo;
        this.refJobDate = refJobDate;
        this.refFinItemCode = refFinItemCode;
        this.prDate = prDate;
        this.refQuotNo = refQuotNo;
        this.compCode = compCode;
        this.centCode = centCode;
        this.branchCode = branchCode;
        this.prNo = prNo;
        this.itemType = itemType;
        this.itemCode = itemCode;
        this.miscMastName = miscMastName;
        this.itemName = itemName;
        this.size = size;
        this.sizeName = sizeName;
        this.color = color;
        this.baseUom = baseUom;
        this.baseName = baseName;
        this.convFactor = convFactor;
        this.purUom = purUom;
        this.purUomName = purUomName;
        this.prQty = prQty;
        this.poRaisedQty = poRaisedQty;
        this.prBalanceQty = prBalanceQty;
        this.prApprovalFlag = prApprovalFlag;
        this.prApprovalUser = prApprovalUser;
        this.prApprovalDate = prApprovalDate;
        this.prCancelFlag = prCancelFlag;
        this.prCancelDate = prCancelDate;
        this.prCancelUser = prCancelUser;
        this.prCloseFlag = prCloseFlag;
        this.prCloseUser = prCloseUser;
        this.prCloseDate = prCloseDate;
        this.remarks = remarks;
        this.status = status;
        this.comments = comments;
        this.refRsNo = refRsNo;
        this.quotCnt = quotCnt;
        this.itemQuotCnt = itemQuotCnt;
        this.productCode = productCode;
        this.mode = mode;
        this.convOperator = convOperator;
    }
}
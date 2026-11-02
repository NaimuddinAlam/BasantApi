
        package com.medayes.erp.ErpApi.dto.response.indent;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PendingPurchaseRequisitionDTO implements Serializable {

    @JsonProperty("COMP_CODE")
    private String compCode;

    @JsonProperty("CENT_CODE")
    private String centCode;

    @JsonProperty("BRANCH_CODE")
    private String branchCode;

    @JsonProperty("CENT_NAME")
    private String centName;

    @JsonProperty("BRANCH_NAME")
    private String branchName;

    @JsonProperty("RS_NO")
    private String rsNo;

    @JsonProperty("RS_DATE")
    private String rsDate;

    @JsonProperty("REF_JOB_NO")
    private String refJobNo;

    @JsonProperty("REF_JOB_DATE")
    private String refJobDate;

    @JsonProperty("REF_FIN_ITEM_CODE")
    private String refFinItemCode;

    @JsonProperty("DEPTT_CODE")
    private String depttCode;

    @JsonProperty("DEPTT_NAME")
    private String depttName;

    @JsonProperty("WORK_CENTER_CODE")
    private String workCenterCode;

    @JsonProperty("WORK_CENTER_NAME")
    private String workCenterName;

    @JsonProperty("ITEM_TYPE")
    private String itemType;

    @JsonProperty("ITEM_TYPE_NAME")
    private String itemTypeName;

    @JsonProperty("ITEM_CODE")
    private String itemCode;

    @JsonProperty("ITME_NAME")
    private String itemName;

    @JsonProperty("SIZE")
    private String size;

    @JsonProperty("SIZE_NAME")
    private String sizeName;

    @JsonProperty("COLOR")
    private String color;

    @JsonProperty("BASE_UOM")
    private String baseUom;

    @JsonProperty("BASE_NAME")
    private String baseName;

    @JsonProperty("CONV_FACTOR")
    private String convFactor;

    @JsonProperty("PUR_UOM")
    private String purUom;

    @JsonProperty("PUR_UOM_NAME")
    private String purUomName;

    @JsonProperty("PACKING_TYPE_CODE")
    private String packingTypeCode;

    @JsonProperty("RS_QTY")
    private String rsQty;

    @JsonProperty("PR_RAISED_QTY")
    private String prRaisedQty;

    @JsonProperty("ISSUED_QTY")
    private String issuedQty;

    @JsonProperty("RS_DISAPP_QTY")
    private String rsDisappQty;

    @JsonProperty("RS_BALANCE_QTY")
    private String rsBalanceQty;

    @JsonProperty("RS_APPROVAL_FLAG")
    private String rsApprovalFlag;

    @JsonProperty("RS_APPROVAL_USER")
    private String rsApprovalUser;

    @JsonProperty("RS_APPROVAL_DATE")
    private String rsApprovalDate;

    @JsonProperty("RS_CANCEL_FLAG")
    private String rsCancelFlag;

    @JsonProperty("RS_CANCEL_DATE")
    private String rsCancelDate;

    @JsonProperty("RS_CANCEL_USER")
    private String rsCancelUser;

    @JsonProperty("RS_CLOSE_FLAG")
    private String rsCloseFlag;

    @JsonProperty("RS_CLOSE_USER")
    private String rsCloseUser;

    @JsonProperty("RS_CLOSE_DATE")
    private String rsCloseDate;

    @JsonProperty("CLOSING_STK")
    private String closingStk;

    @JsonProperty("AVERAGE_RATE")
    private String averageRate;

    @JsonProperty("REMARKS")
    private String remarks;

    @JsonProperty("LAST_PUR_RATE")
    private String lastPurRate;

    @JsonProperty("DEPENDANCY")
    private String dependancy;

    @JsonProperty("PRODUCT_CODE")
    private String productCode;
}


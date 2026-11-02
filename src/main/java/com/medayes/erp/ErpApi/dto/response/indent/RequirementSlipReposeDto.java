package com.medayes.erp.ErpApi.dto.response.indent;



public record   RequirementSlipReposeDto(
        String centCode,
        String branchCode,
        String whCode,
        String centName,
        String branchName,
        String whName,

        String refJobNo,
        String refJobDate,
        String rsDate,
        String compCode,

        String rsNo,
        String itemType,
        String itemCode,
        String itemTypeName,
        String itemName,

        String size,
        String sizeName,
        String color,

        String baseUom,
        String baseName,
        String convFactor,

        String purUom,

        String rsRaisedQty,
        String rsQty,
        String prRaisedQty,
        String issuedQty,
        String rsBalanceQty,

        String rsApprovalDate,
        String rsCancelDate,
        String rsCancelUser,

        String rsCloseFlag,
        String rsCloseUser,
        String rsCloseDate,

        String remarks,
        String status,
        String comments,

        String productCode,

        String depttCode,
        String depttName,

        String raisedBy,
        String raisedName,
        String phone1,
        String emailId1,

        String attachment,
        String closingStk,

        String seq3ApprovalUserName,
        String seq3ApprovalRemarks,
        String seq3Status,
        String seq3ApprovalDate,
        String seq3ApprovalQty,

        String seq1ApprovalUserName,
        String seq1ApprovalRemarks,
        String seq1Status,
        String seq1ApprovalDate,
        String seq1ApprovalQty,

        String seq2ApprovalUserName,
        String seq2ApprovalRemarks,
        String seq2Status,
        String seq2ApprovalDate,
        String seq2ApprovalQty

) {
}

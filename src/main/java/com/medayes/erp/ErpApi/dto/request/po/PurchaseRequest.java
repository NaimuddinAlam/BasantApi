package com.medayes.erp.ErpApi.dto.request.po;

public record PurchaseRequest(
        String pCompCode,      // @P_COMP_CODE
        String pCentCode,      // @P_CENT_CODE
        String pBranch,        // @P_BRANCH
        Integer pDeptNo,       // @P_DEPTNO
        String pPrNo,          // @P_PRNO
        String pItemType,      // @P_ITEMTYPE
        String pItem,          // @P_ITEM
        String pPrDateFrom, // @P_PR_Date_From
        String pPrDateTo,
        String  itemName,
        String size,
         String color,// @PPR_Date_To
        String dateFormat,     // @dateformat
        String pExtra,         // @PEXTRA
        String pExtra1,        // @PEXTRA1
        String pExtra2,        // @PEXTRA2
        String pExtra3,        // @PEXTRA3
        String pExtra4,        // @PEXTRA4
        String pExtra5         // @PEXTRA5
) {
}

package com.medayes.erp.ErpApi.dto.request.indent;

import lombok.Data;

@Data
public class QuotationRequisitionRequestDTO {
    private String compCode;     // @P_COMP_CODE
    private String centCode;     // @P_CENT_CODE
    private String branch;       // @P_BRANCH
    private String deptNo;       // @P_DEPTNO
    private String prNo;         // @P_PRNO
    private String color;        // @PCOLOR
    private String size;         // @PSIZE
    private String itemName;     // @P_ITEM_NAME
    private String dateFormat;   // @dateformat
}

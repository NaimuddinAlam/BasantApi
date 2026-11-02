package com.medayes.erp.ErpApi.dto.request.indent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PendingPurchaseRequisitionRequestDTO {
    private  String compCode;
    private  String centCode;
    private  String branchCode;
    private  String deptCode;
    private  String rsNo;
    private  String itemtype;
    private  String itemCode;
    private  String rsDateFrom;
    private  String rdDateTo;
    private  String dateFormat;
    private  String pextra;
    private  String pextra1;
    private  String pextra2;
    private  String pextra3;
    private  String pextra4;
    private  String pextra5;
}

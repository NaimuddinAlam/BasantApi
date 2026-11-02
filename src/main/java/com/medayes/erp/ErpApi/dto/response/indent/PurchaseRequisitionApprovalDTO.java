package com.medayes.erp.ErpApi.dto.response.indent;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class PurchaseRequisitionApprovalDTO{
    String prNo;
    String prDate;
    String status;
    String itemName;
    String itemCode;
    String qnt;




    List<PurchaseRequisitionResponseDataDTO> items;
}

package com.medayes.erp.ErpApi.dto.response.billPrint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor

public class BillPrintsDTO {
 private String  COMP_CODE;
    private String CENT_CODE;
    private String BRANCH_NAME;
    private String INVOICE_TYPE_CODE;
    private String INV_NO;
    private String INV_DATE;
    private String SALE_TYPE;
    private String SALE_TYPE_NAME;
    private String BILL_PARTY_CODE;
    private String LEDGER_NAME;
    private String AMOUNT;
}

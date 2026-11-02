package com.medayes.erp.ErpApi.dto.request;

import lombok.Data;

@Data
public class BillPrintRequestDTO {
    private String  pcompcode;
    private String pcentcode;
    private String party_code;
    private String pfrom_date;
    private String pto_date;
    private String pbill_no;
    private String pextra1;
    private String pextra2;
    private String pextra3;
    private String pextra4;
    private String pextra5;
}

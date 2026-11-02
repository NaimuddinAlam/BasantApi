package com.medayes.erp.ErpApi.dto.response;

import lombok.Data;

@Data
public class DocumentDTO {
   private String  documentType;
    private String  attachDesc;
    private String  attachFileName;
    private  String filePermission;


}

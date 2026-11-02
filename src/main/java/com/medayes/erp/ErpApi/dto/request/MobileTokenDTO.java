package com.medayes.erp.ErpApi.dto.request;

import lombok.Data;

@Data
public class MobileTokenDTO {
     String compCode;
     String centCode;
     String empCode;
     String token;
     String type;


}

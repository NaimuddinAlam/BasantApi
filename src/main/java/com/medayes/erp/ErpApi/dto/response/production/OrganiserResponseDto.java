package com.medayes.erp.ErpApi.dto.response.production;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrganiserResponseDto {
    private  String orgName;
    private  String orgCode;
    private  String farmerCode;
    private  String farmerName;
}

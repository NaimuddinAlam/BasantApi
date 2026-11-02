package com.medayes.erp.ErpApi.dto.response.production;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ReasonResponseDto {
    private  String  reasonCode;
    private  String reasonName;
}


package com.medayes.erp.ErpApi.dto.response.production;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class IssueOrgResponseDto {
    private String  orgName;
    private  List<ReportReposeDto> dto;
    private  Double totalQuantity;
    private  Double totalQuantityKg;
}

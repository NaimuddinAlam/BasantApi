package com.medayes.erp.ErpApi.dto.response;

public record AccountAnalysisResponseDto(
      String  comp_code,
      String cent_code,
      String type_code,
      String type_desc,
      String amount
) {
}

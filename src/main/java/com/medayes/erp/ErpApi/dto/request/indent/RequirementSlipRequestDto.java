package com.medayes.erp.ErpApi.dto.request.indent;

public record RequirementSlipRequestDto(
        String compCode,
        String centCode,
        String branchCode,
        String deptNo,
        String rsNo,
        String itemType,
        String item,
        String rsFromDate,
        String rsToDate,
        String dateformat,
        String extra,
        String extra1,
        String extra2,
        String extra3,
        String extra4,
        String extra5
        ) {


}

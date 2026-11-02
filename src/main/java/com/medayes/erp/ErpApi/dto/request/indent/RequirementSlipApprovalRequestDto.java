package com.medayes.erp.ErpApi.dto.request.indent;

public record RequirementSlipApprovalRequestDto(
        String pcompcode,
        String pcenter, String pbranch,
        String pdocno, String pitemtype, String pitemcode,
        String puserid, String papporvalflag, String pappcencelfalg,
        String papprovaltype, String pstatus,
        String pextra1, String pextra2, String pextra3,
        String pextra4, String pextra5
) {
}

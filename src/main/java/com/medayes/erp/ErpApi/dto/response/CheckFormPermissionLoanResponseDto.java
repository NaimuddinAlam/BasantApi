package com.medayes.erp.ErpApi.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CheckFormPermissionLoanResponseDto {

    private List<PermissionData> reportingData = new ArrayList<>();

    @Data
    public static class PermissionData {
        private String reporting;        // Y
        private String reportingValue;   // 0,1,2
        private String reportingName;    // Reporting, SEQ1, SEQ2
    }
}

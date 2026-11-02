package com.medayes.erp.ErpApi.dto.response.tracking;

import java.util.List;

public class EmployeeTrackingWrapperDto {

    private final List<EmployeeTrackingResponseDto> trackingList;
    private final EmployeeTrackingSummaryDto summary;

    public EmployeeTrackingWrapperDto(
            List<EmployeeTrackingResponseDto> trackingList,
            EmployeeTrackingSummaryDto summary) {
        this.trackingList = trackingList;
        this.summary = summary;
    }

    public List<EmployeeTrackingResponseDto> getTrackingList() {
        return trackingList;
    }

    public EmployeeTrackingSummaryDto getSummary() {
        return summary;
    }
}

package com.medayes.erp.ErpApi.services.tracking;

import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;
import com.medayes.erp.ErpApi.dto.response.tracking.EmployeeTrackingRequestDto;

public interface EmployeeTrackingServices {
    TaskMastResponsibleResponseDto<?> getEmployeeTracking(EmployeeTrackingRequestDto trackingRequestDto);
    TaskMastResponsibleResponseDto<?> getEmployee(EmployeeTrackingRequestDto trackingRequestDto);
}

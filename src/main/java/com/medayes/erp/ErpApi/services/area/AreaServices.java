package com.medayes.erp.ErpApi.services.area;

import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;

public interface AreaServices {
    TaskMastResponsibleResponseDto<?> getStateMast();
    TaskMastResponsibleResponseDto<?> getDistrictMast(String stateCode);
    TaskMastResponsibleResponseDto<?> getTaluka(String distCode);
}

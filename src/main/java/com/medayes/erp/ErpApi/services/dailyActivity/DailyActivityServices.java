package com.medayes.erp.ErpApi.services.dailyActivity;

import com.medayes.erp.ErpApi.dto.request.TimeSheetDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;


public interface DailyActivityServices {
    TaskMastResponsibleResponseDto<?> getDailyActivity();
    TaskMastResponsibleResponseDto<?> getClientName(TimeSheetDto mobile);
    TaskMastResponsibleResponseDto<?> getMobileNo(TimeSheetDto clientName);

}

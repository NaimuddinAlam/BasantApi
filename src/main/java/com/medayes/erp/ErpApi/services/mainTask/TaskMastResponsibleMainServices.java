package com.medayes.erp.ErpApi.services.mainTask;


import com.medayes.erp.ErpApi.dto.request.task.TaskMastResponsibleRequestDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponseListDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleTempResponseDto;

import java.util.List;


public interface TaskMastResponsibleMainServices {
    TaskMastResponsibleTempResponseDto saveMainResponsible(TaskMastResponsibleRequestDto taskMastResponsibleTempRequestDto);
    TaskMastResponsibleResponseDto<List<TaskMastResponseListDto>> fetchMain(TaskMastResponsibleRequestDto taskMastResponsibleTempRequestDto);
    TaskMastResponsibleTempResponseDto deleteMain(TaskMastResponsibleRequestDto taskMastResponsibleTempRequestDto);


}

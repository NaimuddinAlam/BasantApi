package com.medayes.erp.ErpApi.services.tempTask;


import com.medayes.erp.ErpApi.dto.request.task.TaskMastResponsibleTempRequestDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleTempResponseDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleTempResponseListDto;

import java.util.List;


public interface TaskMastResponsibleTempServices {
    TaskMastResponsibleTempResponseDto saveResponsible(TaskMastResponsibleTempRequestDto taskMastResponsibleTempRequestDto);
    TaskMastResponsibleTempResponseDto deleteResponsible(TaskMastResponsibleTempRequestDto taskMastResponsibleTempRequestDto);
    TaskMastResponsibleResponseDto<List<TaskMastResponsibleTempResponseListDto>> fetchResponsible(TaskMastResponsibleTempRequestDto taskMastResponsibleTempRequestDto);

}

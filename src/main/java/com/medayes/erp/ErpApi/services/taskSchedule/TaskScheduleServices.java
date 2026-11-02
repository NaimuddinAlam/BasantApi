package com.medayes.erp.ErpApi.services.taskSchedule;

import com.medayes.erp.ErpApi.dto.request.indent.RequirementSlipRequestDto;
import com.medayes.erp.ErpApi.dto.request.task.*;
import com.medayes.erp.ErpApi.dto.response.task.*;

import java.util.List;

public interface TaskScheduleServices {
    TaskMastResponsibleResponseDto< List<TaskSchedulerResponseDto>> bindTaskMast(TaskSchedulerRequestDto schedulerRequestDto);
    TaskMastResponsibleResponseDto< List<TaskLocationResponseDto>> bindLocation(TaskSchedulerRequestDto schedulerRequestDto);
    TaskMastResponsibleResponseDto<List<TaskMastResponsibleTempResponseListDto>> fetchResponsible(TaskScheduleReponsibalRequestDto taskMastResponsibleTempRequestDto);
    TaskMastResponsibleTempResponseDto saveResponsible(TaskScheduleReponsibalRequestDto taskMastResponsibleTempRequestDto);
    TaskMastResponsibleTempResponseDto deleteResponsible(TaskScheduleReponsibalRequestDto taskMastResponsibleTempRequestDto);

    TaskMastResponsibleResponseDto<List<CmsTaskScheduleResponseDto>> fetchCmsSchedule(CmsTaskScheduleRequestDto task);
    TaskMastResponsibleTempResponseDto saveCmsSchedule(CmsTaskScheduleRequestDto task);


    TaskMastResponsibleResponseDto< List<ResponsiblePersonResponseDto>> fetchResponsiblePerson(ResponsiblePersonRequestDto requestDto);


    TaskMastResponsibleResponseDto<List<TaskOptionResponseDto>> fetchCmsOption(CmsTaskScheduleRequestDto task);
    TaskMastResponsibleResponseDto<CheckResponsibleResponseDto> fetchCheckFrom(RequirementSlipRequestDto task);




}

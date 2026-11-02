package com.medayes.erp.ErpApi.repository.taskSchdule;

import com.medayes.erp.ErpApi.dto.request.task.TaskScheduleReponsibalRequestDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleTempResponseListDto;

import java.util.List;
import java.util.Map;

public interface TaskReschedulerRepository {
    List<TaskMastResponsibleTempResponseListDto> fetchResponsiblePerson(TaskScheduleReponsibalRequestDto taskScheduleReponsibalRequestDto);
    Map<String, Object> addResponsiblePerson(TaskScheduleReponsibalRequestDto taskScheduleReponsibalRequestDto);
    Map<String, Object> deleteResponsiblePerson(TaskScheduleReponsibalRequestDto taskScheduleReponsibalRequestDto);

}


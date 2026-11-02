package com.medayes.erp.ErpApi.repository.tempTask;

import com.medayes.erp.ErpApi.dto.request.task.TaskMastResponsibleRequestDto;
import com.medayes.erp.ErpApi.dto.request.task.TaskMastResponsibleTempRequestDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleTempResponseListDto;

import java.util.List;
import java.util.Map;

public interface TaskMastResponsibleTempRepository {
    Map<String, Object> execute(TaskMastResponsibleTempRequestDto req);
    List<TaskMastResponsibleTempResponseListDto> fetch(TaskMastResponsibleTempRequestDto req);
    Map<String, Object> delete(TaskMastResponsibleTempRequestDto req);


}

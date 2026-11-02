package com.medayes.erp.ErpApi.repository.mainTask;

import com.medayes.erp.ErpApi.dto.request.task.TaskMastResponsibleRequestDto;
import com.medayes.erp.ErpApi.dto.request.task.TaskMastResponsibleTempRequestDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponseListDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleTempResponseListDto;

import java.util.List;
import java.util.Map;

public interface TaskMastResponsibleMainRepository {

    Map<String, Object> executeMain(TaskMastResponsibleRequestDto req);

    Map<String, Object> deleteMain(TaskMastResponsibleRequestDto req);
}

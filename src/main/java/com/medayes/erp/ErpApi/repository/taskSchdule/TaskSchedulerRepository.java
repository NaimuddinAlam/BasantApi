package com.medayes.erp.ErpApi.repository.taskSchdule;

import com.medayes.erp.ErpApi.dto.request.task.TaskSchedulerRequestDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskLocationResponseDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskSchedulerResponseDto;

import java.util.List;
import java.util.Map;

public interface TaskSchedulerRepository {
   List<TaskSchedulerResponseDto> bindTaskMast(TaskSchedulerRequestDto schedulerRequestDto);



}

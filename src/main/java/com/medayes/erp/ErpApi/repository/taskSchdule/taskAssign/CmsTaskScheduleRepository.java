package com.medayes.erp.ErpApi.repository.taskSchdule.taskAssign;

import com.medayes.erp.ErpApi.dto.request.task.CmsTaskScheduleRequestDto;
import com.medayes.erp.ErpApi.dto.response.task.CmsTaskScheduleResponseDto;


import java.util.List;
import java.util.Map;

public interface CmsTaskScheduleRepository {
    Map<String, Object> saveCmsTaskSchedule(CmsTaskScheduleRequestDto task);
    List<CmsTaskScheduleResponseDto> fetchCmsTaskSchedule(CmsTaskScheduleRequestDto task);
    List<CmsTaskScheduleResponseDto> fetchCmsTaskSchedulePerson(CmsTaskScheduleRequestDto task);
}

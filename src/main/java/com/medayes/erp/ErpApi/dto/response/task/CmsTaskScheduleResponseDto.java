package com.medayes.erp.ErpApi.dto.response.task;

public record CmsTaskScheduleResponseDto( String taskScheduleCode,
                                          String scheduleDate,
                                          String scheduleTime,
                                          String taskEndDate,
                                          String taskEndTime,
                                          String taskSubject,
                                          String taskName,
                                          String taskLocation,
                                          String taskLocationName,
                                          String callDescription,
                                          String responsiblePerson,
                                          String status, String priority,
                                          String imagePath) {
}

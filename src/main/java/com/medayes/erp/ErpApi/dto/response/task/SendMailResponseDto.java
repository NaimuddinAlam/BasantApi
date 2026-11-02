package com.medayes.erp.ErpApi.dto.response.task;

public record SendMailResponseDto(String SCHEDULE_DATE, String SCHEDULE_TIME,String ALERT_TIME, String ALERT_BEFORE_TIME, String token,String emialId, String taskName, String createdBy, String mobileNo, String taskPriority, String personName, String remake) {
}

package com.medayes.erp.ErpApi.dto.response.task;

public record TaskMastResponseListDto( String COMP_CODE,
                                       String TASK_CODE	,
                                       String TASK_NAME	,
                                       String TASK_NICK,
                                       String DEFAULT_FLAG,
                                       String SEQ_NO,
                                       String REMARKS,
                                       String STATUSNAME) {
}

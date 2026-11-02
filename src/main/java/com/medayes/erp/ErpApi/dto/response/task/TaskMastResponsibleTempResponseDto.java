package com.medayes.erp.ErpApi.dto.response.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskMastResponsibleTempResponseDto {
    private String status;
    private String message;
    private int code;

    public static TaskMastResponsibleTempResponseDto response(String status, String message, int code) {
        return new TaskMastResponsibleTempResponseDto(status, message, code);
    }
    private static TaskMastResponsibleTempResponseDto build(
            String status, String message, int code) {

        return new TaskMastResponsibleTempResponseDto(status, message, code );
    }




}


package com.medayes.erp.ErpApi.controller.task;


import com.medayes.erp.ErpApi.dto.request.task.TaskMastResponsibleTempRequestDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleTempResponseDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleTempResponseListDto;
import com.medayes.erp.ErpApi.services.tempTask.TaskMastResponsibleTempServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/PAYROLL_APP_TEST/api/PayrollSale/")
public class TaskMastResponsibleTempController {
   @Autowired private TaskMastResponsibleTempServices taskMastResponsibleTempServices;
    @PostMapping(value = "taskMastResponsibleTemp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskMastResponsibleTempResponseDto> save(@RequestBody TaskMastResponsibleTempRequestDto req) {
        TaskMastResponsibleTempResponseDto response= taskMastResponsibleTempServices.saveResponsible(req);
        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }
    @PostMapping(value = "taskMastResponsibleTempFetch", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskMastResponsibleResponseDto<List<TaskMastResponsibleTempResponseListDto>>>  fetch(@RequestBody TaskMastResponsibleTempRequestDto req) {
        TaskMastResponsibleResponseDto<List<TaskMastResponsibleTempResponseListDto>>  response= taskMastResponsibleTempServices.fetchResponsible(req);
        return ResponseEntity
                .status(response.code())
                .body(response);
    }

    @PostMapping(value = "taskMastResponsibleTempDelete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskMastResponsibleTempResponseDto> delete(@RequestBody TaskMastResponsibleTempRequestDto req) {
        TaskMastResponsibleTempResponseDto response= taskMastResponsibleTempServices.deleteResponsible(req);
        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }
}

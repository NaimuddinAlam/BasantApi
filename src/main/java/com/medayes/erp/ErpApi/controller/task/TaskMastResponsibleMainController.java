package com.medayes.erp.ErpApi.controller.task;


import com.medayes.erp.ErpApi.dto.request.task.TaskMastResponsibleRequestDto;

import com.medayes.erp.ErpApi.dto.request.task.TaskMastResponsibleTempRequestDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponseListDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleTempResponseDto;

import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleTempResponseListDto;
import com.medayes.erp.ErpApi.services.mainTask.TaskMastResponsibleMainServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/PAYROLL_APP_TEST/api/PayrollSale/")
public class TaskMastResponsibleMainController {
   @Autowired private TaskMastResponsibleMainServices taskMastResponsibleTempServices;
    @PostMapping(value = "cmsTaskMastInUpDet", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskMastResponsibleTempResponseDto> save(@RequestBody TaskMastResponsibleRequestDto req) {
        TaskMastResponsibleTempResponseDto response= taskMastResponsibleTempServices.saveMainResponsible(req);
        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }
    @PostMapping(value = "cmsTaskMastFetch", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskMastResponsibleResponseDto<List<TaskMastResponseListDto>>>  fetch(@RequestBody TaskMastResponsibleRequestDto req) {
        TaskMastResponsibleResponseDto<List<TaskMastResponseListDto>>  response= taskMastResponsibleTempServices.fetchMain(req);
        return ResponseEntity
                .status(response.code())
                .body(response);
    }
    @DeleteMapping(value = "cmsTaskMastDelete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskMastResponsibleTempResponseDto> delete(@RequestBody TaskMastResponsibleRequestDto req) {
        TaskMastResponsibleTempResponseDto response= taskMastResponsibleTempServices.deleteMain(req);
        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }

}

package com.medayes.erp.ErpApi.controller.task;


import com.medayes.erp.ErpApi.dto.request.indent.RequirementSlipRequestDto;
import com.medayes.erp.ErpApi.dto.request.task.CmsTaskScheduleRequestDto;

import com.medayes.erp.ErpApi.dto.request.task.ResponsiblePersonRequestDto;
import com.medayes.erp.ErpApi.dto.request.task.TaskScheduleReponsibalRequestDto;
import com.medayes.erp.ErpApi.dto.request.task.TaskSchedulerRequestDto;
import com.medayes.erp.ErpApi.dto.response.task.*;
import com.medayes.erp.ErpApi.services.taskSchedule.TaskScheduleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/PAYROLL_APP_TEST/api/PayrollSale/")
public class TaskSchedulerController {

    @Autowired
    private TaskScheduleServices taskScheduleServices;

    @PostMapping(value = "cmsTaskMast", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskMastResponsibleResponseDto<List<TaskSchedulerResponseDto>>> fetch(@RequestBody TaskSchedulerRequestDto req) {
        TaskMastResponsibleResponseDto<List<TaskSchedulerResponseDto>>  response= taskScheduleServices.bindTaskMast(req);
        return ResponseEntity
                .status(response.code())
                .body(response);
    }
    @PostMapping(value = "cmsTaskLocation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskMastResponsibleResponseDto<List<TaskLocationResponseDto>>> fetchLocation(@RequestBody TaskSchedulerRequestDto req) {
        TaskMastResponsibleResponseDto<List<TaskLocationResponseDto>>  response= taskScheduleServices.bindLocation(req);
        return ResponseEntity
                .status(response.code())
                .body(response);
    }
    @PostMapping(value = "cmsTaskResponsiblePerson", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskMastResponsibleResponseDto<List<TaskMastResponsibleTempResponseListDto>>> fetchResponsiblePerson(@RequestBody TaskScheduleReponsibalRequestDto req) {
        TaskMastResponsibleResponseDto<List<TaskMastResponsibleTempResponseListDto>>  response= taskScheduleServices.fetchResponsible(req);
        return ResponseEntity
                .status(response.code())
                .body(response);
    }
    @DeleteMapping(value = "cmsTaskResponsiblePersonDelete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskMastResponsibleTempResponseDto> delete(@RequestBody TaskScheduleReponsibalRequestDto req) {
        TaskMastResponsibleTempResponseDto response= taskScheduleServices.deleteResponsible(req);
        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }
    @PostMapping(value = "cmsTaskResponsiblePersonSave", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskMastResponsibleTempResponseDto> save(@RequestBody TaskScheduleReponsibalRequestDto req) {
        TaskMastResponsibleTempResponseDto response= taskScheduleServices.saveResponsible(req);
        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }


    @PostMapping(value = "cmsScheduleSave", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskMastResponsibleTempResponseDto> save(@RequestBody CmsTaskScheduleRequestDto req) {
        TaskMastResponsibleTempResponseDto response= taskScheduleServices.saveCmsSchedule(req);
        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }
    @PostMapping(value = "cmsScheduleFetch", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskMastResponsibleResponseDto<List<CmsTaskScheduleResponseDto>>> cmsScheduleFetch(@RequestBody CmsTaskScheduleRequestDto req) {
        TaskMastResponsibleResponseDto<List<CmsTaskScheduleResponseDto>>  response= taskScheduleServices.fetchCmsSchedule(req);
        return ResponseEntity
                .status(response.code())
                .body(response);
    }

    @PostMapping(value = "fetchResponsiblePerson", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskMastResponsibleResponseDto<List<ResponsiblePersonResponseDto>>> fetchResponsiblePerson(@RequestBody ResponsiblePersonRequestDto req) {
        TaskMastResponsibleResponseDto<List<ResponsiblePersonResponseDto>>  response= taskScheduleServices.fetchResponsiblePerson(req);
        return ResponseEntity
                .status(response.code())
                .body(response);
    }
    @PostMapping(value = "fetchCmsOption", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskMastResponsibleResponseDto<List<TaskOptionResponseDto>>> fetchResponsiblePerson(@RequestBody CmsTaskScheduleRequestDto req) {
        TaskMastResponsibleResponseDto<List<TaskOptionResponseDto>>  response= taskScheduleServices.fetchCmsOption(req);
        return ResponseEntity
                .status(response.code())
                .body(response);
    }
    @PostMapping(value = "fromPermission", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskMastResponsibleResponseDto<CheckResponsibleResponseDto>> fetchCheckFrom(@RequestBody RequirementSlipRequestDto req) {
        TaskMastResponsibleResponseDto<CheckResponsibleResponseDto>  response= taskScheduleServices.fetchCheckFrom(req);
        return ResponseEntity
                .status(response.code())
                .body(response);
    }
}

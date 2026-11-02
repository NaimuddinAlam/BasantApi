package com.medayes.erp.ErpApi.controller.task;


import com.medayes.erp.ErpApi.dto.request.TimeSheetDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;
import com.medayes.erp.ErpApi.services.dailyActivity.DailyActivityServices;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/PAYROLL_APP_TEST/api/PayrollSale/")
public class DailyActivityController {

    private  final DailyActivityServices dailyActivityServices;

    public DailyActivityController(DailyActivityServices dailyActivityServices) {
        this.dailyActivityServices = dailyActivityServices;
    }

    @GetMapping("dailyActivity")
    public ResponseEntity<TaskMastResponsibleResponseDto<?>> fetch() {
        TaskMastResponsibleResponseDto<?>  response= dailyActivityServices.getDailyActivity();
        return ResponseEntity
                .status(response.code())
                .body(response);
    }
    @PostMapping(value ="fetchClientName", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskMastResponsibleResponseDto<?>> fetchClientName(@RequestBody TimeSheetDto mobile) {
        TaskMastResponsibleResponseDto<?>  response= dailyActivityServices.getClientName(mobile);
        return ResponseEntity
                .status(response.code())
                .body(response);
    }
    @PostMapping(value ="fetchMobile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskMastResponsibleResponseDto<?>> fetchMobile(@RequestBody TimeSheetDto mobile) {
        TaskMastResponsibleResponseDto<?>  response= dailyActivityServices.getMobileNo(mobile);
        return ResponseEntity
                .status(response.code())
                .body(response);
    }
}

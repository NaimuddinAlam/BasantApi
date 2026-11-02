package com.medayes.erp.ErpApi.controller.mobileversion;


import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;

import com.medayes.erp.ErpApi.services.mobileversion.MobileVersionServices;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/PAYROLL_APP_TEST/api/PayrollSale/")
public class MobileVersionController {

    private  final MobileVersionServices mobileVersionServices;

    public MobileVersionController(MobileVersionServices dailyActivityServices) {
        this.mobileVersionServices = dailyActivityServices;
    }

    @GetMapping("mobileVersion")
    public ResponseEntity<TaskMastResponsibleResponseDto<?>> fetch() {
        TaskMastResponsibleResponseDto<?>  response= mobileVersionServices.getMobileVersion();
        return ResponseEntity
                .status(response.code())
                .body(response);
    }

}
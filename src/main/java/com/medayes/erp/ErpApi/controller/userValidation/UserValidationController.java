package com.medayes.erp.ErpApi.controller.userValidation;

import com.medayes.erp.ErpApi.dto.request.UserValidationRequest;

import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleTempResponseDto;


import com.medayes.erp.ErpApi.services.userValidation.MobileValidationServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
@RequestMapping("/PAYROLL_APP_TEST/api/PayrollSale/")
public class UserValidationController {

    private final MobileValidationServices mobileValidationServices;

    @PostMapping(value = "mobileValidation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskMastResponsibleTempResponseDto> mobileValidation(@RequestBody UserValidationRequest req) {
        TaskMastResponsibleTempResponseDto response = mobileValidationServices.mobileValidation(req);
        return ResponseEntity
                .status(response.getCode()).body(response);
    }

    @PostMapping(value = "employeeInactive", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskMastResponsibleTempResponseDto> employeeInactive(@RequestBody UserValidationRequest req) {
        TaskMastResponsibleTempResponseDto response = mobileValidationServices.empInactive(req);
        return ResponseEntity
                .status(response.getCode()).body(response);
    }

    }

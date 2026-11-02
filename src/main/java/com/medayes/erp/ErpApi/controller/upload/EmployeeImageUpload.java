package com.medayes.erp.ErpApi.controller.upload;


import com.medayes.erp.ErpApi.dto.request.AccountAnalysisRequestDto;
import com.medayes.erp.ErpApi.dto.request.sale.SalePermissionRequestDto;
import com.medayes.erp.ErpApi.dto.request.upload.EmployeeUploadDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;
import com.medayes.erp.ErpApi.services.graph.AccountAnalysisServices;
import com.medayes.erp.ErpApi.services.upload.EmployeeUploadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/PAYROLL_APP_TEST/api/PayrollSale/")
public class EmployeeImageUpload {
    @Autowired
    private EmployeeUploadService employeeUploadService;
    @Autowired
    private AccountAnalysisServices services;



    @PostMapping(value = "upload/profile", consumes =MediaType.MULTIPART_FORM_DATA_VALUE )
    public ResponseEntity<TaskMastResponsibleResponseDto<?>> mobileValidation(@ModelAttribute EmployeeUploadDto employeeUploadDto) {
         TaskMastResponsibleResponseDto<?> response = employeeUploadService.employeeUpload(employeeUploadDto);
        return ResponseEntity
                .status(response.code()).body(response);
    }

    @PostMapping(value = "account/analysis")
    public ResponseEntity<TaskMastResponsibleResponseDto<?>> analysis(@RequestBody AccountAnalysisRequestDto employeeUploadDto) {
        TaskMastResponsibleResponseDto<?> response = services.accountAnalysis(employeeUploadDto);
        return ResponseEntity
                .status(response.code()).body(response);
    }
    @PostMapping(value = "employee/resignation")
    public ResponseEntity<TaskMastResponsibleResponseDto<?>> employeeResignation(@RequestBody EmployeeUploadDto employeeUploadDto) {
        TaskMastResponsibleResponseDto<?> response = employeeUploadService.employeeResignation(employeeUploadDto);
        return ResponseEntity
                .status(response.code()).body(response);
    }
    @PostMapping(value = "bannerMast")
    public ResponseEntity<TaskMastResponsibleResponseDto<?>> bannerMast(@RequestBody EmployeeUploadDto employeeUploadDto) {
        TaskMastResponsibleResponseDto<?> response = employeeUploadService.getBannerMast(employeeUploadDto);
        return ResponseEntity
                .status(response.code()).body(response);
    }
    @PostMapping(value = "misUrl")
    public ResponseEntity<TaskMastResponsibleResponseDto<?>> misUrl(@RequestBody @Valid  EmployeeUploadDto  employeeUploadDto) {
        TaskMastResponsibleResponseDto<?> response = employeeUploadService.getMisUrl(employeeUploadDto);
        return ResponseEntity
                .status(response.code()).body(response);
    }
    @PostMapping("misPermission")
    public ResponseEntity<?> misPermission(@RequestBody SalePermissionRequestDto permissionRequestDto)

    {
        TaskMastResponsibleResponseDto<?> responseDto=employeeUploadService.misPermission(permissionRequestDto);
        return  ResponseEntity.status(responseDto.code()).body(responseDto);
    }


}
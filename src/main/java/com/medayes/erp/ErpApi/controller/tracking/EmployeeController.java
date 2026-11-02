package com.medayes.erp.ErpApi.controller.tracking;

import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;
import com.medayes.erp.ErpApi.dto.response.tracking.EmployeeTrackingRequestDto;
import com.medayes.erp.ErpApi.services.tracking.EmployeeTrackingServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/PAYROLL_APP_TEST/api/PayrollSale/")
public class EmployeeController {
    private  final EmployeeTrackingServices employeeTrackingServices;

    public EmployeeController(EmployeeTrackingServices employeeTrackingServices) {
        this.employeeTrackingServices = employeeTrackingServices;
    }
    @PostMapping("tracking")
    public ResponseEntity<TaskMastResponsibleResponseDto<?>> getTracking(@RequestBody EmployeeTrackingRequestDto requestDto)
    {
      TaskMastResponsibleResponseDto<?> responseDto=   employeeTrackingServices.getEmployeeTracking(requestDto);
       return   ResponseEntity.status(responseDto.code()).body(responseDto);
    }
    @PostMapping("reportingEmp")
    public ResponseEntity<TaskMastResponsibleResponseDto<?>> getEmp(@RequestBody EmployeeTrackingRequestDto requestDto)
    {
        TaskMastResponsibleResponseDto<?> responseDto=   employeeTrackingServices.getEmployee(requestDto);
        return   ResponseEntity.status(responseDto.code()).body(responseDto);
    }
}

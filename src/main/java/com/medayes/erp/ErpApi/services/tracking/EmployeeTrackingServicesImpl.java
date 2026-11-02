package com.medayes.erp.ErpApi.services.tracking;

import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;
import com.medayes.erp.ErpApi.dto.response.tracking.EmployeeTrackingRequestDto;
import com.medayes.erp.ErpApi.dto.response.tracking.EmployeeTrackingResponseDto;
import com.medayes.erp.ErpApi.dto.response.tracking.EmployeeTrackingWrapperDto;
import com.medayes.erp.ErpApi.dto.response.tracking.ReportingEmployeeResponseDTO;
import com.medayes.erp.ErpApi.exeption.BusinessException;
import com.medayes.erp.ErpApi.repository.tracking.EmployeeTrackingRepository;
import com.medayes.erp.ErpApi.repository.tracking.ReportingEmployee;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class EmployeeTrackingServicesImpl  implements  EmployeeTrackingServices{
    private final EmployeeTrackingRepository employeeTrackingRepository;
    private final ReportingEmployee reportingEmployee;

    public EmployeeTrackingServicesImpl(EmployeeTrackingRepository employeeTrackingRepository,ReportingEmployee reportingEmployee) {
        this.employeeTrackingRepository = employeeTrackingRepository;
        this.reportingEmployee=reportingEmployee;
    }

    @Override
    public TaskMastResponsibleResponseDto<?> getEmployeeTracking(EmployeeTrackingRequestDto trackingRequestDto) {
         try {
             EmployeeTrackingWrapperDto  result= employeeTrackingRepository.getEmpTracking(trackingRequestDto);
             if (result == null ) {
                 return new TaskMastResponsibleResponseDto<>(
                         "false",
                         "No data found",
                         HttpStatus.BAD_REQUEST.value(),
                         Collections.emptyList()
                 );
             }

             return new TaskMastResponsibleResponseDto<>(
                     "true",
                     "Data fetch successful",
                     HttpStatus.OK.value(),
                     result
             );

         } catch (NullPointerException ex) {
             // MISSING REQUIRED INPUTS
             return new TaskMastResponsibleResponseDto<>(
                     "false",
                     "Null Value Error: " + ex.getMessage(),
                     HttpStatus.BAD_REQUEST.value(), null);

         } catch (IllegalArgumentException ex) {
             // INVALID INPUT FORMAT
             return new TaskMastResponsibleResponseDto<>(
                     "false",
                     "Invalid Input: " + ex.getMessage(),
                     HttpStatus.BAD_REQUEST.value(), null
             );
         } catch (Exception ex) {
             return new TaskMastResponsibleResponseDto<>(
                     "false",
                     "Unexpected Error: " + ex.getMessage(),
                     HttpStatus.INTERNAL_SERVER_ERROR.value(),
                     null
             );
         }



    }

    @Override
    public TaskMastResponsibleResponseDto<?> getEmployee(EmployeeTrackingRequestDto trackingRequestDto) {
        try {
            List<ReportingEmployeeResponseDTO> result= reportingEmployee.getEmp(trackingRequestDto);
            if (result == null ) {
                return new TaskMastResponsibleResponseDto<>(
                        "false",
                        "No data found",
                        HttpStatus.BAD_REQUEST.value(),
                        Collections.emptyList()
                );
            }

            return new TaskMastResponsibleResponseDto<>(
                    "true",
                    "Data fetch successful",
                    HttpStatus.OK.value(),
                    result
            );

        } catch (NullPointerException ex) {
            // MISSING REQUIRED INPUTS
            return new TaskMastResponsibleResponseDto<>(
                    "false",
                    "Null Value Error: " + ex.getMessage(),
                    HttpStatus.BAD_REQUEST.value(), null);

        } catch (IllegalArgumentException ex) {
            // INVALID INPUT FORMAT
            return new TaskMastResponsibleResponseDto<>(
                    "false",
                    "Invalid Input: " + ex.getMessage(),
                    HttpStatus.BAD_REQUEST.value(), null
            );
        } catch (Exception ex) {
            return new TaskMastResponsibleResponseDto<>(
                    "false",
                    "Unexpected Error: " + ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    null
            );
        }
    }
}

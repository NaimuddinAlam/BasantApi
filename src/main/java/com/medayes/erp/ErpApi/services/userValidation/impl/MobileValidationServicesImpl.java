package com.medayes.erp.ErpApi.services.userValidation.impl;

import com.medayes.erp.ErpApi.dto.request.UserValidationRequest;
import com.medayes.erp.ErpApi.dto.response.UserValidationResponse;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleTempResponseDto;
import com.medayes.erp.ErpApi.entity.AppEmployeeInactiveEntity;
import com.medayes.erp.ErpApi.exeption.ResourceNotFoundException;
import com.medayes.erp.ErpApi.repository.userValidation.AppEmployeeInactiveRepository;
import com.medayes.erp.ErpApi.repository.userValidation.MobileValidation;
import com.medayes.erp.ErpApi.services.userValidation.MobileValidationServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class MobileValidationServicesImpl implements MobileValidationServices {


    private final MobileValidation mobileValidation;
    private final AppEmployeeInactiveRepository appEmployeeInactiveRepository;

    @Override
    public TaskMastResponsibleTempResponseDto mobileValidation(UserValidationRequest request) {

        try {

            UserValidationResponse result = mobileValidation.mobileValidation(request);

            if (result.res() == null || result.res().isEmpty()) {
                return new TaskMastResponsibleTempResponseDto(
                        "false",
                        "Stored procedure returned no data",
                        HttpStatus.INTERNAL_SERVER_ERROR.value()
                );
            }

            if( result.res().equalsIgnoreCase("Y"))
            {
                return new TaskMastResponsibleTempResponseDto(
                        "true",
                        result.res(),
                        HttpStatus.OK.value()
                );
            }else {
                return new TaskMastResponsibleTempResponseDto(
                        "false",
                        result.res(),
                        HttpStatus.NOT_FOUND.value()
                );
            }



        } catch (NullPointerException ex) {
            // MISSING REQUIRED INPUTS
            return new TaskMastResponsibleTempResponseDto(
                    "false",
                    "Null Value Error: " + ex.getMessage(),
                    HttpStatus.BAD_REQUEST.value()
            );

        } catch (IllegalArgumentException ex) {
            // INVALID INPUT FORMAT
            return new TaskMastResponsibleTempResponseDto(
                    "false",
                    "Invalid Input: " + ex.getMessage(),
                    HttpStatus.BAD_REQUEST.value()
            );

        } catch (Exception ex) {
            // UNKNOWN ISSUES
            return new TaskMastResponsibleTempResponseDto(
                    "false",
                    "Unexpected Error: " + ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
        }
    }

    @Override
    public TaskMastResponsibleTempResponseDto empInactive(UserValidationRequest request) {
        try {
            AppEmployeeInactiveEntity response = appEmployeeInactiveRepository
                    .findByEmpCodeAndInactiveDate(request.empCode(), request.inactiveDate())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

            boolean isMatch = response.getInactiveDate().isEqual(request.inactiveDate());

            return new TaskMastResponsibleTempResponseDto(
                    String.valueOf(isMatch),
                    "Y",
                    HttpStatus.OK.value()
            );

        } catch (ResourceNotFoundException ex) {
            return new TaskMastResponsibleTempResponseDto(
                    "false",
                    ex.getMessage(),
                    HttpStatus.NOT_FOUND.value()
            );
        } catch (Exception ex) {
            return new TaskMastResponsibleTempResponseDto(
                    "false",
                    "Unexpected error occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
        }
    }
}

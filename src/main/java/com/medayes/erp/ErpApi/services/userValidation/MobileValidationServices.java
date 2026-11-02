package com.medayes.erp.ErpApi.services.userValidation;

import com.medayes.erp.ErpApi.dto.request.UserValidationRequest;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleTempResponseDto;



public interface MobileValidationServices {
    TaskMastResponsibleTempResponseDto mobileValidation(UserValidationRequest request);
    TaskMastResponsibleTempResponseDto empInactive(UserValidationRequest request);
}

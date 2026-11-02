package com.medayes.erp.ErpApi.services.salary;

import com.medayes.erp.ErpApi.dto.request.salary.SalaryProcessRequest;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;

public interface SalaryServices {
    TaskMastResponsibleResponseDto<?> salaryProcess(SalaryProcessRequest processRequest);
}


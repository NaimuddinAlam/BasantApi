package com.medayes.erp.ErpApi.services.salary;


import com.medayes.erp.ErpApi.dto.request.salary.SalaryProcessRequest;


import com.medayes.erp.ErpApi.dto.response.salary.SalaryResponse;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;

import com.medayes.erp.ErpApi.exeption.BusinessException;
import com.medayes.erp.ErpApi.repository.salary.SalaryRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class SalaryServiceImpl implements SalaryServices {

    private final SalaryRepository salaryRepository;

    @Override
    public TaskMastResponsibleResponseDto<?> salaryProcess(
            SalaryProcessRequest processRequest) {

        SalaryResponse salaryEmployeeResponse =
                salaryRepository.apiCalling(processRequest);

        if (salaryEmployeeResponse == null) {
            throw new BusinessException("No data found");
        }

        return new TaskMastResponsibleResponseDto<>(
                "true",
                "Data fetch successful",
                HttpStatus.OK.value(),
                salaryEmployeeResponse
        );
    }
}

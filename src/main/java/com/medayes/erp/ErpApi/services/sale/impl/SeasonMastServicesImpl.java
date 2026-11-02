package com.medayes.erp.ErpApi.services.sale.impl;

import com.medayes.erp.ErpApi.dto.request.sale.SalePermissionRequestDto;
import com.medayes.erp.ErpApi.dto.request.sale.SeasonMastRequestDto;
import com.medayes.erp.ErpApi.dto.response.sale.SalePermissionResponseDto;
import com.medayes.erp.ErpApi.dto.response.sale.SeasonMastResponseDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;
import com.medayes.erp.ErpApi.exeption.BusinessException;
import com.medayes.erp.ErpApi.exeption.DatabaseException;
import com.medayes.erp.ErpApi.repository.sale.SeasonMastRepository;
import com.medayes.erp.ErpApi.services.sale.SeasonMastServices;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeasonMastServicesImpl  implements SeasonMastServices {

    private final SeasonMastRepository seasonMastRepository;

    public SeasonMastServicesImpl(SeasonMastRepository seasonMastRepository) {
        this.seasonMastRepository = seasonMastRepository;
    }

    @Override
    public TaskMastResponsibleResponseDto<?> fetchSeason(SeasonMastRequestDto seasonMastRequestDto) {
        try {
            List<SeasonMastResponseDto> response= seasonMastRepository.fetchSeasonMast(seasonMastRequestDto);
            if( response==null || response.isEmpty())
            {
                throw  new BusinessException("No season data found");
            }
            return  new TaskMastResponsibleResponseDto<>("true",
                    "Data fetch successful",
                    HttpStatus.OK.value(),
                    response);
        }catch (DataAccessException ex)
        {
            throw new DatabaseException(
                    "Database error occurred: " + ex.getMostSpecificCause().getMessage(), ex
            );
        }

    }

    @Override
    public TaskMastResponsibleResponseDto<?> salePermission(SalePermissionRequestDto salePermissionRequestDto) {
        try {
            SalePermissionResponseDto response= seasonMastRepository.approvalId(salePermissionRequestDto);
            if( response==null )
            {
                throw  new BusinessException("No Approval id found");
            }
            return  new TaskMastResponsibleResponseDto<>("true",
                    "Data fetch successful",
                    HttpStatus.OK.value(),
                    response);
        }catch (DataAccessException ex)
        {
            throw new DatabaseException(
                    "Database error occurred: " + ex.getMostSpecificCause().getMessage(), ex
            );
        }
    }
}

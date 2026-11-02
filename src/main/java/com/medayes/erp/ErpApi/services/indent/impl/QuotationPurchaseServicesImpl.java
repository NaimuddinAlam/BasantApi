package com.medayes.erp.ErpApi.services.indent.impl;

import com.medayes.erp.ErpApi.dto.request.indent.QuotationRequisitionRequestDTO;
import com.medayes.erp.ErpApi.dto.response.indent.QuotationRequisitionDTO;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;
import com.medayes.erp.ErpApi.exeption.BusinessException;
import com.medayes.erp.ErpApi.exeption.DatabaseException;
import com.medayes.erp.ErpApi.repository.indent.PurchaseRequisitionQuotationRepository;
import com.medayes.erp.ErpApi.services.indent.QuotationPurchaseServices;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuotationPurchaseServicesImpl implements QuotationPurchaseServices {
    private  final PurchaseRequisitionQuotationRepository repository;
    @Override
    public TaskMastResponsibleResponseDto<List<QuotationRequisitionDTO>> getQuotationRequisition(QuotationRequisitionRequestDTO requestDto) {

        try {
            List<QuotationRequisitionDTO> result = repository.response(requestDto);

            if (result == null || result.isEmpty()) {
                throw new BusinessException(" No data found");

            }

            return new TaskMastResponsibleResponseDto<>("true",
                    "Data fetch successful",
                    HttpStatus.OK.value(),
                    result);
        } catch (DataAccessException ex) {
            throw new DatabaseException(
                    "Database error occurred: " + ex.getMostSpecificCause().getMessage(), ex
            );


        }
    }
}

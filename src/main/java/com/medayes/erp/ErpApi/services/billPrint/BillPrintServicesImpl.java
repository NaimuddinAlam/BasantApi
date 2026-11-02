package com.medayes.erp.ErpApi.services.billPrint;


import com.medayes.erp.ErpApi.dto.request.BillPrintRequestDTO;
import com.medayes.erp.ErpApi.dto.response.billPrint.BillPrintDTO;
import com.medayes.erp.ErpApi.dto.response.billPrint.BillPrintsDTO;
import com.medayes.erp.ErpApi.dto.response.sale.SeasonMastResponseDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;
import com.medayes.erp.ErpApi.entity.BillPrintEntity;
import com.medayes.erp.ErpApi.exeption.BusinessException;
import com.medayes.erp.ErpApi.exeption.DatabaseException;
import com.medayes.erp.ErpApi.repository.billPrint.BillPrintInvoiceNoRepository;
import com.medayes.erp.ErpApi.repository.billPrint.BillPrintRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillPrintServicesImpl implements BillPrintServices{
    private  final BillPrintInvoiceNoRepository billPrintInvoiceNoRepository;
    private  final BillPrintRepository billPrintRepository;
    private  final ModelMapper mapper;
    @Override
    public TaskMastResponsibleResponseDto<?> getBillPrint() {
     List<BillPrintEntity> billPrintEntities=  billPrintInvoiceNoRepository.findAll();
        if(billPrintEntities.isEmpty())
        {
            throw  new BusinessException("No dta found");
        }
        List<BillPrintDTO> stateMastDTOS=billPrintEntities.stream()
                .map(billPrint -> mapper.map(billPrint, BillPrintDTO.class) ).toList();

        return new TaskMastResponsibleResponseDto<>(
                "true",
                "Data fetch successful",
                HttpStatus.OK.value(),
                stateMastDTOS
        );
    }

    @Override
    public TaskMastResponsibleResponseDto<?> getBillPrintInvoice(BillPrintRequestDTO billPrintRequestDTO) {

        try {
            List<BillPrintsDTO> response= billPrintRepository.fetchBillInvoice(billPrintRequestDTO);
            if( response==null || response.isEmpty())
            {
                throw  new BusinessException("No bill data found");
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

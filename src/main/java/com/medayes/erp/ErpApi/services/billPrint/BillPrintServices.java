package com.medayes.erp.ErpApi.services.billPrint;

import com.medayes.erp.ErpApi.dto.request.BillPrintRequestDTO;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;

public interface BillPrintServices {
    TaskMastResponsibleResponseDto<?> getBillPrint();
    TaskMastResponsibleResponseDto<?> getBillPrintInvoice(BillPrintRequestDTO billPrintRequestDTO);
}

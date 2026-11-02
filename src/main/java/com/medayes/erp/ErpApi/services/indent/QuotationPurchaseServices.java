package com.medayes.erp.ErpApi.services.indent;


import com.medayes.erp.ErpApi.dto.request.indent.QuotationRequisitionRequestDTO;
import com.medayes.erp.ErpApi.dto.response.indent.QuotationRequisitionDTO;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;

import java.util.List;

public interface QuotationPurchaseServices {
    TaskMastResponsibleResponseDto<List<QuotationRequisitionDTO>> getQuotationRequisition(QuotationRequisitionRequestDTO requestDto);
}

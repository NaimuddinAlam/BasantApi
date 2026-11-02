package com.medayes.erp.ErpApi.services.purchase;

import com.medayes.erp.ErpApi.dto.request.po.PurchaseOrderRequestDTO;
import com.medayes.erp.ErpApi.dto.request.po.PurchaseRequest;
import com.medayes.erp.ErpApi.dto.response.op.*;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;

import java.util.List;

public interface PurchaseFetchServices {
    TaskMastResponsibleResponseDto<List<PurchaseOrderResponseDto>> getPurchaseFetch(PurchaseRequest purchaseRequest);
    TaskMastResponsibleResponseDto<List<PurchaseOrderItemDto>> getPurchaseItemFetch(PurchaseRequest purchaseRequest);
    TaskMastResponsibleResponseDto<List<QuotationDto>> getQuotation(PurchaseRequest purchaseRequest);
    TaskMastResponsibleResponseDto<List<PurchaseOrderApprovalDTO>> savePurchaseOrder(PurchaseOrderRequestDTO purchaseRequest);
}

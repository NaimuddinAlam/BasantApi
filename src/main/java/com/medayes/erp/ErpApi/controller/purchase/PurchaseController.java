package com.medayes.erp.ErpApi.controller.purchase;

import com.medayes.erp.ErpApi.dto.request.po.PurchaseOrderRequestDTO;
import com.medayes.erp.ErpApi.dto.request.po.PurchaseRequest;
import com.medayes.erp.ErpApi.dto.response.op.PurchaseOrderApprovalDTO;
import com.medayes.erp.ErpApi.dto.response.op.PurchaseOrderItemDto;
import com.medayes.erp.ErpApi.dto.response.op.PurchaseOrderResponseDto;
import com.medayes.erp.ErpApi.dto.response.op.QuotationDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;
import com.medayes.erp.ErpApi.services.purchase.PurchaseFetchServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/PAYROLL_APP_TEST/api/PayrollSale/")
public class PurchaseController {
    @Autowired
    private PurchaseFetchServices purchaseFetchServices;

    @PostMapping("/purchaseOrderFetch")
    public ResponseEntity<TaskMastResponsibleResponseDto<List<PurchaseOrderResponseDto>>>fetch(@RequestBody PurchaseRequest purchaseRequest)
    {
        TaskMastResponsibleResponseDto<List<PurchaseOrderResponseDto>>  responseDto=  purchaseFetchServices.getPurchaseFetch(purchaseRequest);
        return  ResponseEntity.status(responseDto.code()).body(responseDto);

    }
    @PostMapping("/purchaseOrderItem")
    public ResponseEntity<TaskMastResponsibleResponseDto<List<PurchaseOrderItemDto>>>purchaseItem(@RequestBody PurchaseRequest purchaseRequest)
    {
        TaskMastResponsibleResponseDto<List<PurchaseOrderItemDto>>  responseDto=  purchaseFetchServices.getPurchaseItemFetch(purchaseRequest);
        return  ResponseEntity.status(responseDto.code()).body(responseDto);

    }
    @PostMapping("/purchaseQuotation")
    public ResponseEntity<TaskMastResponsibleResponseDto<List<QuotationDto>>>purchaseQuotation(@RequestBody PurchaseRequest purchaseRequest)
    {
        TaskMastResponsibleResponseDto<List<QuotationDto>>  responseDto=  purchaseFetchServices.getQuotation(purchaseRequest);
        return  ResponseEntity.status(responseDto.code()).body(responseDto);

    }
    @PostMapping("/purchaseOrderApproval")
    public ResponseEntity<TaskMastResponsibleResponseDto<List<PurchaseOrderApprovalDTO>>>purchaseOrderApproval(@RequestBody PurchaseOrderRequestDTO purchaseRequest)
    {
        TaskMastResponsibleResponseDto<List<PurchaseOrderApprovalDTO>>  responseDto=  purchaseFetchServices.savePurchaseOrder(purchaseRequest);
        return  ResponseEntity.status(responseDto.code()).body(responseDto);

    }


}

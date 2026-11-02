package com.medayes.erp.ErpApi.repository.POapproval;

import com.medayes.erp.ErpApi.dto.request.po.PurchaseOrderRequestDTO;
import com.medayes.erp.ErpApi.dto.request.po.PurchaseRequest;
import com.medayes.erp.ErpApi.dto.response.op.PurchaseOrderApprovalDTO;
import com.medayes.erp.ErpApi.dto.response.op.PurchaseOrderItemResponse;
import com.medayes.erp.ErpApi.dto.response.op.PurchaseOrderResponse;
import com.medayes.erp.ErpApi.dto.response.op.QuotationResponse;

import java.util.List;

public interface PurchaseOrderFetchRepository {
    List<PurchaseOrderResponse> purchaseFetch(PurchaseRequest purchaseRequest);
    List<PurchaseOrderItemResponse> purchaseItemFetch(PurchaseRequest purchaseRequest);
    List<QuotationResponse> quotationFetch(PurchaseRequest purchaseRequest);
    List<PurchaseOrderApprovalDTO> savePurchaseOrder(PurchaseOrderRequestDTO purchaseOrderRequestDTO);
}

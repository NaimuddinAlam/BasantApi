package com.medayes.erp.ErpApi.repository.indent;

import com.medayes.erp.ErpApi.dto.request.indent.PendingPurchaseRequisitionRequestDTO;
import com.medayes.erp.ErpApi.dto.request.indent.PendingPurchaseRequisitionRequestStatusDTO;
import com.medayes.erp.ErpApi.dto.response.indent.*;

import java.util.List;

public interface PurchaseRequisitionRepositoryServices {
    List<PendingPurchaseRequisitionDTO> response(PendingPurchaseRequisitionRequestDTO dto);
    String savePRDETINSUPDDEL(IssueRequestDTO dto);
    String savePRHDRINSUPDDEL(IssueHeaderRequestDTO dto);
    List<PurchaseRequisitionResponseDTO> purchaseRequisitionStatus(PendingPurchaseRequisitionRequestStatusDTO dto);
    String savePurchaseRequisitionApproval(PendingPurchaseRequisitionRequestStatusDTO dto);


}

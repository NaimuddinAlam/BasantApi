package com.medayes.erp.ErpApi.services.indent;

import com.medayes.erp.ErpApi.dto.request.indent.PendingPurchaseRequisitionRequestDTO;
import com.medayes.erp.ErpApi.dto.request.indent.PendingPurchaseRequisitionRequestStatusDTO;
import com.medayes.erp.ErpApi.dto.request.indent.RequirementSlipApprovalRequestDto;
import com.medayes.erp.ErpApi.dto.request.indent.RequirementSlipRequestDto;
import com.medayes.erp.ErpApi.dto.response.indent.*;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleTempResponseDto;

import java.util.List;

public interface IndentServices {
    TaskMastResponsibleResponseDto<List<RequirementSlipDto>> getRequirementSlip(RequirementSlipRequestDto requestDto);
    TaskMastResponsibleResponseDto<List<RequirementSlipReposeDto>> getRequirementApprovalSlip(RequirementSlipRequestDto requestDto);
    TaskMastResponsibleResponseDto<List<PermissionsResponseDto>> getRequirementPermission(RequirementSlipRequestDto requestDto);
    TaskMastResponsibleTempResponseDto requirementApprovalSlip(RequirementSlipApprovalRequestDto requestDto);
    TaskMastResponsibleResponseDto<List<PendingPurchaseRequisitionResponseDTO>> getPendingPurchaseRequisition(PendingPurchaseRequisitionRequestDTO requestDto);

    TaskMastResponsibleTempResponseDto saveInDet(IssueRequestDTO dto);
    TaskMastResponsibleTempResponseDto saveInHrd(IssueHeaderRequestDTO dto);



    TaskMastResponsibleResponseDto<List<PurchaseRequisitionResponseDTO>> getPendingPurchaseRequisitionStatus(PendingPurchaseRequisitionRequestStatusDTO requestDto);

    TaskMastResponsibleResponseDto<List<PurchaseRequisitionApprovalDTO>> getPurchaseRequisitionApproval(PendingPurchaseRequisitionRequestStatusDTO requestDto);

    TaskMastResponsibleTempResponseDto prApproval(PendingPurchaseRequisitionRequestStatusDTO dto);





}

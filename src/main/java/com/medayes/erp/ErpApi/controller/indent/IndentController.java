package com.medayes.erp.ErpApi.controller.indent;


import com.medayes.erp.ErpApi.dto.request.indent.*;
import com.medayes.erp.ErpApi.dto.response.indent.*;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleTempResponseDto;
import com.medayes.erp.ErpApi.services.indent.IndentServices;
import com.medayes.erp.ErpApi.services.indent.QuotationPurchaseServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/PAYROLL_APP_TEST/api/PayrollSale/")
@RequiredArgsConstructor
public class IndentController {
    final private IndentServices  indentServices;
    final  private QuotationPurchaseServices quotationPurchaseServices;


    @PostMapping(value =  "reqSlipFetch", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskMastResponsibleResponseDto<List<RequirementSlipDto>>> getRequestSlip(@RequestBody RequirementSlipRequestDto requestDto)
    {
        TaskMastResponsibleResponseDto<List<RequirementSlipDto>> responseDto=   indentServices.getRequirementSlip(requestDto);
         return  ResponseEntity.status(responseDto.code()).body(responseDto);

    }
        @PostMapping(value =  "reqSlipApproval", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskMastResponsibleResponseDto<List<RequirementSlipReposeDto>>> getRequestSlipApproval(@RequestBody RequirementSlipRequestDto requestDto)
    {
        TaskMastResponsibleResponseDto<List<RequirementSlipReposeDto>> responseDto=   indentServices.getRequirementApprovalSlip(requestDto);
        return  ResponseEntity.status(responseDto.code()).body(responseDto);

    }
    @PostMapping(value =  "permission", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskMastResponsibleResponseDto<List<PermissionsResponseDto>>> getPermission(@RequestBody RequirementSlipRequestDto requestDto)
    {
        TaskMastResponsibleResponseDto<List<PermissionsResponseDto>> responseDto=   indentServices.getRequirementPermission(requestDto);
        return  ResponseEntity.status(responseDto.code()).body(responseDto);

    }
    @PostMapping(value =  "requestSlipApproval", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskMastResponsibleTempResponseDto> requestSlipApproval(@RequestBody RequirementSlipApprovalRequestDto requestDto)
    {
        TaskMastResponsibleTempResponseDto responseDto=   indentServices.requirementApprovalSlip(requestDto);
        return  ResponseEntity.status(responseDto.getCode()).body(responseDto);

    }

    @PostMapping(value =  "pendingPurchaseRequisition", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskMastResponsibleResponseDto<List<PendingPurchaseRequisitionResponseDTO>>> pendingPurchaseRequisition(@RequestBody PendingPurchaseRequisitionRequestDTO requestDto)
    {
        TaskMastResponsibleResponseDto<List<PendingPurchaseRequisitionResponseDTO>> responseDto=   indentServices.getPendingPurchaseRequisition(requestDto);
        return  ResponseEntity.status(responseDto.code()).body(responseDto);

    }

    @PostMapping(value =  "prHdr", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskMastResponsibleTempResponseDto> prHdrSave(@RequestBody IssueHeaderRequestDTO requestDto)
    {
        TaskMastResponsibleTempResponseDto responseDto=   indentServices.saveInHrd(requestDto);
        return  ResponseEntity.status(responseDto.getCode()).body(responseDto);

    }
    @PostMapping(value =  "prDet", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskMastResponsibleTempResponseDto> prDetSave(@RequestBody IssueRequestDTO requestDto)
    {
        TaskMastResponsibleTempResponseDto responseDto=   indentServices.saveInDet(requestDto);
        return  ResponseEntity.status(responseDto.getCode()).body(responseDto);

    }

    @PostMapping(value =  "purchaseRequisitionStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskMastResponsibleResponseDto<List<PurchaseRequisitionResponseDTO>>> pendingPurchaseRequisition(@RequestBody PendingPurchaseRequisitionRequestStatusDTO requestDto)
    {
        TaskMastResponsibleResponseDto<List<PurchaseRequisitionResponseDTO>> responseDto=   indentServices.getPendingPurchaseRequisitionStatus(requestDto);
        return  ResponseEntity.status(responseDto.code()).body(responseDto);

    }

    @PostMapping(value =  "purchaseRequisitionApproval", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskMastResponsibleResponseDto<List<PurchaseRequisitionApprovalDTO>>> pendingPurchaseRequisitionApproval(@RequestBody PendingPurchaseRequisitionRequestStatusDTO requestDto)
    {
        TaskMastResponsibleResponseDto<List<PurchaseRequisitionApprovalDTO>> responseDto=   indentServices.getPurchaseRequisitionApproval(requestDto);
        return  ResponseEntity.status(responseDto.code()).body(responseDto);

    }
    @PostMapping(value =  "purchaseRequisitionApprovalSave", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskMastResponsibleTempResponseDto> pendingPurchaseRequisitionApprovalSave(@RequestBody PendingPurchaseRequisitionRequestStatusDTO requestDto)
    {
        TaskMastResponsibleTempResponseDto  responseDto=   indentServices.prApproval(requestDto);
        return  ResponseEntity.status(responseDto.getCode()).body(responseDto);

    }


    @PostMapping(value =  "quotationRequisition", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskMastResponsibleResponseDto<List<QuotationRequisitionDTO>>> quotationRequisition(@RequestBody QuotationRequisitionRequestDTO requestDto)
    {
        TaskMastResponsibleResponseDto<List<QuotationRequisitionDTO>>  responseDto=   quotationPurchaseServices.getQuotationRequisition(requestDto);
        return  ResponseEntity.status(responseDto.code()).body(responseDto);

    }









}

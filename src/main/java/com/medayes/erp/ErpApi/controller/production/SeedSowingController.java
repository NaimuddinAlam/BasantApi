package com.medayes.erp.ErpApi.controller.production;

import com.medayes.erp.ErpApi.dto.request.production.*;
import com.medayes.erp.ErpApi.dto.response.production.ReportOrganiserStockResponseDto;
import com.medayes.erp.ErpApi.dto.response.production.ReportResponseDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;
import com.medayes.erp.ErpApi.services.production.MultipleImageUploadServices;
import com.medayes.erp.ErpApi.services.production.SeedSowingServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/PAYROLL_APP_TEST/api/PayrollSale/")
@RequiredArgsConstructor
public class SeedSowingController {
    private  final SeedSowingServices services;
    private  final MultipleImageUploadServices multipleImageUploadServices;


    @PostMapping("seedSowing")
    private ResponseEntity<?> seedSowing(@RequestBody SeedSowingRequestSto sowingRequestSto)
    {
        TaskMastResponsibleResponseDto<?>  response=services.getSeedSowing(sowingRequestSto);
         return  ResponseEntity.status(response.code()).body(response);
    }
    @PostMapping("seedSowingUpdate")
    private ResponseEntity<?> seedSowingUpdate(@RequestBody SeedSowingUpdateRequestDto sowingRequestSto,@RequestHeader("Idempotency-Key") String idempotencyKey)
    {
        TaskMastResponsibleResponseDto<?>  response=services.updateSeedSowing(sowingRequestSto,idempotencyKey);
        return  ResponseEntity.status(response.code()).body(response);
    }
    @PostMapping("generateFarmerLotNo")
    private ResponseEntity<?> generateFarmerLotNo(@RequestBody SeedSowingUpdateRequestDto sowingRequestSto,@RequestHeader("Idempotency-Key") String idempotencyKey)
    {
        TaskMastResponsibleResponseDto<?>  response=services.generateFarmerLotNo(sowingRequestSto,idempotencyKey);
        return  ResponseEntity.status(response.code()).body(response);
    }
    @PostMapping("fetchOperation")
    private ResponseEntity<?> fetchFiledOperation(@RequestBody OperationRequestDto sowingRequestSto)
    {
        TaskMastResponsibleResponseDto<?>  response=services.fetchFiledOperation(sowingRequestSto);
        return  ResponseEntity.status(response.code()).body(response);
    }
    @PostMapping("issue/organiser")
    private ResponseEntity<?> fetchIssueOrganiser(@RequestBody ReportRequestDto dto)
    {
        ReportResponseDto<?> response=services.fetchIssueOrganiser(dto);
        return  ResponseEntity.status(response.code()).body(response);
    }
    @PostMapping("stock/ledger/organiser")
    private ResponseEntity<?> fetchOrganiserStockLedge(@RequestBody ReportRequestDto dto)
    {
        ReportOrganiserStockResponseDto<?> response=services.fetchOrganiserStockLedge(dto);
        return  ResponseEntity.status(response.code()).body(response);
    }@PostMapping("stock/summary/organiser")
    private ResponseEntity<?> fetchOrganiserSummary(@RequestBody ReportRequestDto dto)
    {
        TaskMastResponsibleResponseDto<?>  response=services.fetchOrganiserSummary(dto);
        return  ResponseEntity.status(response.code()).body(response);
    }
    @PostMapping("fetchOperationDetails")
    private ResponseEntity<?> fetchOperationDetails(@RequestBody OperationRequestDto sowingRequestSto)
    {
        TaskMastResponsibleResponseDto<?>  response=services.fetchFiledOperationDetails(sowingRequestSto);
        return  ResponseEntity.status(response.code()).body(response);
    }

    @PostMapping("fetchOperations")
    private ResponseEntity<?> fetchFiledOperations(@RequestBody OperationRequestDto sowingRequestSto)
    {
        TaskMastResponsibleResponseDto<?>  response=services.fetchFiledOperations(sowingRequestSto);
        return  ResponseEntity.status(response.code()).body(response);
    }
    @PostMapping("fetchQcInputType")
    private ResponseEntity<?> fetchQCInputType(@RequestBody OperationRequestDto sowingRequestSto)
    {
        TaskMastResponsibleResponseDto<?>  response=services.fetchQCInputType(sowingRequestSto);
        return  ResponseEntity.status(response.code()).body(response);
    }
    @PostMapping("operationDetSave")
    private ResponseEntity<?> operationDetSave(@RequestBody OperationRequestDto sowingRequestSto)
    {
        TaskMastResponsibleResponseDto<?>  response=services.operationDetSave(sowingRequestSto);
        return  ResponseEntity.status(response.code()).body(response);

}
    @PostMapping(
            value = "/upload-images",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public TaskMastResponsibleResponseDto<?> uploadImages(
            @ModelAttribute OperationRequestDto photos) {
        TaskMastResponsibleResponseDto<?>  response=multipleImageUploadServices.saveImage(photos);
        return ResponseEntity.status(response.code()).body(response).getBody();




    }

    @PostMapping("filedOperationGenerate")
    private ResponseEntity<?> filedOperationGenerate(@RequestBody SeedSowingUpdateRequestDto sowingRequestSto)
    {
        TaskMastResponsibleResponseDto<?>  response=services.filedOperationGenerate(sowingRequestSto);
        return  ResponseEntity.status(response.code()).body(response);
    }

    @PostMapping("qualityCheck")
    private ResponseEntity<?> filedOperationGenerate(@RequestBody QualityCheckRequestDto sowingRequestSto)
    {
        TaskMastResponsibleResponseDto<?>  response=services.fetchQualityCheck(sowingRequestSto);
        return  ResponseEntity.status(response.code()).body(response);

}
    @PostMapping("reason")
    private ResponseEntity<?> fetchReason(@RequestBody QualityCheckRequestDto sowingRequestSto)
    {
        TaskMastResponsibleResponseDto<?>  response=services.fetchReason(sowingRequestSto);
        return  ResponseEntity.status(response.code()).body(response);

    }
    @PostMapping("qualityCheckUpdate")
    private ResponseEntity<?> filedOperationGenerate(@RequestBody QualityCheckUpdateRequestDto sowingRequestSto)
    {
        TaskMastResponsibleResponseDto<?>  response=services.fetchQualityCheckUpdate(sowingRequestSto);
        return  ResponseEntity.status(response.code()).body(response);

    }
    @PostMapping("reasonType")
    private ResponseEntity<?> fetchReasonType(@RequestBody QualityCheckRequestDto sowingRequestSto)
    {
        TaskMastResponsibleResponseDto<?>  response=services.fetchReasonType(sowingRequestSto);
        return  ResponseEntity.status(response.code()).body(response);

    }

    @PostMapping("organiser")
    private ResponseEntity<?> fetchOrganiser(@RequestBody QualityCheckRequestDto sowingRequestSto)
    {
        TaskMastResponsibleResponseDto<?>  response=services.fetchOrganiser(sowingRequestSto);
        return  ResponseEntity.status(response.code()).body(response);

    }
    @PostMapping("farmer")
    private ResponseEntity<?> fetchFarmer(@RequestBody QualityCheckRequestDto sowingRequestSto)
    {
        TaskMastResponsibleResponseDto<?>  response=services.fetchFarmer(sowingRequestSto);
        return  ResponseEntity.status(response.code()).body(response);

    }

}

package com.medayes.erp.ErpApi.services.production;


import com.medayes.erp.ErpApi.dto.request.production.*;

import com.medayes.erp.ErpApi.dto.response.production.QualityCheckResponseDto;
import com.medayes.erp.ErpApi.dto.response.production.ReportOrganiserStockResponseDto;
import com.medayes.erp.ErpApi.dto.response.production.ReportResponseDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;
import org.springframework.data.domain.Pageable;


public interface SeedSowingServices {
    TaskMastResponsibleResponseDto<?> getSeedSowing(SeedSowingRequestSto seedSowingRequestSto);
    TaskMastResponsibleResponseDto<?> updateSeedSowing(SeedSowingUpdateRequestDto seedSowingRequestSto, String idempotencyKey);
    TaskMastResponsibleResponseDto<?> generateFarmerLotNo(SeedSowingUpdateRequestDto seedSowingRequestSto, String idempotencyKey);
    TaskMastResponsibleResponseDto<?> fetchFiledOperation(OperationRequestDto operationRequestDto);
    ReportResponseDto<?> fetchIssueOrganiser(ReportRequestDto dto);
    ReportOrganiserStockResponseDto<?> fetchOrganiserStockLedge(ReportRequestDto dto);
    TaskMastResponsibleResponseDto<?> fetchOrganiserSummary(ReportRequestDto dto);

    TaskMastResponsibleResponseDto<?> fetchFiledOperationDetails(OperationRequestDto operationRequestDto);
    TaskMastResponsibleResponseDto<?> fetchFiledOperations(OperationRequestDto operationRequestDto);
    TaskMastResponsibleResponseDto<?> fetchQCInputType(OperationRequestDto operationRequestDto);
    TaskMastResponsibleResponseDto<?> operationDetSave(OperationRequestDto operationRequestDto);

    TaskMastResponsibleResponseDto<?> filedOperationGenerate(SeedSowingUpdateRequestDto seedSowingRequestSto);
    TaskMastResponsibleResponseDto<?> fetchQualityCheck(QualityCheckRequestDto dto);

    TaskMastResponsibleResponseDto<?> fetchReason(QualityCheckRequestDto dto);
    TaskMastResponsibleResponseDto<?> fetchQualityCheckUpdate(QualityCheckUpdateRequestDto dto);
    TaskMastResponsibleResponseDto<?> fetchReasonType(QualityCheckRequestDto dto);
    TaskMastResponsibleResponseDto<?> fetchOrganiser(QualityCheckRequestDto dto);
    TaskMastResponsibleResponseDto<?> fetchFarmer(QualityCheckRequestDto dto);





}

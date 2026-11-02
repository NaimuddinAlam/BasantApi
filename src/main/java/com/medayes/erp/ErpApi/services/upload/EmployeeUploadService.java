package com.medayes.erp.ErpApi.services.upload;

import com.medayes.erp.ErpApi.dto.request.sale.SalePermissionRequestDto;
import com.medayes.erp.ErpApi.dto.request.upload.EmployeeUploadDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;


public interface EmployeeUploadService {
    TaskMastResponsibleResponseDto<?> employeeUpload(EmployeeUploadDto dto);
    TaskMastResponsibleResponseDto<?> employeeResignation(EmployeeUploadDto dto);
    TaskMastResponsibleResponseDto<?> getBannerMast(EmployeeUploadDto dto);
    TaskMastResponsibleResponseDto<?> getMisUrl(EmployeeUploadDto dto);
    TaskMastResponsibleResponseDto<?> misPermission(SalePermissionRequestDto salePermissionRequestDto);


}

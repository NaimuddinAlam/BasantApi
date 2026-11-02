package com.medayes.erp.ErpApi.services.sale;

import com.medayes.erp.ErpApi.dto.request.sale.SalePermissionRequestDto;
import com.medayes.erp.ErpApi.dto.request.sale.SeasonMastRequestDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;

public interface SeasonMastServices {

    TaskMastResponsibleResponseDto<?> fetchSeason(SeasonMastRequestDto seasonMastRequestDto);
    TaskMastResponsibleResponseDto<?> salePermission(SalePermissionRequestDto salePermissionRequestDto);
}

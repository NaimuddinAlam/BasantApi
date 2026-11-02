package com.medayes.erp.ErpApi.repository.sale;

import com.medayes.erp.ErpApi.dto.request.sale.SalePermissionRequestDto;
import com.medayes.erp.ErpApi.dto.request.sale.SeasonMastRequestDto;
import com.medayes.erp.ErpApi.dto.response.sale.SalePermissionResponseDto;
import com.medayes.erp.ErpApi.dto.response.sale.SeasonMastResponseDto;

import java.util.List;

public interface SeasonMastRepositoryImpl {
    List<SeasonMastResponseDto> fetchSeasonMast(SeasonMastRequestDto mastRequestDto);

    SalePermissionResponseDto approvalId(SalePermissionRequestDto permissionRequestDto);


}

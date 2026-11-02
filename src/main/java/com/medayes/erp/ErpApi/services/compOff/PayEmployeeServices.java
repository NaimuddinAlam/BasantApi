package com.medayes.erp.ErpApi.services.compOff;

import com.medayes.erp.ErpApi.dto.request.compOff.CompOffRequestDTO;
import com.medayes.erp.ErpApi.dto.request.compOff.PayEmployeeDTO;
import com.medayes.erp.ErpApi.dto.response.APIResponse;


public interface PayEmployeeServices {
    APIResponse<PayEmployeeDTO> fetchInfo(CompOffRequestDTO comp);

}

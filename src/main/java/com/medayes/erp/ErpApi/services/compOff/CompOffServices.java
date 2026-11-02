package com.medayes.erp.ErpApi.services.compOff;

import com.medayes.erp.ErpApi.dto.request.compOff.CompOffApprovalByDTO;
import com.medayes.erp.ErpApi.dto.request.compOff.CompOffApprovalDTO;
import com.medayes.erp.ErpApi.dto.request.compOff.CompOffRequestDTO;
import com.medayes.erp.ErpApi.dto.request.compOff.CompOffRequestSaveDTO;
import com.medayes.erp.ErpApi.dto.response.APIResponse;
import com.medayes.erp.ErpApi.dto.response.compOff.CompOffDTO;


public interface CompOffServices {
    APIResponse<CompOffDTO> fetchCompOff( CompOffRequestDTO comp);
    APIResponse<?> saveCompOff( CompOffRequestSaveDTO comp);
    APIResponse<CompOffApprovalDTO> fetchCompOffApproval(CompOffRequestDTO comp);
    APIResponse<CompOffApprovalDTO> fetchCompOffRequest(CompOffRequestDTO comp);
    APIResponse<?> updateCompOff( CompOffApprovalByDTO comp);


}

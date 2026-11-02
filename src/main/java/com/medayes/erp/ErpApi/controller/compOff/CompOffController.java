package com.medayes.erp.ErpApi.controller.compOff;

import com.medayes.erp.ErpApi.dto.request.compOff.CompOffApprovalByDTO;
import com.medayes.erp.ErpApi.dto.request.compOff.CompOffRequestDTO;
import com.medayes.erp.ErpApi.dto.request.compOff.CompOffRequestSaveDTO;
import com.medayes.erp.ErpApi.dto.response.APIResponse;
import com.medayes.erp.ErpApi.dto.response.compOff.CompOffDTO;
import com.medayes.erp.ErpApi.services.compOff.CompOffServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/PAYROLL_APP_TEST/api/PayrollSale/")
@RequiredArgsConstructor
public class CompOffController {
    private  final CompOffServices compOffServices;
    @PostMapping("compOff")
    private ResponseEntity<APIResponse<CompOffDTO>> compOff(@RequestBody CompOffRequestDTO compOffRequestDTO)
    {
        APIResponse<CompOffDTO>  apiResponse=  compOffServices.fetchCompOff(compOffRequestDTO);
         return  ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }
    @PostMapping("compOffSave")
    private ResponseEntity<APIResponse<?>> compOffSave(@RequestBody CompOffRequestSaveDTO compOffRequestDTO)
    {
        APIResponse<?>  apiResponse=  compOffServices.saveCompOff(compOffRequestDTO);
        return  ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }
    @PostMapping("compOffFetch")
    private ResponseEntity<APIResponse<?>> compOffFetch(@RequestBody CompOffRequestDTO compOffRequestDTO)
    {
        APIResponse<?>  apiResponse=  compOffServices.fetchCompOffApproval(compOffRequestDTO);
        return  ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }
    @PostMapping("compOffFetchRequest")
    private ResponseEntity<APIResponse<?>> fetchCompOffRequest(@RequestBody CompOffRequestDTO compOffRequestDTO)
    {
        APIResponse<?>  apiResponse=  compOffServices.fetchCompOffRequest(compOffRequestDTO);
        return  ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }
    @PostMapping("compOffUpdate")
    private ResponseEntity<APIResponse<?>> compOffUpdate(@RequestBody CompOffApprovalByDTO compOffRequestDTO)
    {
        APIResponse<?>  apiResponse=  compOffServices.updateCompOff(compOffRequestDTO);
        return  ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }


}

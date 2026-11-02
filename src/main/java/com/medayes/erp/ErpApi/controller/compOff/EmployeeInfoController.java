package com.medayes.erp.ErpApi.controller.compOff;


import com.medayes.erp.ErpApi.dto.request.compOff.CompOffRequestDTO;
import com.medayes.erp.ErpApi.dto.request.compOff.PayEmployeeDTO;
import com.medayes.erp.ErpApi.dto.response.APIResponse;
import com.medayes.erp.ErpApi.services.compOff.PayEmployeeServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/PAYROLL_APP_TEST/api/PayrollSale/")
@RequiredArgsConstructor
public class EmployeeInfoController {
    private  final PayEmployeeServices payEmployeeServices;
    @PostMapping("info")
    private ResponseEntity<APIResponse<PayEmployeeDTO>> getInfo(@RequestBody CompOffRequestDTO compOffRequestDTO)
    {
        APIResponse<PayEmployeeDTO>  apiResponse=  payEmployeeServices.fetchInfo(compOffRequestDTO);
        return  ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }


}
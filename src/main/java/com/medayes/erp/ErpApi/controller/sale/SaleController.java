package com.medayes.erp.ErpApi.controller.sale;

import com.medayes.erp.ErpApi.dto.request.sale.SalePermissionRequestDto;
import com.medayes.erp.ErpApi.dto.request.sale.SeasonMastRequestDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;
import com.medayes.erp.ErpApi.services.sale.SeasonMastServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/PAYROLL_APP_TEST/api/PayrollSale/")
public class SaleController {
    private  final SeasonMastServices services;

    public SaleController(SeasonMastServices services) {
        this.services = services;
    }

    @PostMapping("/season")
    public ResponseEntity<?> fetchSeason(@RequestBody SeasonMastRequestDto seasonMastRequestDto)

    {
        TaskMastResponsibleResponseDto<?> responseDto=services.fetchSeason(seasonMastRequestDto);
        return  ResponseEntity.status(responseDto.code()).body(responseDto);
    }
    @PostMapping("/salePermission")
    public ResponseEntity<?> permission(@RequestBody SalePermissionRequestDto permissionRequestDto)

    {
        TaskMastResponsibleResponseDto<?> responseDto=services.salePermission(permissionRequestDto);
        return  ResponseEntity.status(responseDto.code()).body(responseDto);
    }
}

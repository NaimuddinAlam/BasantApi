package com.medayes.erp.ErpApi.controller.area;


import com.medayes.erp.ErpApi.dto.request.BillPrintRequestDTO;
import com.medayes.erp.ErpApi.dto.request.salary.SalaryProcessRequest;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;
import com.medayes.erp.ErpApi.services.area.AreaServices;
import com.medayes.erp.ErpApi.services.billPrint.BillPrintServices;
import com.medayes.erp.ErpApi.services.salary.SalaryServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/PAYROLL_APP_TEST/api/PayrollSale/")
@RequiredArgsConstructor
public class AreaController {
    private  final AreaServices areaServices;
    private  final BillPrintServices billPrintServices;
    private  final SalaryServices salaryService;
    @GetMapping("state")
    public ResponseEntity<?> state()
    {
        TaskMastResponsibleResponseDto<?> responseDto=areaServices.getStateMast();
        return ResponseEntity.status(responseDto.code()).body(responseDto);
    }
    @GetMapping("district/{stateCode}")
    public ResponseEntity<?> district(@PathVariable String stateCode)
    {
        TaskMastResponsibleResponseDto<?> responseDto=areaServices.
                getDistrictMast(stateCode);
        return ResponseEntity.status(responseDto.code()).body(responseDto);
    }
    @GetMapping("taluka/{distCode}")
    public ResponseEntity<?> taluka(@PathVariable String distCode)
    {
        TaskMastResponsibleResponseDto<?> responseDto=areaServices.
                getTaluka(distCode);
        return ResponseEntity.status(responseDto.code()).body(responseDto);
    }
    @GetMapping("billNo")
    public ResponseEntity<?> billNo()
    {
        TaskMastResponsibleResponseDto<?> responseDto=billPrintServices.
                getBillPrint();
        return ResponseEntity.status(responseDto.code()).body(responseDto);
    }
    @PostMapping("billInvoice")
    public ResponseEntity<?> billInvoice(@RequestBody BillPrintRequestDTO billPrintRequestDTO)
    {
        TaskMastResponsibleResponseDto<?> responseDto=billPrintServices.getBillPrintInvoice(billPrintRequestDTO);
        return ResponseEntity.status(responseDto.code()).body(responseDto);
    }

    @PostMapping("salaryProcess")
    public ResponseEntity<?> salaryProcess(@RequestBody SalaryProcessRequest billPrintRequestDTO)
    {
        TaskMastResponsibleResponseDto<?> responseDto=salaryService.salaryProcess(billPrintRequestDTO);
        return ResponseEntity.status(responseDto.code()).body(responseDto);
    }

}

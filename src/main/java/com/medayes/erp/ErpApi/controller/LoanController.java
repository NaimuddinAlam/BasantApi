package com.medayes.erp.ErpApi.controller;




import com.medayes.erp.ErpApi.dto.request.*;
import com.medayes.erp.ErpApi.dto.response.CheckFormPermissionLoanResponseDto;
import com.medayes.erp.ErpApi.dto.response.LoanResponse;
import com.medayes.erp.ErpApi.dto.response.LoanResponseDto;
import com.medayes.erp.ErpApi.dto.response.LoanResponseFetchDetDto;
import com.medayes.erp.ErpApi.services.InvReqSlipService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("PAYROLL_APP_TEST/api/PayrollSale/")

public class LoanController {

    @Autowired
    private InvReqSlipService loanService;




    @PostMapping("/generate")
    public ResponseEntity<LoanResponseDto> generateLoanNoPost( @Valid @RequestBody LoanRequestDto request) {
        LoanResponseDto responseDto=  loanService.generateLoanNo(request);

          return   ResponseEntity.status(responseDto.code()).body(responseDto);
    }

    @PostMapping("/hdrsave")
    public ResponseEntity<LoanResponseDto> hdrSave(@Valid @RequestBody LoanSaveHdrRequestDto request) {
        LoanResponseDto responseDto=  loanService.saveLoanHdr(request);

        return   ResponseEntity.status(responseDto.code()).body(responseDto);
    }
    @PostMapping("/detsave")
    public ResponseEntity<LoanResponseDto> detSave(@Valid @RequestBody LoanSaveDetRequestDto request) {
        LoanResponseDto responseDto=  loanService.saveLoanDet(request);

        return   ResponseEntity.status(responseDto.code()).body(responseDto);
    }
    @PostMapping("/fetchLoanData")
    public ResponseEntity<?> fetchLoan( @RequestBody LoanSaveDetRequestDto request) {

        LoanResponse<List<LoanResponseFetchDetDto>> responseDto = loanService.fetchLoanData(request);


        return ResponseEntity.status(responseDto.getCode()).body(responseDto);
    }
    @PostMapping("/fetchLoanDataApproval")
    public ResponseEntity<?> fetchLoanApproval( @RequestBody LoanFetchApprovalRequestDto request) {

        LoanResponse<List<LoanResponseFetchDetDto>> responseDto = loanService.fetchLoanDataApproval(request);


        return ResponseEntity.status(responseDto.getCode()).body(responseDto);
    }
    @PostMapping("/checkFromPermission")
    public ResponseEntity<?> checkFromPermission( @RequestBody CheckFormPermissionLoanRequestDto request) {

        LoanResponse<List<CheckFormPermissionLoanResponseDto>> responseDto = loanService.checkFormPermissionLoan(request);


        return ResponseEntity.status(responseDto.getCode()).body(responseDto);
    }

    @PostMapping("/approvalLoanSave")
    public ResponseEntity<LoanResponseDto> approvalLoanSave( @RequestBody LoanApprovalRequestDto request) {
        LoanResponseDto responseDto=  loanService.approvalLoanSave(request);

        return   ResponseEntity.status(responseDto.code()).body(responseDto);
    }

}



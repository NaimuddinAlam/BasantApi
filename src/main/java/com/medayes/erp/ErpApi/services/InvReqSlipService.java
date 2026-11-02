package com.medayes.erp.ErpApi.services;

import com.medayes.erp.ErpApi.dto.request.*;
import com.medayes.erp.ErpApi.dto.response.CheckFormPermissionLoanResponseDto;
import com.medayes.erp.ErpApi.dto.response.LoanResponse;
import com.medayes.erp.ErpApi.dto.response.LoanResponseDto;
import com.medayes.erp.ErpApi.dto.response.LoanResponseFetchDetDto;

import java.util.List;

public interface InvReqSlipService {
    LoanResponseDto generateLoanNo(LoanRequestDto loanRequestDto);
    LoanResponseDto saveLoanHdr(LoanSaveHdrRequestDto loanSaveHdrRequestDto);
    LoanResponseDto saveLoanDet(LoanSaveDetRequestDto loanSaveDetRequestDto);
    LoanResponse<List<LoanResponseFetchDetDto>> fetchLoanData(LoanSaveDetRequestDto loanSaveDetRequestDto);
    LoanResponse<List<LoanResponseFetchDetDto>> fetchLoanDataApproval(LoanFetchApprovalRequestDto loanFetchApprovalRequestDto);
    LoanResponse<List<CheckFormPermissionLoanResponseDto>> checkFormPermissionLoan(CheckFormPermissionLoanRequestDto loanRequestDto);
    LoanResponseDto approvalLoanSave(LoanApprovalRequestDto loanSaveDetRequestDto);





}

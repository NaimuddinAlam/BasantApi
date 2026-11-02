package com.medayes.erp.ErpApi.repository.expenses;

import com.medayes.erp.ErpApi.dto.request.expense.ApprovalExpenseRequestDTO;
import com.medayes.erp.ErpApi.dto.request.expense.ExpenseRequestDTO;
import com.medayes.erp.ErpApi.dto.response.expense.AdvanceApprovalResponseDTO;
import com.medayes.erp.ErpApi.dto.response.expense.AdvanceExpenseResponseDTO;
import com.medayes.erp.ErpApi.dto.response.expense.ExpenseResponseDTO;

import java.util.List;

public interface AdvanceExpenseRepository {
    List<ExpenseResponseDTO> save(ExpenseRequestDTO expenseRequestDTO);
    List<AdvanceExpenseResponseDTO> fetch(ExpenseRequestDTO expenseRequestDTO);
    List<AdvanceExpenseResponseDTO> fetchApproval(ApprovalExpenseRequestDTO expenseRequestDTO);
    List<AdvanceApprovalResponseDTO> saveApproval(ApprovalExpenseRequestDTO expenseRequestDTO);


}

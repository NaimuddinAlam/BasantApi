package com.medayes.erp.ErpApi.services.expense;

import com.medayes.erp.ErpApi.dto.request.expense.ApprovalExpenseRequestDTO;
import com.medayes.erp.ErpApi.dto.request.expense.ExpenseRequestDTO;
import com.medayes.erp.ErpApi.dto.response.expense.ResponseExpenseDTO;

public interface AdvanceExpenseServices {
    ResponseExpenseDTO<?> saveAdvanceExpense(ExpenseRequestDTO expenseRequestDTO);
    ResponseExpenseDTO<?> fetchAdvanceExpense(ExpenseRequestDTO expenseRequestDTO);
    ResponseExpenseDTO<?> fetchApprovalAdvanceExpense(ApprovalExpenseRequestDTO expenseRequestDTO);
    ResponseExpenseDTO<?> saveAdvanceExpenseApproval(ApprovalExpenseRequestDTO expenseRequestDTO);
}

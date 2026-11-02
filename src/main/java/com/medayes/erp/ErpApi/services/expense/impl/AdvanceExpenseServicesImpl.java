package com.medayes.erp.ErpApi.services.expense.impl;

import com.medayes.erp.ErpApi.dto.request.expense.ApprovalExpenseRequestDTO;
import com.medayes.erp.ErpApi.dto.request.expense.ExpenseRequestDTO;
import com.medayes.erp.ErpApi.dto.response.expense.AdvanceApprovalResponseDTO;
import com.medayes.erp.ErpApi.dto.response.expense.AdvanceExpenseResponseDTO;
import com.medayes.erp.ErpApi.dto.response.expense.ExpenseResponseDTO;
import com.medayes.erp.ErpApi.dto.response.expense.ResponseExpenseDTO;

import com.medayes.erp.ErpApi.exeption.DatabaseException;
import com.medayes.erp.ErpApi.repository.expenses.AdvanceExpenseRepositoryImpl;
import com.medayes.erp.ErpApi.services.expense.AdvanceExpenseServices;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvanceExpenseServicesImpl  implements AdvanceExpenseServices {
  private  final AdvanceExpenseRepositoryImpl advanceExpense;
    public AdvanceExpenseServicesImpl(AdvanceExpenseRepositoryImpl advanceExpense) {
        this.advanceExpense = advanceExpense;
    }



    @Override
    public ResponseExpenseDTO<?> saveAdvanceExpense(ExpenseRequestDTO expenseRequestDTO) {
        try {
            List<ExpenseResponseDTO> result= advanceExpense.save(expenseRequestDTO);

            if(result==null || result.isEmpty())
            {
                return new ResponseExpenseDTO<>("Advance Expense not successfully", false, HttpStatus.BAD_REQUEST.value(), result);

            }


            return new ResponseExpenseDTO<>("Advance Expense save successfully", true, HttpStatus.OK.value(), result);
        }catch (DataAccessException ex) {
            throw new DatabaseException(
                    "Database error occurred: " + ex.getMostSpecificCause().getMessage(), ex
            );


        }}

    @Override
    public ResponseExpenseDTO<?> fetchAdvanceExpense(ExpenseRequestDTO expenseRequestDTO) {
        try {
            List<AdvanceExpenseResponseDTO> result= advanceExpense.fetch(expenseRequestDTO);

            if(result==null || result.isEmpty())
            {
                return new ResponseExpenseDTO<>("Advance Expense not data found", false, HttpStatus.BAD_REQUEST.value(), result);

            }

            return new ResponseExpenseDTO<>("Advance Expense fetch successfully", true, HttpStatus.OK.value(), result);
        }catch (DataAccessException ex) {
            throw new DatabaseException(
                    "Database error occurred: " + ex.getMostSpecificCause().getMessage(), ex
            );


        }}

    @Override
    public ResponseExpenseDTO<?> fetchApprovalAdvanceExpense(ApprovalExpenseRequestDTO expenseRequestDTO) {
        try {
            List<AdvanceExpenseResponseDTO> result= advanceExpense.fetchApproval(expenseRequestDTO);

            if(result==null || result.isEmpty())
            {
                return new ResponseExpenseDTO<>("Advance Expense not data found", false, HttpStatus.OK.value(), result);

            }

            return new ResponseExpenseDTO<>("Advance Expense fetch successfully", true, HttpStatus.OK.value(), result);
        }catch (DataAccessException ex) {
            throw new DatabaseException(
                    "Database error occurred: " + ex.getMostSpecificCause().getMessage(), ex
            );


        }
    }

    @Override
    public ResponseExpenseDTO<?> saveAdvanceExpenseApproval(ApprovalExpenseRequestDTO expenseRequestDTO) {
        try {
            List<AdvanceApprovalResponseDTO> result= advanceExpense.saveApproval(expenseRequestDTO);

            if(result==null || result.isEmpty())
            {
                return new ResponseExpenseDTO<>("Advance Expense Approval not data found", false, HttpStatus.BAD_REQUEST.value(), result);

            }
            if(expenseRequestDTO.getAppType()=="A")
            {
                return new ResponseExpenseDTO<>("Advance expense approval save successfully", true, HttpStatus.OK.value(), result);
            }else {
                return new ResponseExpenseDTO<>("Advance expense approval cancel successfully", true, HttpStatus.OK.value(), result);
            }
           // return new ResponseExpenseDTO<>("Advance expense approval save successfully", true, HttpStatus.OK.value(), result);
        }catch (DataAccessException ex) {
            throw new DatabaseException(
                    "Database error occurred: " + ex.getMostSpecificCause().getMessage(), ex
            );


        }
    }


}

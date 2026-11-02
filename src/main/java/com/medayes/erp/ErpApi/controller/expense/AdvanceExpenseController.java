package com.medayes.erp.ErpApi.controller.expense;

import com.medayes.erp.ErpApi.dto.request.DocumentRequestDTO;
import com.medayes.erp.ErpApi.dto.request.expense.ApprovalExpenseRequestDTO;
import com.medayes.erp.ErpApi.dto.request.expense.ExpenseRequestDTO;

import com.medayes.erp.ErpApi.dto.response.expense.ResponseExpenseDTO;

import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;
import com.medayes.erp.ErpApi.services.documents.DocumentsServices;
import com.medayes.erp.ErpApi.services.expense.AdvanceExpenseServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/PAYROLL_APP_TEST/api/PayrollSale/")
@RequiredArgsConstructor
public class AdvanceExpenseController {
    private  final AdvanceExpenseServices advanceExpenseServices;
    private  final DocumentsServices documentsServices;


    @PostMapping(value =  "advanceExpense", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseExpenseDTO<?>> save(@RequestBody ExpenseRequestDTO requestDto)
    {
        ResponseExpenseDTO<?> responseDto=   advanceExpenseServices.saveAdvanceExpense(requestDto);
        return  ResponseEntity.status(responseDto.getHttpStatus()).body(responseDto);

    }
    @PostMapping(value =  "advanceExpenseFetch", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseExpenseDTO<?>> fetch(@RequestBody ExpenseRequestDTO requestDto)
    {
        ResponseExpenseDTO<?> responseDto=   advanceExpenseServices.fetchAdvanceExpense(requestDto);
        return  ResponseEntity.status(responseDto.getHttpStatus()).body(responseDto);

    }
    @PostMapping(value =  "fetchApprovalAdvanceExpense", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseExpenseDTO<?>> fetchApproval(@RequestBody ApprovalExpenseRequestDTO requestDto)
    {
        ResponseExpenseDTO<?> responseDto=   advanceExpenseServices.fetchApprovalAdvanceExpense(requestDto);
        return  ResponseEntity.status(responseDto.getHttpStatus()).body(responseDto);

    }
    @PostMapping(value =  "saveApprovalAdvanceExpense", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseExpenseDTO<?>> saveApproval(@RequestBody ApprovalExpenseRequestDTO requestDto)
    {
        ResponseExpenseDTO<?> responseDto=   advanceExpenseServices.saveAdvanceExpenseApproval(requestDto);
        return  ResponseEntity.status(responseDto.getHttpStatus()).body(responseDto);

    }

    @PostMapping("/documents")
    public ResponseEntity<TaskMastResponsibleResponseDto<?>> getDocuments(@RequestBody DocumentRequestDTO type)
    {
        TaskMastResponsibleResponseDto<?> responseDto=   documentsServices.getDocuments(type);
        return  ResponseEntity.status(responseDto.code()).body(responseDto);

    }

}

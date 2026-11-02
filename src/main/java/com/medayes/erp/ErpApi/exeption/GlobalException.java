package com.medayes.erp.ErpApi.exeption;


import com.medayes.erp.ErpApi.dto.response.ErrorResponse;
import com.medayes.erp.ErpApi.dto.response.LoanResponseDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleTempResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationExceptions(MethodArgumentNotValidException ex)
    {
        Map<String, String> errors=new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error->
                errors.put(error.getField(),error.getDefaultMessage())
        );
        return new  ResponseEntity<Map<String,String>>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<LoanResponseDto> handleUnexpected(Exception ex) {
        log.error("Unexpected error in LoanService", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(LoanResponseDto.error("An unexpected error occurred."));
    }
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<LoanResponseDto> handleConflict(ConflictException ex) {
        log.warn("Conflict: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(LoanResponseDto.error(ex.getMessage()));
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public  ResponseEntity<Object>handleResourceNotFoundException(ResourceNotFoundException ex)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(BadRequestException.class)
    public  ResponseEntity<Object>handleBadRequestException(BadRequestException ex)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<LoanResponseDto> handleBusiness(IllegalStateException ex) {
        log.warn("Business rule violation: {}", ex.getMessage());
        return ResponseEntity.badRequest()
                .body(LoanResponseDto.failure(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {

        ErrorResponse response = new ErrorResponse(
      "false",
        ex.getMessage(),
       400);

        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<TaskMastResponsibleTempResponseDto> handleSQLException(SQLException ex) {

        TaskMastResponsibleTempResponseDto response = new TaskMastResponsibleTempResponseDto(
                "FAILED",
                "Database Error: " + ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()

        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(BusinessException.class)
    public  ResponseEntity<ErrorResponse> businessException(BusinessException ex)
    { log.error("Business Exception: {}", ex.getMessage());
        ErrorResponse errorResponse= new ErrorResponse("false",ex.getMessage(),HttpStatus.BAD_REQUEST.value());
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DatabaseException.class)
    public  ResponseEntity<ErrorResponse> databaseException(DatabaseException exception)
    {
        log.error("Database Exception: {}",exception.getMessage());
        ErrorResponse errorResponse= new ErrorResponse("false",exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
    @ExceptionHandler(DataAccessException.class)
    public ErrorResponse handleSpringDBException(DataAccessException ex) {
        log.error("Spring DB Exception: {}", ex.getMostSpecificCause().getMessage());
        return new ErrorResponse(
                "false",
                "Database error: " + ex.getMostSpecificCause().getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
    }





}

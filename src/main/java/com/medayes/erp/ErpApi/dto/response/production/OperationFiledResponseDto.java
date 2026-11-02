package com.medayes.erp.ErpApi.dto.response.production;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationFiledResponseDto {
    private String operTypeCode;
    private String operTypeName;
    private String operCode;
    private String operName;
    private String targetDays;
    private String planDate;
    private String actDate;
    private String value;
    private String rating;
    private String status;
    private String remarks;
    private String lstActDate;
    private String lstValue;
    private String lstRating;
    private String lstStatus;
    private String lstStatusName;
    private String lstRemarks;
    private String inputType;
    private  String rowId;
    private  String inputValue;
    private  String res;

}
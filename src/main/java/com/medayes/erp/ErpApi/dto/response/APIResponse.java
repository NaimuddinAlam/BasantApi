package com.medayes.erp.ErpApi.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class APIResponse<T> {
   private String message;
   private  int statusCode;
   private  boolean status;
   private List<T> data;
   private LocalDateTime time;


}

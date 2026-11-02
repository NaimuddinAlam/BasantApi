package com.medayes.erp.ErpApi.dto.request.task;

public record TaskScheduleReponsibalRequestDto(
     String pcomPcode,
     String pcentCode,
    String pbranchCode,
     String ptaskScheduleCode,

      String ptaskCode,
    String prowId,
     String personCode,
      String personName,
     String email,
       String mobileNo,

      String px1,
      String px2,
      String px3,
     String px4,
      String px5,

      String pextra1,
      String pextra2,
      String pextra3,
      String pextra4,
    String pextra5,

     String ptype,
       String peditFlag
) {
}

package com.medayes.erp.ErpApi.dto.response.mis;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MisUrlResponseDto {
   private   String misUrl;
   private  String password;

}

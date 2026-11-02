package com.medayes.erp.ErpApi.dto.response;



import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyActivityDto {

    private String taskCode;
    private String taskName;
    private String taskNick;
    private String status;
}

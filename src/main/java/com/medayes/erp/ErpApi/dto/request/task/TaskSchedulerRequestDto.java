package com.medayes.erp.ErpApi.dto.request.task;

import lombok.Data;


public record TaskSchedulerRequestDto( String PCOMPCODE, String PCENTCODE, String PBRANCHCODE, String PTASKCODE, String PTASKNAME, String PTASKNICK, String PPEXTRA1) {


}

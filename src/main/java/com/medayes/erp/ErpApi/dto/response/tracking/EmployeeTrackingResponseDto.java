package com.medayes.erp.ErpApi.dto.response.tracking;

public record EmployeeTrackingResponseDto(
      String  COMP_CODE,	String USER_ID , String	USER_NAME, Double	LAT,Double	LONG, String	ROW_ID, String	CREATION_DATE, String	DISTANCE_METERS,
      String LOCATION
) {
}

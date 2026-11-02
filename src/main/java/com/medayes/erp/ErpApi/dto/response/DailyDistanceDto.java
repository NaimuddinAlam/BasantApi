package com.medayes.erp.ErpApi.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyDistanceDto {

    private String userId;
    private LocalDate date;
    private double distanceKm;
    private double distanceMeters;
    private int totalPoints;
    private int validSegments;
}
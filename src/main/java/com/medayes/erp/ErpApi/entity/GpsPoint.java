package com.medayes.erp.ErpApi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "PAY_USER_LAT_LONG")
public class GpsPoint {

    @Id
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "LAT")
    private double latitude;

    @Column(name = "LONG")
    private double longitude;

    @Column(name = "CREATE_DATE")
    private LocalDateTime timestamp;

    public GpsPoint() {
    }

    public GpsPoint(Long userId, double latitude, double longitude, LocalDateTime timestamp) {
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }
}